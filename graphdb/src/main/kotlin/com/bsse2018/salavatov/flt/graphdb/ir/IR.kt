package com.bsse2018.salavatov.flt.graphdb.ir

sealed class IR

data class IRScript(val statements: List<IRStatement>) : IR() {
    constructor(vararg statements: IRStatement) : this(listOf(*statements))
}

sealed class IRStatement : IR()
data class IRStatementConnect(val destination: String) : IRStatement()
object IRStatementList : IRStatement()
data class IRStatementPatternDeclaration(val nonterminal: String, val pattern: IRPattern) : IRStatement()
data class IRStatementSelect(
    val objectExpr: IRObjectExpression,
    val fromExpr: IRFromExpression,
    val whereExpr: IRWhereExpression
) : IRStatement()

sealed class IRObjectExpression() : IR()
data class IRVerticesExpression(val verticesDesc: IRVerticesDescription) : IRObjectExpression()
data class IRCountExpression(val verticesDesc: IRVerticesDescription) : IRObjectExpression()
data class IRExistsExpression(val verticesDesc: IRVerticesDescription) : IRObjectExpression()

data class IRFromExpression(val from: String) : IR()
data class IRWhereExpression(val pathDesc: IRPathDescription) : IR()

sealed class IRVerticesDescription() : IR()
data class IRPairVerticesDescription(
    val first: IRVertexName,
    val second: IRVertexName
) :
    IRVerticesDescription()

data class IRSingleVertexDescription(val vertex: IRVertexName) : IRVerticesDescription()

data class IRPathDescription(
    val fromVertexCond: IRVertexCondition,
    val pattern: IRPattern,
    val toVertexCond: IRVertexCondition
) : IR()

sealed class IRVertexCondition : IR()
data class IRVertexConditionPure(val vertex: IRVertexName) : IRVertexCondition()
data class IRVertexConditionWithId(val vertex: IRVertexName, val id: Int) : IRVertexCondition()

sealed class IRPattern : IR()
data class IRPatternConcat(val first: IRPattern, val second: IRPattern) : IRPattern()
data class IRPatternAlternative(val first: IRPattern, val second: IRPattern) : IRPattern()
data class IRPatternParenthesis(val pattern: IRPattern, val mod: IRPatternModifier?) : IRPattern()
data class IRPatternNonterminal(val nonterminal: String, val mod: IRPatternModifier?) : IRPattern()
data class IRPatternTerminal(val terminal: IRTerminal, val mod: IRPatternModifier?) : IRPattern()

sealed class IRPatternModifier : IR()
object IRPatternStar : IRPatternModifier()
object IRPatternPlus : IRPatternModifier()
object IRPatternOptional : IRPatternModifier()

sealed class IRTerminal : IR()
object IREpsilon : IRTerminal() {
    val text = "()"
}

data class IRTerminalPure(val terminal: String) : IRTerminal()

data class IRVertexName(val name: String) : IR()