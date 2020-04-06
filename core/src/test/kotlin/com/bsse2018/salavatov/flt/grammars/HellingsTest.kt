package com.bsse2018.salavatov.flt.grammars

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class HellingsTest {
    @Test
    fun brackets() {
        val graph = arrayOf(
            arrayOf(Pair("a", 1)),
            arrayOf(Pair("a", 2), Pair("b", 5)),
            arrayOf(Pair("b", 3), Pair("b", 5)),
            arrayOf(Pair("b", 4), Pair("a", 6)),
            arrayOf(),
            arrayOf(),
            arrayOf(Pair("b", 0))
        )
        assertEquals(
            HellingsQuery(graph, bracketsGrammar()),
            hashSetOf(
                Pair(0, 5), Pair(0, 4),
                Pair(1, 5), Pair(1, 3),
                Pair(1, 0), Pair(1, 4),
                Pair(3, 0), Pair(3, 5), Pair(3, 4),
                Pair(0, 0), Pair(1, 1), Pair(2, 2), Pair(3, 3), Pair(4, 4), Pair(5, 5), Pair(6, 6)
            )
        )
    }

    @Test
    fun `worst case`() {
        val graph = arrayOf(
            arrayOf(Pair("a", 1)),
            arrayOf(Pair("a", 2)),
            arrayOf(Pair("a", 0), Pair("b", 3)),
            arrayOf(Pair("b", 2))
        )
        val grammar = ContextFreeGrammar.fromStrings(
            arrayOf(
                "S A B", "S A S1",
                "S1 S B",
                "A a",
                "B b"
            )
        ).toWeakChomskyNormalForm()
        assertEquals(
            HellingsQuery(graph, grammar),
            hashSetOf(Pair(1, 3), Pair(0, 2), Pair(2, 3), Pair(1, 2), Pair(0, 3), Pair(2, 2))
        )
    }

    companion object {
        fun bracketsGrammar() = ContextFreeGrammar.fromStrings(
            arrayOf(
                "S eps",
                "S a S b",
                "S S S"
            )
        ).toWeakChomskyNormalForm()
    }
}