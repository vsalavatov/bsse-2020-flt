package com.bsse2018.salavatov.flt.graphdb.evaluator

import com.bsse2018.salavatov.flt.algorithms.CFPQTensorQuery
import com.bsse2018.salavatov.flt.automata.PDABuilder
import com.bsse2018.salavatov.flt.graphdb.ir.*
import com.bsse2018.salavatov.flt.utils.graphFromStrings
import java.io.File
import java.nio.file.Paths

class ScriptEvaluationContext {
    var connection: String? = null
    val grammarBuilder = PDABuilder()

    fun evaluate(script: IRScript): ScriptEvaluationResult = ResultList(
        script.statements.map {
            evaluate(it)
        }
    )

    fun evaluate(statement: IRStatement): ScriptEvaluationResult =
        when (statement) {
            is IRStatementConnect -> evaluate(statement)
            is IRStatementList -> evaluate(statement)
            is IRStatementPatternDeclaration -> evaluate(statement)
            is IRStatementSelect -> evaluate(statement)
        }

    fun evaluate(statement: IRStatementConnect): ScriptEvaluationResult {
        connection = statement.destination
        return ResultUnit
    }

    fun evaluate(@Suppress("UNUSED_PARAMETER") statement: IRStatementList): ScriptEvaluationResult =
        connection?.let {
            ResultOutput(
                File(it).listFiles()!!.map { file ->
                    file.name
                }.joinToString("\n")
            )
        } ?: throw NotConnectedException()


    fun evaluate(statement: IRStatementPatternDeclaration): ScriptEvaluationResult {
        grammarBuilder.addRule(statement)
        return ResultUnit
    }

    private fun PDABuilder.addRule(statement: IRStatementPatternDeclaration) {
        val from = statement.nonterminal
        if (start == "") {
            start = from
        }
        insert(
            startStates.getOrPut(from, ::newNode),
            finishStates.getOrPut(from, ::newNode),
            statement.pattern
        )
    }

    private fun PDABuilder.insert(start: Int, finish: Int, node: IRPattern) {
        fun prepareModifier(start: Int, finish: Int, modifier: IRPatternModifier?): Pair<Int, Int> {
            if (modifier == null)
                return Pair(start, finish)
            val begin = newNode()
            val end = newNode()
            automaton[start].add(Pair(IREpsilon.text, begin))
            automaton[end].add(Pair(IREpsilon.text, finish))
            when (modifier) {
                is IRPatternStar -> {
                    automaton[end].add(Pair(IREpsilon.text, begin))
                    automaton[begin].add(Pair(IREpsilon.text, end))
                }
                is IRPatternPlus -> {
                    automaton[end].add(Pair(IREpsilon.text, begin))
                }
                is IRPatternOptional -> {
                    automaton[begin].add(Pair(IREpsilon.text, end))
                }
            }
            return Pair(begin, end)
        }

        when (node) {
            is IRPatternConcat -> {
                val mid = newNode()
                insert(start, mid, node.first)
                insert(mid, finish, node.second)
            }
            is IRPatternAlternative -> {
                insert(start, finish, node.first)
                insert(start, finish, node.second)
            }
            is IRPatternParenthesis -> {
                val (begin, end) = prepareModifier(start, finish, node.mod)
                insert(begin, end, node.pattern)
            }
            is IRPatternNonterminal -> {
                val (begin, end) = prepareModifier(start, finish, node.mod)
                automaton[begin].add(Pair(node.nonterminal, end))
            }
            is IRPatternTerminal -> {
                val (begin, end) = prepareModifier(start, finish, node.mod)
                automaton[begin].add(
                    Pair(
                        when (node.terminal) {
                            is IREpsilon -> IREpsilon.text
                            is IRTerminalPure -> node.terminal.terminal
                        },
                        end
                    )
                )
            }
        }
    }

    fun evaluate(statement: IRStatementSelect): ScriptEvaluationResult =
        connection?.let { conn ->
            val graphFile = Paths.get(conn, statement.fromExpr.from).toFile()
            if (!graphFile.exists()) {
                throw GraphDoesNotExistException(statement.fromExpr.from)
            }
            val graph = graphFromStrings(graphFile.readLines().filter { it.isNotEmpty() })

            val objVars = statement.objectExpr.variables()
            val whereVars = statement.whereExpr.variables()

            if (whereVars.size == 2 && whereVars[0] == whereVars[1])
                throw LogicErrorException("Path must be specified using two distinct vertex variables")
            if (!whereVars.containsAll(objVars))
                throw LogicErrorException("Selected variable is not presented in query")
            if (objVars.size == 2 && objVars[0] == objVars[1])
                throw LogicErrorException("Selected variables must be distinct")

            val grammarCopy = grammarBuilder.copy()
            val newStart = grammarCopy.freshNonTerminal()
            grammarCopy.addRule(
                IRStatementPatternDeclaration(newStart, statement.whereExpr.pathDesc.pattern)
            )
            grammarCopy.start = newStart

            var result = CFPQTensorQuery(graph, grammarCopy.build(), IREpsilon.text)
            val pathDesc = statement.whereExpr.pathDesc

            if (pathDesc.fromVertexCond is IRVertexConditionWithId) {
                result = result.filter { it.first == pathDesc.fromVertexCond.id }.toSet()
            }
            if (pathDesc.toVertexCond is IRVertexConditionWithId) {
                result = result.filter { it.second == pathDesc.toVertexCond.id }.toSet()
            }

            val selected: Set<Any>

            if (objVars.size == 2) {
                if (objVars == whereVars) {
                    selected = result
                } else {
                    assert(objVars.reversed() == whereVars)
                    selected = result.map { Pair(it.second, it.first) }.toSet()
                }
            } else {
                if (objVars[0] == whereVars[0]) {
                    selected = result.map { it.first }.toSet()
                } else {
                    assert(objVars[0] == whereVars[1])
                    selected = result.map { it.second }.toSet()
                }
            }

            when (statement.objectExpr) {
                is IRVerticesExpression -> ResultOutput(
                    selected.joinToString { "\n" }
                )
                is IRCountExpression -> ResultOutput(
                    selected.size.toString()
                )
                is IRExistsExpression -> ResultOutput(
                    if (result.isEmpty()) {
                        "no"
                    } else {
                        "yes"
                    }
                )
            }
        } ?: throw NotConnectedException()

    private fun IRObjectExpression.variables(): List<IRVertexName> =
        when (this) {
            is IRVerticesExpression -> variables(verticesDesc)
            is IRCountExpression -> variables(verticesDesc)
            is IRExistsExpression -> variables(verticesDesc)
        }

    private fun IRWhereExpression.variables(): List<IRVertexName> =
        variables(pathDesc.fromVertexCond) + variables(pathDesc.toVertexCond)

    private fun variables(verticesDesc: IRVerticesDescription): List<IRVertexName> =
        when (verticesDesc) {
            is IRSingleVertexDescription -> listOf(verticesDesc.vertex)
            is IRPairVerticesDescription -> listOf(verticesDesc.first, verticesDesc.second)
        }

    private fun variables(vertexCond: IRVertexCondition): List<IRVertexName> =
        when (vertexCond) {
            is IRVertexConditionPure -> listOf(vertexCond.vertex)
            is IRVertexConditionWithId -> listOf(vertexCond.vertex)
        }

    private fun PDABuilder.freshNonTerminal(): String {
        val length = (startStates.maxBy { it.key.length }?.key?.length ?: 1) + 1
        return "S" + "a".repeat(length - 1)
    }
}