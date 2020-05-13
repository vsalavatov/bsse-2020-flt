package com.bsse2018.salavatov.flt.algorithms

import com.bsse2018.salavatov.flt.automata.PushDownAutomaton
import com.bsse2018.salavatov.flt.grammars.ContextFreeGrammar.Companion.Epsilon
import com.bsse2018.salavatov.flt.utils.Graph
import org.la4j.matrix.SparseMatrix
import org.la4j.matrix.sparse.CRSMatrix
import java.util.*

fun CFPQTensorQuery(graph: Graph, grammar: PushDownAutomaton, epsilonRepr: String = Epsilon): Set<Pair<Int, Int>> {
    val grammarSize = grammar.automaton.size
    val graphSize = graph.size

    val symbols = grammar.symbols
    val epsEdges = grammar.automaton.mapIndexed { u, edges ->
        edges.filter { it.first == epsilonRepr }
            .map { Pair(u, it.second) }
    }.flatten()

    val grammarMatrices = mutableMapOf<String, SparseMatrix>()
    val graphMatrices = mutableMapOf<String, SparseMatrix>()

    val startFinishToNonTerm = mutableMapOf<Pair<Int, Int>, String>()
    grammar.startStates.forEach { (nonTerm, u) ->
        grammar.finishStates[nonTerm]?.forEach { v ->
            startFinishToNonTerm[u to v] = nonTerm
        }
    }

    symbols.forEach { sym ->
        grammarMatrices[sym] = CRSMatrix(grammarSize, grammarSize)
        graphMatrices[sym] = CRSMatrix(graphSize, graphSize)
    }

    grammar.automaton.forEachIndexed { u, edges ->
        edges.filter { it.first != epsilonRepr }
            .forEach { (sym, v) ->
                grammarMatrices[sym]!![u, v] = 1.0
            }
    }
    graph.forEachIndexed { u, edges ->
        edges.forEach { (sym, v) ->
            graphMatrices[sym]!![u, v] = 1.0
        }
    }

    do {
        var changed = false
        val totalProduct = CRSMatrix(grammarSize * graphSize, grammarSize * graphSize)
        symbols.forEach { sym ->
            val gramMt = grammarMatrices[sym]!!
            val graphMt = graphMatrices[sym]!!
            gramMt.eachNonZero { i, j, _ ->
                graphMt.eachNonZero { u, v, _ ->
                    totalProduct[i * graphSize + u, j * graphSize + v] = 1.0
                }
            }
        }
        epsEdges.forEach { (u, v) ->
            graph.indices.forEach { grU ->
                totalProduct[u * graphSize + grU, v * graphSize + grU] = 1.0
            }
        }
        val tc = transitiveClosure(totalProduct)
        tc.eachNonZero { i, j, _ ->
            val grammarU = i / graphSize
            val grammarV = j / graphSize
            val graphU = i % graphSize
            val graphV = j % graphSize
            startFinishToNonTerm[grammarU to grammarV]?.let { nonTerm ->
                val mt = graphMatrices[nonTerm]!!
                if (mt.get(graphU, graphV) == 0.0) {
                    mt[graphU, graphV] = 1.0
                    changed = true
                }
            }
        }
    } while (changed)

    val result = mutableSetOf<Pair<Int, Int>>()
    graphMatrices[grammar.start]!!.eachNonZero { i, j, _ ->
        result.add(i to j)
    }
    return result
}

private fun transitiveClosurePow(matrix: CRSMatrix): SparseMatrix {
    return matrix.add(CRSMatrix.identity(matrix.columns())).power(matrix.columns()).toSparseMatrix()
}

private fun transitiveClosure(matrix: CRSMatrix): CRSMatrix {
    val queue = LinkedList<Int>()
    val visited = Array(matrix.rows()) { false }
    val result = CRSMatrix(matrix.rows(), matrix.columns())
    for (u in matrix.iteratorOfNonZeroRows()) {
        visited.fill(false)
        matrix.eachNonZeroInRow(u) { v, _ ->
            if (!visited[v]) {
                visited[v] = true
                queue.add(v)
                result[u, v] = 1.0
            }
        }
        while (queue.isNotEmpty()) {
            val curU = queue.pop()
            matrix.eachNonZeroInRow(curU) { v, _ ->
                if (!visited[v]) {
                    visited[v] = true
                    queue.add(v)
                    result[u, v] = 1.0
                }
            }
        }
    }
    return result
}