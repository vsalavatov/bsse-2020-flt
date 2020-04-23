package com.bsse2018.salavatov.flt.grammars

import com.bsse2018.salavatov.flt.algorithms.CYKQuery
import com.bsse2018.salavatov.flt.grammars.TestDataCollection.correctBracketSequenceAmbiguousGrammar
import com.bsse2018.salavatov.flt.grammars.TestDataCollection.correctBracketSequenceGrammar
import com.bsse2018.salavatov.flt.grammars.TestDataCollection.distinctNumberABGrammar
import com.bsse2018.salavatov.flt.grammars.TestDataCollection.inherentlyAmbiguousGrammar
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class CYKTest {
    private fun String.listify() = map { it.toString() }

    @Test
    fun `correct bracket sequence`() {
        val brackets = correctBracketSequenceGrammar().toChomskyNormalForm()
        assertTrue(CYKQuery(brackets, "".listify()))
        assertTrue(CYKQuery(brackets, "ab".listify()))
        assertTrue(CYKQuery(brackets, "aabb".listify()))
        assertTrue(CYKQuery(brackets, "aababbab".listify()))

        assertFalse(CYKQuery(brackets, "aaaaa".listify()))
        assertFalse(CYKQuery(brackets, "aababbaba".listify()))
        assertFalse(CYKQuery(brackets, "abc".listify()))
        assertFalse(CYKQuery(brackets, "baba".listify()))
    }

    @Test
    fun `correct bracket sequence ambiguous`() {
        val brackets = correctBracketSequenceAmbiguousGrammar().toChomskyNormalForm()
        assertTrue(CYKQuery(brackets, "".listify()))
        assertTrue(CYKQuery(brackets, "ab".listify()))
        assertTrue(CYKQuery(brackets, "aabb".listify()))
        assertTrue(CYKQuery(brackets, "aababbab".listify()))

        assertFalse(CYKQuery(brackets, "aaaaa".listify()))
        assertFalse(CYKQuery(brackets, "aababbaba".listify()))
        assertFalse(CYKQuery(brackets, "abc".listify()))
        assertFalse(CYKQuery(brackets, "baba".listify()))
    }

    @Test
    fun `distinct ABs`() {
        val distinctAB = distinctNumberABGrammar().toChomskyNormalForm()
        assertTrue(CYKQuery(distinctAB, "a".listify()))
        assertTrue(CYKQuery(distinctAB, "b".listify()))
        assertTrue(CYKQuery(distinctAB, "aaabb".listify()))
        assertTrue(CYKQuery(distinctAB, "babaaaaabababaaaaa".listify()))

        assertFalse(CYKQuery(distinctAB, "".listify()))
        assertFalse(CYKQuery(distinctAB, "ab".listify()))
        assertFalse(CYKQuery(distinctAB, "ba".listify()))
        assertFalse(CYKQuery(distinctAB, "abba".listify()))
        assertFalse(CYKQuery(distinctAB, "bababaaabb".listify()))
    }

    @Test
    fun `inherently ambiguous grammar`() {
        val iag = inherentlyAmbiguousGrammar().toChomskyNormalForm()
        assertTrue(CYKQuery(iag, "abcd".listify()))
        assertTrue(CYKQuery(iag, "abbccd".listify()))
        assertTrue(CYKQuery(iag, "aabcdd".listify()))
        assertTrue(CYKQuery(iag, "aaabbccddd".listify()))
        assertTrue(CYKQuery(iag, "aabbcd".listify()))
        assertTrue(CYKQuery(iag, "aabbcccddd".listify()))

        assertFalse(CYKQuery(iag, "".listify()))
        assertFalse(CYKQuery(iag, "ab".listify()))
        assertFalse(CYKQuery(iag, "ad".listify()))
        assertFalse(CYKQuery(iag, "bc".listify()))
        assertFalse(CYKQuery(iag, "aaabbcdd".listify()))
    }
}