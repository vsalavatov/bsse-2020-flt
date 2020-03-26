package com.bsse2018.salavatov.flt.automata

import dk.brics.automaton.Automaton
import dk.brics.automaton.RegExp

fun RegExp.toMinimalDFA(): Automaton = this.toAutomaton(true)

fun intersect(a: Automaton, b: Automaton): Automaton = a.intersection(b)