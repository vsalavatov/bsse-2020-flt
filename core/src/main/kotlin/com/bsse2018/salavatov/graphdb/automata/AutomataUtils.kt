package com.bsse2018.salavatov.graphdb.automata

import dk.brics.automaton.Automaton
import dk.brics.automaton.RegExp

fun RegExp.toMinimalDFA(): Automaton = this.toAutomaton(true)

fun intersect(a: Automaton, b: Automaton) = a.intersection(b)