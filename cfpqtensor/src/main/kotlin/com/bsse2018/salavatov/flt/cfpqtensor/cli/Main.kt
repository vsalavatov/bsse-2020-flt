package com.bsse2018.salavatov.flt.cfpqtensor.cli

import com.bsse2018.salavatov.flt.automata.PushDownAutomaton
import com.bsse2018.salavatov.flt.grammars.*
import com.bsse2018.salavatov.flt.utils.graphFromStrings
import com.bsse2018.salavatov.flt.utils.toAdjacencyMatrix
import java.io.File
import java.lang.Exception

fun main(args: Array<String>) {
    if (args.size != 3) {
        println("Arguments: <grammar file> <graph file> <output file>")
        return
    }
    val rawGrammar = File(args[0]).readLines().filter { it.isNotEmpty() }
    val rawGraph = File(args[1]).readLines().filter { it.isNotEmpty() }

    try {
        val graph = graphFromStrings(rawGraph)
        val pda = PushDownAutomaton.fromStrings(rawGrammar)
        val result = CFPQTensorQuery(graph, pda)
        val adjMatrix = pda.automaton.toAdjacencyMatrix()

        File(args[2]).also {
            it.writeText(adjMatrix.joinToString("\n") {
                it.joinToString(" ") {
                    if (it.isEmpty()) {
                        "."
                    } else {
                        it.toString()
                    }
                }
            })
            it.appendText("\n")
            it.appendText(result.joinToString("\n") { "${it.first} ${it.second}" })
        }
    } catch (e: Exception) {
        println("ERROR: ${e.message}")
    }
}