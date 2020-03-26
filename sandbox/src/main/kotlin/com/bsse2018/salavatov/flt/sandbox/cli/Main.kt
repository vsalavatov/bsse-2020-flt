package com.bsse2018.salavatov.flt.sandbox.cli

import com.bsse2018.salavatov.flt.grammars.ContextFreeGrammar.Rule
import com.bsse2018.salavatov.flt.grammars.ContextFreeGrammar
import com.bsse2018.salavatov.flt.grammars.EmptyLanguageException
import java.lang.Exception

fun main() {
    var start: String? = null
    val rules = hashSetOf<Rule>()
    var line: String?
    while (true) {
        line = readLine()?.trim() ?: break
        if (line.isEmpty()) continue
        val tokens = line.split(Regex("\\s+"))
        if (tokens.size <= 1) {
            println("Invalid format: at least 1 token must be presented on rhs")
            return
        }
        if (start == null) start = tokens[0]
        rules.add(Rule(tokens[0], tokens.drop(1).toTypedArray()))
    }
    if (start == null) {
        println("No rules specified!")
        return
    }
    val grammar = ContextFreeGrammar(start, rules)
    try {
        val cnf = grammar.toChomskyNormalForm()
        val cnfRules = cnf.rules.toTypedArray()
        cnfRules.sortBy {
            if (it.from == cnf.start) {
                ""
            } else {
                it.from
            }
        }
        cnfRules.forEach {
            println("${it.from} ${it.to.joinToString(" ")}")
        }
    } catch (e: EmptyLanguageException) {
        System.err.println("stderr: Language is empty. Nothing to write")
    } catch (e: Exception) {
        println("ERROR: ${e.message}")
    }
}