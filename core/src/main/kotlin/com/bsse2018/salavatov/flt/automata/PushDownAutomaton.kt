package com.bsse2018.salavatov.flt.automata

import com.bsse2018.salavatov.flt.grammars.ContextFreeGrammar.Companion.Epsilon
import com.bsse2018.salavatov.flt.utils.*

data class PushDownAutomaton(
    val automaton: Graph,
    val startStates: Map<String, Int>,
    val finishStates: Map<String, List<Int>>,
    val start: String
) {
    companion object {
        fun fromStrings(desc: List<String>): PushDownAutomaton {
            return PDABuilder().apply {
                desc.forEach { addRule(it) }
            }.build()
        }
    }
}

internal class PDABuilder {
    val automaton: MutableGraph = mutableListOf()
    val startStates: MutableMap<String, Int> = hashMapOf()
    val finishStates: MutableMap<String, Int> = hashMapOf()
    var start = ""

    fun addRule(regexp: String): PDABuilder {
        val st = GrammarRegexpParser.parseGrammarRegexp(regexp)
        if (start == "")
            start = st.from
        insert(
            startStates.getOrPut(start, ::newNode),
            finishStates.getOrPut(start, ::newNode),
            st.rhs
        )
        return this
    }

    fun insert(start: Int, finish: Int, node: GrammarRegexpNode) {
        when (node) {
            is GRNSequence -> {
                var curStart = start
                for (i in 0 until node.sequence.size - 1) {
                    val newStart = newNode()
                    insert(start, newStart, node.sequence[i])
                    curStart = newStart
                }
                insert(curStart, finish, node.sequence.last())
            }
            is GRNAlternatives -> {
                node.alternatives.forEach {
                    insert(start, finish, it)
                }
            }
            is GRNStar -> {
                val begin = newNode()
                val end = newNode()
                automaton[start].add(Pair(Epsilon, begin))
                automaton[end].add(Pair(Epsilon, finish))
                automaton[end].add(Pair(Epsilon, begin))
                automaton[begin].add(Pair(Epsilon, end))
                insert(begin, end, node.nested)
            }
            is GRNUnit -> {
                automaton[start].add(Pair(node.to, finish))
            }
            else -> {
                throw Exception("Unexpected grammar node type. That shouldn't have happened...")
            }
        }
    }

    private fun newNode(): Int {
        val index = automaton.size
        automaton.add(mutableListOf())
        return index
    }

    fun build(): PushDownAutomaton {
        return PushDownAutomaton(
            automaton,
            startStates,
            finishStates.mapValues { listOf(it.value) },
            start
        )
    }
}