package com.bsse2018.salavatov.graphdb.automata

import dk.brics.automaton.RegExp
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class AutomataUtilsTest {

    @DisplayName("Regexp")
    @Nested
    inner class RegexpTest {
        @Test
        fun `regexp to minimal DFA 1`() {
            val automaton = RegExp("a(aa)*").toMinimalDFA()
            assert(automaton.isDeterministic)
            assert(automaton.numberOfStates == 2)
            assert(automaton.getShortestExample(true) == "a")
            assert(automaton.run("aaa"))
            assert(automaton.run("aaaaa"))
            assert(!automaton.run("b"))
        }

        @Test
        fun `regexp to minimal DFA 2`() {
            val automaton = RegExp("(00|01|10|11)*").toMinimalDFA()
            assert(automaton.isDeterministic)
            assert(automaton.numberOfStates == 2)
            assert(automaton.run(""))
            assert(!automaton.run("0"))
            assert(!automaton.run("1"))
            assert(automaton.run("00"))
            assert(automaton.run("0010"))
            assert(automaton.run("111111"))
            assert(!automaton.run("lol"))
        }
    }

    @DisplayName("Intersection")
    @Nested
    inner class Intersection {
        @Test
        fun `intersection test 1`() {
            val a = RegExp("00(10)*0").toMinimalDFA()
            val b = RegExp("(00|101)*").toAutomaton()
            val c = intersect(a, b)
            assert(c.run("0010100"))
            assert(!c.run("001010100"))
            assert(!c.run("000"))
        }
    }
}