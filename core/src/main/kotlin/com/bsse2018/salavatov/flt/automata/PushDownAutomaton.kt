package com.bsse2018.salavatov.flt.automata

import com.bsse2018.salavatov.flt.grammars.ContextFreeGrammar.Companion.Epsilon
import com.bsse2018.salavatov.flt.grammars.ContextFreeGrammar.Companion.isNonTerminal
import com.bsse2018.salavatov.flt.utils.*
import java.util.*

data class PushDownAutomaton(
    val automaton: Graph,
    val startStates: Map<String, Int>,
    val finishStates: Map<String, List<Int>>,
    val start: String
) {

    val symbols: Set<String>
        get() {
            val result = mutableSetOf<String>()
            automaton.forEach { edges ->
                edges.forEach { (sym, _) ->
                    if (sym != Epsilon) result.add(sym)
                }
            }
            startStates.forEach { (nonTerm, _) ->
                result.add(nonTerm)
            }
            return result
        }

    fun epsilonProducers(): Set<String> {
        val epsProducing = mutableSetOf<String>()
        val dependants = mutableMapOf<String, MutableSet<String>>()

        val enqueued = mutableSetOf<String>()
        val queue = LinkedList<String>()

        startStates.forEach {
            queue.add(it.key)
            enqueued.add(it.key)
        }

        while (queue.isNotEmpty()) {
            val curNonTerm = queue.pop()
            enqueued.remove(curNonTerm)
            if (epsProducing.contains(curNonTerm)) continue

            val start = startStates[curNonTerm] ?: continue
            val bfs = LinkedList<Int>()
            val visited = Array<Boolean>(automaton.size) { false }
            bfs.add(start)
            visited[start] = true
            while (bfs.isNotEmpty()) {
                val v = bfs.pop()
                automaton[v]
                    .filter {
                        it.first == Epsilon || (isNonTerminal(it.first) && epsProducing.contains(it.first))
                    }
                    .forEach { (_, to) ->
                        if (!visited[to]) {
                            bfs.add(to)
                            visited[to] = true
                        }
                    }
                automaton[v]
                    .filter {
                        isNonTerminal(it.first) && !epsProducing.contains(it.first)
                    }
                    .forEach {
                        dependants.getOrPut(it.first, { mutableSetOf() }).add(curNonTerm)
                    }
            }
            if (finishStates[curNonTerm]?.any { visited[it] } == true) {
                epsProducing.add(curNonTerm)
                dependants[curNonTerm]?.forEach {
                    if (!enqueued.contains(it)) {
                        queue.add(it)
                        enqueued.add(it)
                    }
                }
                dependants.remove(curNonTerm)
            }
        }

        return epsProducing
    }

    companion object {
        fun fromStrings(desc: List<String>): PushDownAutomaton {
            return PDABuilder().apply {
                desc.forEach { addRule(it) }
            }.build()
        }
    }
}

class PDABuilder {
    val automaton: MutableGraph = mutableListOf()
    val startStates: MutableMap<String, Int> = hashMapOf()
    val finishStates: MutableMap<String, Int> = hashMapOf()
    var start = ""

    fun addRule(regexp: String): PDABuilder {
        val st = GrammarRegexpParser.parseGrammarRegexp(regexp)
        if (start == "")
            start = st.from
        insert(
            startStates.getOrPut(st.from, ::newNode),
            finishStates.getOrPut(st.from, ::newNode),
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
                    insert(curStart, newStart, node.sequence[i])
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

    fun newNode(): Int {
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

    fun copy(): PDABuilder {
        val that = this
        return PDABuilder().apply {
            startStates.putAll(that.startStates)
            finishStates.putAll(that.finishStates)
            that.automaton.forEach { edges ->
                automaton.add(mutableListOf())
                edges.forEach { edge ->
                    automaton.last().add(edge.copy())
                }
            }
        }
    }
}