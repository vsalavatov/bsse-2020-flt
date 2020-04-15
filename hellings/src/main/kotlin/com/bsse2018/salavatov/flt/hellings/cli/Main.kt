package com.bsse2018.salavatov.flt.hellings.cli

import com.bsse2018.salavatov.flt.grammars.CYKQuery
import com.bsse2018.salavatov.flt.grammars.ContextFreeGrammar
import com.bsse2018.salavatov.flt.grammars.EmptyLanguageException
import com.bsse2018.salavatov.flt.grammars.HellingsQuery
import com.bsse2018.salavatov.flt.utils.graphFromStrings
import java.io.File
import java.lang.Exception
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if (args.size != 3) {
        println("Arguments: <grammar file> <graph file> <output file>")
        return
    }
    val rawGrammar = File(args[0]).readLines().filter { it.isNotEmpty() }
    val grammar = ContextFreeGrammar.fromStrings(rawGrammar)
    val rawGraph = File(args[1]).readLines().filter { it.isNotEmpty() }

    try {
        val graph = graphFromStrings(rawGraph)
        val wcnf = grammar.toWeakChomskyNormalForm()
        val hellings = HellingsQuery(graph, wcnf)

        File(args[2]).also {
            it.writeText(wcnf.dumpAsStrings().joinToString("\n"))
            it.appendText("\n")
            it.appendText(hellings.map { "${it.first} ${it.second}" }.joinToString("\n"))
        }
    } catch (e: Exception) {
        println("ERROR: ${e.message}")
    }
}