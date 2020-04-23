package com.bsse2018.salavatov.flt.grammars

import com.bsse2018.salavatov.flt.algorithms.CFPQTensorQuery
import com.bsse2018.salavatov.flt.automata.PushDownAutomaton
import com.bsse2018.salavatov.flt.utils.Graph
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CFPQTensorTest : CFPQCommonTestSuite() {
    override fun runQuery(graph: Graph, wcnf: ContextFreeGrammar): Set<Pair<Int, Int>> {
        val pda = PushDownAutomaton.fromStrings(wcnf.dumpAsStrings())
        return CFPQTensorQuery(graph, pda)
    }

    @Test
    fun `worst case, extended grammar`() {
        val pda = PushDownAutomaton.fromStrings(
            listOf(
                "S a b | a S b"
            )
        )
        assertEquals(
            setOf(Pair(1, 3), Pair(0, 2), Pair(2, 3), Pair(1, 2), Pair(0, 3), Pair(2, 2)),
            CFPQTensorQuery(
                TestDataCollection.CYKWorstCaseGraph(),
                pda
            )
        )
    }

    @Test
    fun `full graph`() {
        val pda = PushDownAutomaton.fromStrings(
            listOf(
                "S a*"
            )
        )
        val graph = listOf(
            listOf("a" to 1, "a" to 2),
            listOf("a" to 2, "a" to 3),
            listOf("a" to 3, "a" to 0),
            listOf("a" to 0, "a" to 1)
        )
        assertEquals(
            16,
            CFPQTensorQuery(graph, pda).size
        )
    }
}