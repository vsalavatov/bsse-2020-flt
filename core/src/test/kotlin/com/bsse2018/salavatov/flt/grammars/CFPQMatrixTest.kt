package com.bsse2018.salavatov.flt.grammars

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CFPQMatrixTest : CFPQCommonTestSuite() {
    override fun runQuery(graph: Array<Array<Pair<String, Int>>>, wcnf: ContextFreeGrammar): HashSet<Pair<Int, Int>> =
        CFPQMatrixQuery(graph, wcnf)
}