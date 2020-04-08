package com.bsse2018.salavatov.flt.grammars

internal class HellingsTest : CFPQCommonTestSuite() {
    override fun runQuery(graph: Array<Array<Pair<String, Int>>>, wcnf: ContextFreeGrammar): HashSet<Pair<Int, Int>> =
        HellingsQuery(graph, wcnf)
}