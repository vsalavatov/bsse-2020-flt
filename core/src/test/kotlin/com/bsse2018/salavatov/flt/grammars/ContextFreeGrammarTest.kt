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
                "S", hashSetOf(
                    Rule("S", arrayOf("a", "b")),
                    Rule("S", arrayOf(Epsilon))
                )
            ).hasOnlySmallRules()
        )
        assertFalse(
            ContextFreeGrammar(
                "S", hashSetOf(
                    Rule("S", arrayOf("a", "b", "N")),
                    Rule("S", arrayOf(Epsilon)),
                    Rule("N", arrayOf(Epsilon))
                )
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
            grammar.generatingRules(), hashSetOf(
                Rule("S", arrayOf("d")),
                Rule("S", arrayOf("S0", "d")),
                Rule("S0", arrayOf("S1")),
                Rule("S1", arrayOf("A", "B")),
                Rule("S1", arrayOf("B")),
                Rule("S1", arrayOf("A")),
                Rule("B", arrayOf("A")),
                Rule("A", arrayOf("a"))
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
                "S", hashSetOf(
                    Rule("S", arrayOf("d")),
                    Rule("S", arrayOf("S0", "d")),
                    Rule("S0", arrayOf("S1")),
                    Rule("S1", arrayOf("A", "B")),
                    Rule("S1", arrayOf("B")),
                    Rule("S1", arrayOf("A")),
                    Rule("B", arrayOf("A")),
                    Rule("A", arrayOf("a"))
                )
            )
        )
    }

    @Test
    fun reduceUnreachable() {
        val grammar = ContextFreeGrammar(
            "S", hashSetOf(
                Rule("S", arrayOf("X")),
                Rule("X", arrayOf("x")),
                Rule("X", arrayOf("X")),
                Rule("AAA3", arrayOf("X"))
            )
        )
        assertEquals(
            grammar.reduceUnreachable(),
            ContextFreeGrammar(
                "S",
                hashSetOf(
                    Rule("S", arrayOf("X")),
                    Rule("X", arrayOf("x")),
                    Rule("X", arrayOf("X"))
                )
            )
        )
    }

    @Test
    fun reduceLongTerminalRules() {
        val grammar = ContextFreeGrammar(
            "S", hashSetOf(
                Rule("S", arrayOf("A", "A")),
                Rule("S", arrayOf("A")),
                Rule("A", arrayOf("a", "B")),
                Rule("A", arrayOf("x", "A")),
                Rule("B", arrayOf("b", "a"))
            )
        )
        assertEquals(
            grammar.reduceLongTerminalRules(), ContextFreeGrammar(
                "S",
                hashSetOf(
                    Rule("S", arrayOf("A", "A")),
                    Rule("S", arrayOf("A")),
                    Rule("A", arrayOf("S0", "B")),
                    Rule("A", arrayOf("S1", "A")),
                    Rule("B", arrayOf("S2", "S0")),
                    Rule("S0", arrayOf("a")),
                    Rule("S1", arrayOf("x")),
                    Rule("S2", arrayOf("b"))
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
                "S3", hashSetOf(
                    Rule("S3", arrayOf("S2", "S0")),
                    Rule("S3", arrayOf("S1", "S")),
                    Rule("S3", arrayOf(Epsilon)),
                    Rule("S", arrayOf("S1", "S")),
                    Rule("S", arrayOf("S2", "S0")),
                    Rule("S1", arrayOf("S2", "S0")),
                    Rule("S2", arrayOf("S4", "S")),
                    Rule("S2", arrayOf("a")),
                    Rule("S0", arrayOf("b")),
                    Rule("S4", arrayOf("a"))
                )
            )
        )
    }

    companion object {
        fun CFGEps() = ContextFreeGrammar(
            "S", hashSetOf(
                Rule("S", arrayOf("A", "B", "C", "d")),
                Rule("A", arrayOf("a")),
                Rule("A", arrayOf(Epsilon)),
                Rule("B", arrayOf("A", "C")),
                Rule("C", arrayOf("c")),
                Rule("C", arrayOf(Epsilon))
            )
        )

        fun CFGEpsSmall() = ContextFreeGrammar(
            "S", hashSetOf(
                Rule("S", arrayOf("S0", "d")),
                Rule("S0", arrayOf("S1", "C")),
                Rule("S1", arrayOf("A", "B")),
                Rule("A", arrayOf("a")),
                Rule("A", arrayOf(Epsilon)),
                Rule("B", arrayOf("A", "C")),
                Rule("C", arrayOf("c")),
                Rule("C", arrayOf(Epsilon))
            )
        )

        fun CFGEpsReduced() = ContextFreeGrammar(
            "S", hashSetOf(
                Rule("S", arrayOf("S0", "d")),
                Rule("S", arrayOf("d")),
                Rule("S0", arrayOf("S1", "C")),
                Rule("S0", arrayOf("C")),
                Rule("S0", arrayOf("S1")),
                Rule("S1", arrayOf("A", "B")),
                Rule("S1", arrayOf("A")),
                Rule("S1", arrayOf("B")),
                Rule("A", arrayOf("a")),
                Rule("B", arrayOf("A", "C")),
                Rule("B", arrayOf("A")),
                Rule("B", arrayOf("C")),
                Rule("C", arrayOf("c"))
            )
        )

        fun CFGUnitReduced() = ContextFreeGrammar(
            "S", hashSetOf(
                Rule("S", arrayOf("S0", "d")),
                Rule("S", arrayOf("d")),
                Rule("S0", arrayOf("S1", "C")),
                Rule("S0", arrayOf("c")),
                Rule("S0", arrayOf("A", "B")),
                Rule("S0", arrayOf("a")),
                Rule("S0", arrayOf("c")),
                Rule("S0", arrayOf("A", "C")),
                Rule("S1", arrayOf("A", "B")),
                Rule("S1", arrayOf("a")),
                Rule("S1", arrayOf("c")),
                Rule("S1", arrayOf("A", "C")),
                Rule("A", arrayOf("a")),
                Rule("B", arrayOf("A", "C")),
                Rule("B", arrayOf("a")),
                Rule("B", arrayOf("c")),
                Rule("C", arrayOf("c"))
            )
        )

        fun CFGBrackets() = ContextFreeGrammar(
            "S", hashSetOf(
                Rule("S", arrayOf("a", "S", "b", "S")),
                Rule("S", arrayOf(Epsilon))
            )
        )
    }
}