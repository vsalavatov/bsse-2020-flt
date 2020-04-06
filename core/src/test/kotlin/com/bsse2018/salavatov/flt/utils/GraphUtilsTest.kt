package com.bsse2018.salavatov.flt.utils

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class GraphUtilsTest {
    @Test
    fun graphFromStrings() {
        assertArrayEquals(
            graphFromStrings(arrayOf("0 a 1", "1 b 2")),
            arrayOf(
                arrayOf(Pair("a", 1)),
                arrayOf(Pair("b", 2)),
                arrayOf()
            )
        )

        assertThrows(GraphInvalidFormatException::class.java) { graphFromStrings(arrayOf("asdasd")) }
        assertThrows(GraphInvalidFormatException::class.java) { graphFromStrings(arrayOf("1 a ")) }
        assertThrows(GraphInvalidFormatException::class.java) { graphFromStrings(arrayOf("A b C")) }
    }
}