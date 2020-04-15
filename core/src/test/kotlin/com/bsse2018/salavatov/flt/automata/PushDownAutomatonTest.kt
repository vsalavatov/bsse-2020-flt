package com.bsse2018.salavatov.flt.automata

import com.bsse2018.salavatov.flt.grammars.ContextFreeGrammar.Companion.Epsilon
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class PushDownAutomatonTest {
    @Nested
    inner class BuildFromStrings {
        @Test
        fun `FullGraph g1`() {
            val rules = listOf(
                "S a S",
                "S a"
            )
            val expected = PushDownAutomaton(
                listOf(
                    listOf(Pair("a", 2), Pair("a", 1)),
                    listOf(),
                    listOf(Pair("S", 1))
                ),
                mapOf("S" to 0),
                mapOf("S" to listOf(1)),
                "S"
            )
            val pda = PushDownAutomaton.fromStrings(rules)
            assertEquals(expected, pda)
        }

        @Test
        fun `Part of MemoryAliases g2`() {
            val rules = listOf(
                "V ((S | eps) b)* (S | eps)"
            )
            val expected = PushDownAutomaton(
                listOf(
                    listOf(Pair(Epsilon, 3)),
                    listOf(),
                    listOf(Pair("S", 1), Pair(Epsilon, 1)),
                    listOf(Pair(Epsilon, 4), Pair("S", 5), Pair(Epsilon, 5)),
                    listOf(Pair(Epsilon, 2), Pair(Epsilon, 3)),
                    listOf(Pair("b", 4))
                ),
                mapOf("V" to 0),
                mapOf("V" to listOf(1)),
                "V"
            )
            val pda = PushDownAutomaton.fromStrings(rules)
            assertEquals(expected, pda)
        }

        @Test
        fun `Worst case extended grammar`() {
            val pda = PushDownAutomaton.fromStrings(
                listOf("S a b | a S b")
            )
            val expected = PushDownAutomaton(
                listOf(
                    listOf("a" to 2, "a" to 3),
                    listOf(),
                    listOf("b" to 1),
                    listOf("S" to 4),
                    listOf("b" to 1)
                ),
                mapOf("S" to 0),
                mapOf("S" to listOf(1)),
                "S"
            )
            assertEquals(expected, pda)
        }
    }

    @Nested
    inner class EpsilonProducers {
        @Test
        fun `something 1`() {
            val rules = listOf(
                "V ((S | eps) b)* (S | eps)",
                "S S"
            )
            val pda = PushDownAutomaton.fromStrings(rules)
            assertEquals(setOf("V"), pda.epsilonProducers())
        }

        @Test
        fun `eps chain`() {
            val rules = listOf(
                "S a | E1",
                "E1 b | E2",
                "E2 lol | eps"
            )
            val pda = PushDownAutomaton.fromStrings(rules)
            assertEquals(setOf("S", "E1", "E2"), pda.epsilonProducers())
        }
    }
}