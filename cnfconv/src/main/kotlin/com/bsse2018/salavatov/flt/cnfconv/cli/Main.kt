package com.bsse2018.salavatov.flt.cnfconv.cli

import com.bsse2018.salavatov.flt.grammars.ContextFreeGrammar
import com.bsse2018.salavatov.flt.grammars.EmptyLanguageException
import java.io.File
import java.lang.Exception

fun main(args: Array<String>) {
    if (args.size != 2) {
        println("Arguments: <input file> <output file>")
        return
    }
    try {
        val rawGrammar = File(args[0]).readLines().filter { it.isNotEmpty() }
        val grammar = ContextFreeGrammar.fromStrings(rawGrammar)
        val cnf = grammar.toChomskyNormalForm()
        File(args[1]).writeText(cnf.dumpAsStrings().joinToString("\n"))
    } catch (e: EmptyLanguageException) {
        System.err.println("stderr: Language is empty. Nothing to write")
    } catch (e: Exception) {
        println("ERROR: ${e.message}")
    }
}