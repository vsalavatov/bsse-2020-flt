package com.bsse2018.salavatov.flt.grammars

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import com.bsse2018.salavatov.flt.utils.Graph

abstract class CFPQCommonTestSuite {
    abstract fun runQuery(graph: Graph, wcnf: ContextFreeGrammar): HashSet<Pair<Int, Int>>

    @Test
    fun `correct bracket sequence`() {
        val graph = arrayOf(
            arrayOf(Pair("a", 1)),
            arrayOf(Pair("a", 2), Pair("b", 5)),
            arrayOf(Pair("b", 3), Pair("b", 5)),
            arrayOf(Pair("b", 4), Pair("a", 6)),
            arrayOf(),
            arrayOf(),
            arrayOf(Pair("b", 0))
        )
        val grammar = TestDataCollection.correctBracketSequenceGrammar().toWeakChomskyNormalForm()
        Assertions.assertEquals(
            hashSetOf(
                Pair(0, 5), Pair(0, 4),
                Pair(1, 5), Pair(1, 3), Pair(1, 0), Pair(1, 4),
                Pair(3, 0), Pair(3, 5), Pair(3, 4),
                Pair(0, 0), Pair(1, 1), Pair(2, 2), Pair(3, 3), Pair(4, 4), Pair(5, 5), Pair(6, 6)
            ),
            runQuery(graph, grammar)
        )
    }

    @Test
    fun `correct bracket sequence ambiguous`() {
        val graph = arrayOf(
            arrayOf(Pair("a", 1)),
            arrayOf(Pair("a", 2), Pair("b", 5)),
            arrayOf(Pair("b", 3), Pair("b", 5)),
            arrayOf(Pair("b", 4), Pair("a", 6)),
            arrayOf(),
            arrayOf(),
            arrayOf(Pair("b", 0))
        )
        val grammar = TestDataCollection.correctBracketSequenceAmbiguousGrammar().toWeakChomskyNormalForm()
        Assertions.assertEquals(
            hashSetOf(
                Pair(0, 5), Pair(0, 4),
                Pair(1, 5), Pair(1, 3), Pair(1, 0), Pair(1, 4),
                Pair(3, 0), Pair(3, 5), Pair(3, 4),
                Pair(0, 0), Pair(1, 1), Pair(2, 2), Pair(3, 3), Pair(4, 4), Pair(5, 5), Pair(6, 6)
            ),
            runQuery(graph, grammar)
        )
    }

    @Test
    fun `worst case`() {
        val graph = TestDataCollection.CYKWorstCaseGraph()
        val grammar = TestDataCollection.CYKWorstCaseGrammar().toWeakChomskyNormalForm()
        Assertions.assertEquals(
            hashSetOf(Pair(1, 3), Pair(0, 2), Pair(2, 3), Pair(1, 2), Pair(0, 3), Pair(2, 2)),
            runQuery(graph, grammar)
        )
    }

    @Test
    fun `empty graph`() {
        val graph = arrayOf<Array<Pair<String, Int>>>()
        val grammar = TestDataCollection.correctBracketSequenceGrammar().toWeakChomskyNormalForm()
        Assertions.assertEquals(
            hashSetOf<Pair<Int, Int>>(),
            runQuery(graph, grammar)
        )
    }

    @Test
    fun multigraph() {
        val graph = arrayOf(
            arrayOf(Pair("a", 1), Pair("a", 1), Pair("b", 1)),
            arrayOf(Pair("a", 2), Pair("b", 5)),
            arrayOf(Pair("b", 3), Pair("b", 5)),
            arrayOf(Pair("b", 4), Pair("a", 6)),
            arrayOf(Pair("a", 4), Pair("b", 7)),
            arrayOf(),
            arrayOf(Pair("b", 0)),
            arrayOf(Pair("b", 8)),
            arrayOf(Pair("b", 9)),
            arrayOf()
        )
        val grammar = TestDataCollection.correctBracketSequenceAmbiguousGrammar().toWeakChomskyNormalForm()
        Assertions.assertEquals(
            hashSetOf(
                Pair(0, 5), Pair(0, 4), Pair(0, 7), Pair(0, 8), Pair(0, 9), Pair(0, 1), Pair(0, 3),
                Pair(1, 5), Pair(1, 3), Pair(1, 0), Pair(1, 4), Pair(1, 7), Pair(1, 8), Pair(1, 9),
                Pair(3, 0), Pair(3, 5), Pair(3, 4), Pair(3, 7), Pair(3, 8), Pair(3, 9), Pair(3, 1),
                Pair(4, 7), Pair(4, 8), Pair(4, 9),
                Pair(0, 0), Pair(1, 1), Pair(2, 2), Pair(3, 3), Pair(4, 4),
                Pair(5, 5), Pair(6, 6), Pair(7, 7), Pair(8, 8), Pair(9, 9)
            ),
            runQuery(graph, grammar)
        )
    }

    @Test
    fun `inherently ambiguous grammar`() {
        val graph = arrayOf(
            arrayOf(Pair("a", 2), Pair("b", 1)),
            arrayOf(),
            arrayOf(Pair("a", 2), Pair("b", 3)),
            arrayOf(Pair("b", 4)),
            arrayOf(Pair("c", 5)),
            arrayOf(Pair("c", 6), Pair("d", 8)),
            arrayOf(Pair("d", 7)),
            arrayOf(),
            arrayOf()
        )
        val grammar = TestDataCollection.inherentlyAmbiguousGrammar().toWeakChomskyNormalForm()
        Assertions.assertEquals(
            hashSetOf(
                Pair(0, 8), Pair(0, 7),
                Pair(2, 8), Pair(2, 7)
            ),
            runQuery(graph, grammar)
        )
    }
}