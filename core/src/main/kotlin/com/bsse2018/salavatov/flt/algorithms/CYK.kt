package com.bsse2018.salavatov.flt.algorithms

import com.bsse2018.salavatov.flt.grammars.ContextFreeGrammar
import com.bsse2018.salavatov.flt.grammars.ContextFreeGrammar.Companion.Epsilon

fun CYKQuery(cnfGrammar: ContextFreeGrammar, query: List<String>): Boolean {
    if (query.isEmpty()) {
        return cnfGrammar.rules.contains(
            ContextFreeGrammar.Rule(
                cnfGrammar.start,
                listOf(Epsilon)
            )
        )
    }

    val dp = List(query.size) { List(query.size + 1) { mutableSetOf<String>() } }

    val symRules = cnfGrammar.rules.filter { it.isTerminal() && !it.to.contains(Epsilon) }
    val concatRules = cnfGrammar.rules.filter { !it.isTerminal() }

    symRules.forEach { rule ->
        val sym = rule.to[0]
        for (i in query.indices) {
            if (query[i] == sym)
                dp[i][i + 1].add(rule.from)
        }
    }

    for (len in 2..query.size) {
        for (left in 0..(query.size - len)) {
            val right = left + len
            concatRules.forEach { rule ->
                val n1 = rule.to[0]
                val n2 = rule.to[1]
                for (border in left until right) {
                    if (dp[left][border].contains(n1) && dp[border][right].contains(n2)) {
                        dp[left][right].add(rule.from)
                        break
                    }
                }
            }
        }
    }

    return dp[0][query.size].contains(cnfGrammar.start)
}