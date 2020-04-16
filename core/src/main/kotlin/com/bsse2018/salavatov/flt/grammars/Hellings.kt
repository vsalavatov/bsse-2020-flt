package com.bsse2018.salavatov.flt.grammars

import com.bsse2018.salavatov.flt.utils.Graph
import java.util.*
import kotlin.collections.MutableSet

fun HellingsQuery(graph: Graph, wcnf: ContextFreeGrammar): MutableSet<Pair<Int, Int>> {
    val dp = List(graph.size) { List(graph.size) { mutableSetOf<String>() } }
    val queue = ArrayDeque<Triple<String, Int, Int>>()
    val epsilonRules = wcnf.rules.filter { it.isEpsilon() }
    val symRules = wcnf.rules.filter { it.isTerminal() }

    graph.forEachIndexed { u, edges ->
        epsilonRules.forEach { rule ->
            if (!dp[u][u].contains(rule.from)) {
                dp[u][u].add(rule.from)
                queue.add(Triple(rule.from, u, u))
            }
        }
        edges.forEach { edge ->
            val v = edge.second
            symRules.filter { edge.first == it.to[0] }
                .forEach { rule ->
                    if (!dp[u][v].contains(rule.from)) {
                        dp[u][v].add(rule.from)
                        queue.add(Triple(rule.from, u, v))
                    }
                }
        }
    }

    while (queue.isNotEmpty()) {
        val (nonTerm, u, v) = queue.pop()
        for (ufrom in dp.indices) {
            val postponedAdd = mutableSetOf<String>()
            dp[ufrom][u].forEach { nonTermBefore ->
                wcnf.rules
                    .filter { it.to == listOf(nonTermBefore, nonTerm) }
                    .forEach { rule ->
                        if (!dp[ufrom][v].contains(rule.from) && !postponedAdd.contains(rule.from)) {
                            postponedAdd.add(rule.from)
                            queue.add(Triple(rule.from, ufrom, v))
                        }
                    }
            }
            dp[ufrom][v].addAll(postponedAdd)
        }
        for (vto in dp.indices) {
            val postponedAdd = mutableSetOf<String>()
            dp[v][vto].forEach { nonTermAfter ->
                wcnf.rules
                    .filter { it.to == listOf(nonTerm, nonTermAfter) }
                    .forEach { rule ->
                        if (!dp[u][vto].contains(rule.from) && !postponedAdd.contains(rule.from)) {
                            postponedAdd.add(rule.from)
                            queue.add(Triple(rule.from, u, vto))
                        }
                    }
            }
            dp[u][vto].addAll(postponedAdd)
        }
    }

    val result = mutableSetOf<Pair<Int, Int>>()
    for (u in dp.indices) {
        for (v in dp.indices) {
            if (dp[u][v].contains(wcnf.start))
                result.add(Pair(u, v))
        }
    }
    return result
}