package com.bsse2018.salavatov.flt.grammars

import com.bsse2018.salavatov.flt.algorithms.CFPQMatrixQuery
import com.bsse2018.salavatov.flt.utils.Graph

internal class CFPQMatrixTest : CFPQCommonTestSuite() {
    override fun runQuery(graph: Graph, wcnf: ContextFreeGrammar): HashSet<Pair<Int, Int>> =
        CFPQMatrixQuery(graph, wcnf)
}