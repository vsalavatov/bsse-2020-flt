package com.bsse2018.salavatov.flt.algorithms

import com.bsse2018.salavatov.flt.grammars.ContextFreeGrammar
import com.bsse2018.salavatov.flt.utils.Graph
import org.la4j.matrix.SparseMatrix
import org.la4j.matrix.sparse.CRSMatrix

fun CFPQMatrixQuery(graph: Graph, wcnf: ContextFreeGrammar): HashSet<Pair<Int, Int>> {
    val nonTerminals = ContextFreeGrammar.NodeAccountant().let {
        it.consume(wcnf)
        it.nonTerminals
    }
    val nodes = graph.size

    val matrices = hashMapOf<String, SparseMatrix>()
    nonTerminals.forEach {
        matrices[it] = CRSMatrix(nodes, nodes)
    }

    val epsilonRules = wcnf.rules.filter { it.isEpsilon() }
    val symRules = wcnf.rules
        .filter { it.isTerminal() && !it.isEpsilon() }
        .groupBy { it.to[0] }
    val nonTermRules = wcnf.rules.filter { !it.isTerminal() }

    for (u in graph.indices) {
        epsilonRules.forEach { rule ->
            matrices[rule.from]!![u, u] = 1.0
        }
        for ((sym, v) in graph[u]) {
            symRules[sym]?.forEach { rule ->
                matrices[rule.from]!![u, v] = 1.0
            }
        }
    }

    var changed = true
    while (changed) {
        changed = false
        nonTermRules.forEach { rule ->
            val nterm1 = rule.to[0]
            val nterm2 = rule.to[1]
            val product = matrices[nterm1]!!.multiply(matrices[nterm2]!!).toSparseMatrix()
            val result = matrices[rule.from]!!
            product.eachNonZero { i, j, _ ->
                if (result.isZeroAt(i, j)) {
                    changed = true
                    result[i, j] = 1.0
                }
            }
        }
    }

    val result = hashSetOf<Pair<Int, Int>>()
    matrices[wcnf.start]!!.eachNonZero { i, j, _ ->
        result.add(Pair(i, j))
    }
    return result
}