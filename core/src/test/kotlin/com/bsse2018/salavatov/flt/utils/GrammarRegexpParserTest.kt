package com.bsse2018.salavatov.flt.utils

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GrammarRegexpParserTest {
    @Test
    fun `S (a S b)* | eps`() {
        val rule = "S (a S b)* | eps"
        assertEquals(
            GRNDeclaration(
                "S",
                GRNAlternatives(
                    listOf(
                        GRNStar(
                            GRNSequence(
                                listOf(
                                    GRNUnit("a"), GRNUnit("S"), GRNUnit("b")
                                )
                            )
                        ),
                        GRNUnit("eps")
                    )
                )
            ),
            GrammarRegexpParser.parseGrammarRegexp(rule)
        )
    }

    @Test
    fun `S a*`() {
        val rule = "S a*"
        assertEquals(
            GRNDeclaration("S", GRNStar(GRNUnit("a"))),
            GrammarRegexpParser.parseGrammarRegexp(rule)
        )
    }

    @Test
    fun `S (a b)* | (S | eps) | (S*)`() {
        val rule = "S (a b)* | (S | eps) | (S*)"
        assertEquals(
            GRNDeclaration(
                "S", GRNAlternatives(
                    listOf(
                        GRNStar(GRNSequence(listOf(GRNUnit("a"), GRNUnit("b")))),
                        GRNAlternatives(listOf(GRNUnit("S"), GRNUnit("eps"))),
                        GRNStar(GRNUnit("S"))
                    )
                )
            ),
            GrammarRegexpParser.parseGrammarRegexp(rule)
        )
    }
}