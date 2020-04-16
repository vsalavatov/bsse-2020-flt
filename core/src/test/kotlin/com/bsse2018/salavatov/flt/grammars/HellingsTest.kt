package com.bsse2018.salavatov.flt.grammars

import com.bsse2018.salavatov.flt.utils.Graph

internal class HellingsTest : CFPQCommonTestSuite() {
    override fun runQuery(graph: Graph, wcnf: ContextFreeGrammar): Set<Pair<Int, Int>> =
        HellingsQuery(graph, wcnf)
}