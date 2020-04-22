package com.bsse2018.salavatov.flt.grammars

import com.bsse2018.salavatov.flt.algorithms.CYKQuery
import com.bsse2018.salavatov.flt.grammars.TestDataCollection.correctBracketSequenceAmbiguousGrammar
import com.bsse2018.salavatov.flt.grammars.TestDataCollection.correctBracketSequenceGrammar
import com.bsse2018.salavatov.flt.grammars.TestDataCollection.distinctNumberABGrammar
import com.bsse2018.salavatov.flt.grammars.TestDataCollection.inherentlyAmbiguousGrammar
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class CYKTest {
    @Test
    fun `correct bracket sequence`() {
        val brackets = correctBracketSequenceGrammar().toChomskyNormalForm()
        assertTrue(CYKQuery(brackets, ""))
        assertTrue(CYKQuery(brackets, "ab"))
        assertTrue(CYKQuery(brackets, "aabb"))
        assertTrue(CYKQuery(brackets, "aababbab"))

        assertFalse(CYKQuery(brackets, "aaaaa"))
        assertFalse(CYKQuery(brackets, "aababbaba"))
        assertFalse(CYKQuery(brackets, "abc"))
        assertFalse(CYKQuery(brackets, "baba"))
    }

    @Test
    fun `correct bracket sequence ambiguous`() {
        val brackets = correctBracketSequenceAmbiguousGrammar().toChomskyNormalForm()
        assertTrue(CYKQuery(brackets, ""))
        assertTrue(CYKQuery(brackets, "ab"))
        assertTrue(CYKQuery(brackets, "aabb"))
        assertTrue(CYKQuery(brackets, "aababbab"))

        assertFalse(CYKQuery(brackets, "aaaaa"))
        assertFalse(CYKQuery(brackets, "aababbaba"))
        assertFalse(CYKQuery(brackets, "abc"))
        assertFalse(CYKQuery(brackets, "baba"))
    }

    @Test
    fun `distinct ABs`() {
        val distinctAB = distinctNumberABGrammar().toChomskyNormalForm()
        assertTrue(CYKQuery(distinctAB, "a"))
        assertTrue(CYKQuery(distinctAB, "b"))
        assertTrue(CYKQuery(distinctAB, "aaabb"))
        assertTrue(CYKQuery(distinctAB, "babaaaaabababaaaaa"))

        assertFalse(CYKQuery(distinctAB, ""))
        assertFalse(CYKQuery(distinctAB, "ab"))
        assertFalse(CYKQuery(distinctAB, "ba"))
        assertFalse(CYKQuery(distinctAB, "abba"))
        assertFalse(CYKQuery(distinctAB, "bababaaabb"))
    }

    @Test
    fun `inherently ambiguous grammar`() {
        val iag = inherentlyAmbiguousGrammar().toChomskyNormalForm()
        assertTrue(CYKQuery(iag, "abcd"))
        assertTrue(CYKQuery(iag, "abbccd"))
        assertTrue(CYKQuery(iag, "aabcdd"))
        assertTrue(CYKQuery(iag, "aaabbccddd"))
        assertTrue(CYKQuery(iag, "aabbcd"))
        assertTrue(CYKQuery(iag, "aabbcccddd"))

        assertFalse(CYKQuery(iag, ""))
        assertFalse(CYKQuery(iag, "ab"))
        assertFalse(CYKQuery(iag, "ad"))
        assertFalse(CYKQuery(iag, "bc"))
        assertFalse(CYKQuery(iag, "aaabbcdd"))
    }
}