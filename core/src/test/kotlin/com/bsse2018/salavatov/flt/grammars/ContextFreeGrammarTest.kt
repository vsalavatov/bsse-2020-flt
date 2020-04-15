package com.bsse2018.salavatov.flt.grammars

import com.bsse2018.salavatov.flt.grammars.ContextFreeGrammar.Companion.Epsilon
import com.bsse2018.salavatov.flt.grammars.ContextFreeGrammar.Companion.rulesFromStrings
import com.bsse2018.salavatov.flt.grammars.ContextFreeGrammar.Rule
import com.bsse2018.salavatov.flt.grammars.TestDataCollection.correctBracketSequenceGrammar
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested

internal class ContextFreeGrammarTest {
    @Test
    fun `shrinkLongRules 1`() {
        val grammar = ContextFreeGrammar(
            "S",
            hashSetOf(
                Rule("S", listOf("a", "b", "c", "d"))
            )
        )
        val result = grammar.shrinkLongRules()
        assertTrue(result.hasOnlySmallRules())
        assertEquals(3, result.rules.size)
        assertTrue(result.rules.contains(Rule("S", listOf("S0", "d"))))
        assertTrue(result.rules.contains(Rule("S0", listOf("S1", "c"))))
        assertTrue(result.rules.contains(Rule("S1", listOf("a", "b"))))
    }

    @Test
    fun `shrinkLongRules 2`() {
        val grammar = CFGEps()
        val result = grammar.shrinkLongRules()
        assertTrue(result == CFGEpsSmall())
    }

    @Test
    fun hasOnlySmallRules() {
        assertTrue(
            ContextFreeGrammar(
                "S", rulesFromStrings(listOf("S a b", "S eps"))
            ).hasOnlySmallRules()
        )
        assertFalse(
            ContextFreeGrammar(
                "S", rulesFromStrings(listOf("S a b N", "S eps", "N eps"))
            ).hasOnlySmallRules()
        )
    }

    @Test
    fun epsilonProducers() {
        assertTrue(CFGEps().epsilonProducers() == hashSetOf("A", "B", "C"))
    }

    @Test
    fun reduceEpsilonRules() {
        val result = CFGEpsSmall().reduceEpsilonRules()
        assertTrue(result == CFGEpsReduced())
    }

    @Test
    fun isEpsilonReduced() {
        assertTrue(CFGEpsReduced().isEpsilonReduced())
        assertFalse(CFGEps().isEpsilonReduced())
        assertFalse(CFGEpsSmall().isEpsilonReduced())
    }

    @Test
    fun reduceUnitRules() {
        val result = CFGEpsReduced().reduceUnitRules()
        assertEquals(CFGUnitReduced(), result)
    }

    @Test
    fun isUnitReduced() {
        assertTrue(CFGUnitReduced().isUnitReduced())
        assertFalse(CFGEpsReduced().isUnitReduced())
    }

    @Test
    fun generatingRules() {
        val cfg = CFGEpsReduced()
        val grammar = ContextFreeGrammar(
            cfg.start,
            cfg.rules.map {
                Rule(it.from, it.to.map {
                    if (it == "c") {
                        "C"
                    } else {
                        it
                    }
                })
            }.toHashSet()
        )
        assertEquals(
            rulesFromStrings(
                listOf(
                    "S d", "S S0 d",
                    "S0 S1",
                    "S1 A B", "S1 B", "S1 A",
                    "B A",
                    "A a"
                )
            ),
            grammar.generatingRules()
        )
    }

    @Test
    fun reduceNonGeneratingRules() {
        val cfg = CFGEpsReduced()
        val grammar = ContextFreeGrammar(
            cfg.start,
            cfg.rules.map {
                Rule(it.from, it.to.map {
                    if (it == "c") {
                        "C"
                    } else {
                        it
                    }
                })
            }.toHashSet()
        )
        assertEquals(
            ContextFreeGrammar.fromStrings(
                listOf(
                    "S d", "S S0 d",
                    "S0 S1",
                    "S1 A B", "S1 B", "S1 A",
                    "B A",
                    "A a"
                )
            ),
            grammar.reduceNonGeneratingRules()
        )
    }

    @Test
    fun reduceUnreachable() {
        val grammar = ContextFreeGrammar.fromStrings(
            listOf(
                "S X",
                "X x", "X X",
                "AAA3 X"
            )
        )
        assertEquals(
            ContextFreeGrammar.fromStrings(
                listOf(
                    "S X",
                    "X x", "X X"
                )
            ),
            grammar.reduceUnreachable()
        )
    }

    @Test
    fun reduceLongTerminalRules() {
        val grammar = ContextFreeGrammar.fromStrings(
            listOf(
                "S A A", "S A",
                "A a B", "A x A",
                "B b a"
            )
        )
        assertEquals(
            ContextFreeGrammar.fromStrings(
                listOf(
                    "S A A", "S A",
                    "A S0 B", "A S1 A",
                    "B S2 S0",
                    "S0 a",
                    "S1 x",
                    "S2 b"
                )
            ),
            grammar.reduceLongTerminalRules()
        )
    }

    @Test
    fun toChomskyNormalForm() {
        val brackets = correctBracketSequenceGrammar()
        val grammar = brackets.toChomskyNormalForm()
        assertEquals(
            ContextFreeGrammar.fromStrings(
                listOf(
                    "S3 S2 S0", "S3 S1 S", "S3 eps",
                    "S S1 S", "S S2 S0",
                    "S1 S2 S0",
                    "S2 S4 S", "S2 a",
                    "S0 b",
                    "S4 a"
                )
            ),
            grammar
        )
    }

    @Test
    fun toWeakChomskyNormalForm() {
        val brackets = correctBracketSequenceGrammar()
        val grammar = brackets.toWeakChomskyNormalForm()
        assertEquals(
            ContextFreeGrammar.fromStrings(
                listOf(
                    "S eps", "S S0 S",
                    "S0 S1 S3",
                    "S1 S2 S",
                    "S2 a",
                    "S3 b"
                )
            ),
            grammar
        )
    }

    @Test
    fun dumpAsStrings() {
        val desc = listOf(
            "S X c", "S a S b S",
            "X c z", "X eps"
        )
        assertEquals(
            desc,
            ContextFreeGrammar.fromStrings(desc).dumpAsStrings()
        )
    }

    @Nested
    inner class CompanionMethods {
        @Test
        fun parseRule() {
            assertEquals(
                Rule("S", listOf("a", "S", "b", "S")),
                ContextFreeGrammar.parseRule("S a S b S")
            )
            assertThrows(InvalidFormatException::class.java) { ContextFreeGrammar.parseRule("S") }
            assertThrows(InvalidFormatException::class.java) { ContextFreeGrammar.parseRule("a S x") }
        }

        @Test
        fun rulesFromStrings() {
            assertEquals(
                hashSetOf<Rule>(
                    Rule("S", listOf("a", "b", "S")),
                    Rule("X", listOf("a", "S"))
                ),
                rulesFromStrings(listOf("S a b S", "X a S"))
            )
        }

        @Test
        fun fromStrings() {
            val cfg = ContextFreeGrammar.fromStrings(
                listOf(
                    "S A B C d",
                    "A a", "A eps",
                    "B A C",
                    "C c", "C eps"
                )
            )
            assertEquals("S", cfg.start)
            assertEquals(6, cfg.rules.size)
            assertTrue(cfg.rules.contains(Rule("B", listOf("A", "C"))))
            assertTrue(cfg.rules.contains(Rule("C", listOf(Epsilon))))
        }
    }

    companion object {
        fun CFGEps() = ContextFreeGrammar.fromStrings(
            listOf(
                "S A B C d",
                "A a", "A eps",
                "B A C",
                "C c", "C eps"
            )
        )

        fun CFGEpsSmall() = ContextFreeGrammar.fromStrings(
            listOf(
                "S S0 d",
                "S0 S1 C",
                "S1 A B",
                "A a", "A eps",
                "B A C",
                "C c", "C eps"
            )
        )

        fun CFGEpsReduced() = ContextFreeGrammar.fromStrings(
            listOf(
                "S S0 d", "S d",
                "S0 S1 C", "S0 C", "S0 S1",
                "S1 A B", "S1 A", "S1 B",
                "A a",
                "B A C", "B A", "B C",
                "C c"
            )
        )

        fun CFGUnitReduced() = ContextFreeGrammar.fromStrings(
            listOf(
                "S S0 d", "S d",
                "S0 S1 C", "S0 c", "S0 A B", "S0 a", "S0 c", "S0 A C",
                "S1 A B", "S1 a", "S1 A C", "S1 c",
                "A a",
                "B A C", "B a", "B c", "C c"
            )
        )
    }
}