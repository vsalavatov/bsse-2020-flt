package com.bsse2018.salavatov.flt.grammars

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.File

internal class CYKTest {
    @Test
    fun `CYK brackets`() {
        assertTrue(CYKQuery(brackets(), ""))
        assertTrue(CYKQuery(brackets(), "ab"))
        assertTrue(CYKQuery(brackets(), "aabb"))
        assertTrue(CYKQuery(brackets(), "aababbab"))

        assertFalse(CYKQuery(brackets(), "aaaaa"))
        assertFalse(CYKQuery(brackets(), "aababbaba"))
        assertFalse(CYKQuery(brackets(), "abc"))
        assertFalse(CYKQuery(brackets(), "baba"))
    }

    @Test
    fun `CYK distinct AB`() {
        assertTrue(CYKQuery(distinctAB(), "a"))
        assertTrue(CYKQuery(distinctAB(), "b"))
        assertTrue(CYKQuery(distinctAB(), "aaabb"))
        assertTrue(CYKQuery(distinctAB(), "babaaaaabababaaaaa"))

        assertFalse(CYKQuery(distinctAB(), ""))
        assertFalse(CYKQuery(distinctAB(), "ab"))
        assertFalse(CYKQuery(distinctAB(), "ba"))
        assertFalse(CYKQuery(distinctAB(), "abba"))
        assertFalse(CYKQuery(distinctAB(), "bababaaabb"))
    }

    companion object {
        fun brackets() = ContextFreeGrammar.fromStrings(arrayOf("S a S b S", "S eps")).toChomskyNormalForm()
        fun distinctAB() = ContextFreeGrammar.fromStrings(
            arrayOf(
                "S T", "S U",
                "T V a T", "T V a V", "T T a V",
                "U V b U", "U V b V", "U U b V",
                "V a V b V", "V b V a V", "V eps"
            )
        ).toChomskyNormalForm()
    }
}