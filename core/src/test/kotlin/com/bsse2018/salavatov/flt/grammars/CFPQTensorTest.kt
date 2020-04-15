package com.bsse2018.salavatov.flt.grammars

import com.bsse2018.salavatov.flt.automata.PushDownAutomaton
import com.bsse2018.salavatov.flt.utils.Graph
import org.junit.jupiter.api.Assertions.*

internal class CFPQTensorTest : CFPQCommonTestSuite() {
    override fun runQuery(graph: Graph, wcnf: ContextFreeGrammar): Set<Pair<Int, Int>> {
        val pda = PushDownAutomaton.fromStrings(wcnf.dumpAsStrings())
        return CFPQTensorQuery(graph, pda)
    }
}