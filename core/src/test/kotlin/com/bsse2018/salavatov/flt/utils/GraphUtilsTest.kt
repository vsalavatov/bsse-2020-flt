package com.bsse2018.salavatov.flt.utils

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class GraphUtilsTest {
    @Test
    fun graphFromStrings() {
        assertEquals(
            graphFromStrings(listOf("0 a 1", "1 b 2")),
            listOf(
                listOf(Pair("a", 1)),
                listOf(Pair("b", 2)),
                listOf()
            )
        )

        assertThrows(GraphInvalidFormatException::class.java) { graphFromStrings(listOf("asdasd")) }
        assertThrows(GraphInvalidFormatException::class.java) { graphFromStrings(listOf("1 a ")) }
        assertThrows(GraphInvalidFormatException::class.java) { graphFromStrings(listOf("A b C")) }
    }
}