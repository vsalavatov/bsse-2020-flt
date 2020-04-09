package com.bsse2018.salavatov.flt.cfpqmatrix.cli

import com.bsse2018.salavatov.flt.grammars.*
import com.bsse2018.salavatov.flt.utils.graphFromStrings
import java.io.File
import java.lang.Exception

fun main(args: Array<String>) {
    if (args.size != 3) {
        println("Arguments: <grammar file> <graph file> <output file>")
        return
    }
    val rawGrammar = File(args[0]).readLines().filter { it.isNotEmpty() }.toTypedArray()
    val grammar = ContextFreeGrammar.fromStrings(rawGrammar)
    val rawGraph = File(args[1]).readLines().filter { it.isNotEmpty() }

    try {
        val graph = graphFromStrings(rawGraph.toTypedArray())
        val wcnf = grammar.toWeakChomskyNormalForm()
        val result = CFPQMatrixQuery(graph, wcnf)

        File(args[2]).also {
            it.writeText(wcnf.dumpAsStrings().joinToString("\n"))
            it.appendText("\n")
            it.appendText(result.map { "${it.first} ${it.second}" }.joinToString("\n"))
        }
    } catch (e: Exception) {
        println("ERROR: ${e.message}")
    }
}