package com.bsse2018.salavatov.flt.querylanganalyzer.cli

import com.bsse2018.salavatov.flt.algorithms.CFPQTensorQuery
import com.bsse2018.salavatov.flt.algorithms.CYKQuery
import com.bsse2018.salavatov.flt.automata.PushDownAutomaton
import com.bsse2018.salavatov.flt.querylang.QueryLanguage
import com.bsse2018.salavatov.flt.utils.graphFromStrings
import com.bsse2018.salavatov.flt.utils.toAdjacencyMatrix
import java.io.File
import java.lang.Exception

fun main(args: Array<String>) {
    if (args.size != 1) {
        println("Arguments: <program tokens file>")
        return
    }

    try {
        val tokens = File(args[0]).readLines()
            .map { it.trim() }.filter { it.isNotEmpty() }
            .flatMap { it.split(" ").filter { it.isNotEmpty() } }
        println(CYKQuery(QueryLanguage, tokens))
    } catch (e: Exception) {
        println("ERROR: ${e.message}")
    }
}