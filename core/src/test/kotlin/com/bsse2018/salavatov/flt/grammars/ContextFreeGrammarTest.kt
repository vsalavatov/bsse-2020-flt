package com.bsse2018.salavatov.flt.grammars

import com.bsse2018.salavatov.flt.grammars.ContextFreeGrammar.Companion.Epsilon
import com.bsse2018.salavatov.flt.grammars.ContextFreeGrammar.Rule
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Assertions.*

internal class ContextFreeGrammarTest {
    @Test
    fun `shrinkLongRules 1`() {
        val grammar = ContextFreeGrammar(
            "S",
            hashSetOf(
                Rule("S", arrayOf("a", "b", "c", "d"))
            )
        )
        val result = grammar.shrinkLongRules()
        assertTrue(result.hasOnlySmallRules())
        assertEquals(result.rules.size, 3)
        assertTrue(result.rules.contains(Rule("S", arrayOf("S0", "d"))))
        assertTrue(result.rules.contains(Rule("S0", arrayOf("S1", "c"))))
        assertTrue(result.rules.contains(Rule("S1", arrayOf("a", "b"))))
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
                "S", rulesFromStrings(arrayOf("S a b", "S eps"))
            ).hasOnlySmallRules()
        )
        assertFalse(
            ContextFreeGrammar(
                "S", rulesFromStrings(arrayOf("S a b N", "S eps", "N eps"))
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
        assertEquals(result, CFGUnitReduced())
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
                }.toTypedArray())
            }.toHashSet()
        )
        assertEquals(
            grammar.generatingRules(), rulesFromStrings(
                arrayOf(
                    "S d", "S S0 d",
                    "S0 S1",
                    "S1 A B", "S1 B", "S1 A",
                    "B A",
                    "A a"
                )
            )
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
                }.toTypedArray())
            }.toHashSet()
        )
        assertEquals(
            grammar.reduceNonGeneratingRules(), ContextFreeGrammar(
                "S", rulesFromStrings(
                    arrayOf(
                        "S d", "S S0 d",
                        "S0 S1",
                        "S1 A B", "S1 B", "S1 A",
                        "B A",
                        "A a"
                    )
                )
            )
        )
    }

    @Test
    fun reduceUnreachable() {
        val grammar = ContextFreeGrammar(
            "S", rulesFromStrings(
                arrayOf(
                    "S X",
                    "X x", "X X",
                    "AAA3 X"
                )
            )
        )
        assertEquals(
            grammar.reduceUnreachable(),
            ContextFreeGrammar(
                "S",
                rulesFromStrings(
                    arrayOf(
                        "S X",
                        "X x", "X X"
                    )
                )
            )
        )
    }

    @Test
    fun reduceLongTerminalRules() {
        val grammar = ContextFreeGrammar(
            "S", rulesFromStrings(
                arrayOf(
                    "S A A", "S A",
                    "A a B", "A x A",
                    "B b a"
                )
            )
        )
        assertEquals(
            grammar.reduceLongTerminalRules(), ContextFreeGrammar(
                "S",
                rulesFromStrings(
                    arrayOf(
                        "S A A", "S A",
                        "A S0 B", "A S1 A",
                        "B S2 S0",
                        "S0 a",
                        "S1 x",
                        "S2 b"
                    )
                )
            )
        )
    }

    @Test
    fun toChomskyNormalForm() {
        val brackets = CFGBrackets()
        val grammar = brackets.toChomskyNormalForm()
        assertEquals(
            grammar,
            ContextFreeGrammar(
                "S3", rulesFromStrings(
                    arrayOf(
                        "S3 S2 S0", "S3 S1 S", "S3 eps",
                        "S S1 S", "S S2 S0",
                        "S1 S2 S0",
                        "S2 S4 S", "S2 a",
                        "S0 b",
                        "S4 a"
                    )
                )
            )
        )
    }

    companion object {
        fun rulesFromStrings(desc: Array<String>) = desc.map {
            val tokens = it.trim().split(Regex("\\s+"))
            assert(tokens.size >= 2)
            Rule(tokens[0], tokens.drop(1).toTypedArray())
        }.toHashSet()

        fun CFGEps() = ContextFreeGrammar(
            "S", rulesFromStrings(
                arrayOf(
                    "S A B C d",
                    "A a", "A eps",
                    "B A C",
                    "C c", "C eps"
                )
            )
        )

        fun CFGEpsSmall() = ContextFreeGrammar(
            "S", rulesFromStrings(
                arrayOf(
                    "S S0 d",
                    "S0 S1 C",
                    "S1 A B",
                    "A a", "A eps",
                    "B A C",
                    "C c", "C eps"
                )
            )
        )

        fun CFGEpsReduced() = ContextFreeGrammar(
            "S", rulesFromStrings(
                arrayOf(
                    "S S0 d", "S d",
                    "S0 S1 C", "S0 C", "S0 S1",
                    "S1 A B", "S1 A", "S1 B",
                    "A a",
                    "B A C", "B A", "B C",
                    "C c"
                )
            )
        )

        fun CFGUnitReduced() = ContextFreeGrammar(
            "S", rulesFromStrings(
                arrayOf(
                    "S S0 d", "S d",
                    "S0 S1 C", "S0 c", "S0 A B", "S0 a", "S0 c", "S0 A C",
                    "S1 A B", "S1 a", "S1 A C", "S1 c",
                    "A a",
                    "B A C", "B a", "B c", "C c"
                )
            )
        )

        fun CFGBrackets() = ContextFreeGrammar(
            "S", rulesFromStrings(
                arrayOf(
                    "S a S b S", "S eps"
                )
            )
        )
    }
}