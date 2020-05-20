package com.bsse2018.salavatov.flt.graphdb.cli

import QueryLanguageLexer
import QueryLanguageParser
import com.bsse2018.salavatov.flt.graphdb.evaluator.ResultOutput
import com.bsse2018.salavatov.flt.graphdb.evaluator.ScriptEvaluationContext
import com.bsse2018.salavatov.flt.graphdb.ir.IRQueryLanguageVisitor
import com.bsse2018.salavatov.flt.queryparser.cli.ParseError
import org.antlr.v4.runtime.*
import org.antlr.v4.runtime.CharStreams.fromStream
import org.antlr.v4.runtime.atn.ATNConfigSet
import org.antlr.v4.runtime.dfa.DFA
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.TerminalNode
import java.io.*
import java.lang.Exception
import java.lang.System.exit
import java.util.*

fun makeScriptParser(inputStream: InputStream): QueryLanguageParser {
    val lexer = QueryLanguageLexer(fromStream(inputStream))
    val tokenStream = CommonTokenStream(lexer)
    val parser = QueryLanguageParser(tokenStream)
    parser.removeErrorListeners()
    parser.addErrorListener(object : ANTLRErrorListener {
        override fun reportAttemptingFullContext(
            recognizer: Parser?,
            dfa: DFA?,
            startIndex: Int,
            stopIndex: Int,
            conflictingAlts: BitSet?,
            configs: ATNConfigSet?
        ) {
            throw ParseError("Script cannot be parsed")
        }

        override fun syntaxError(
            recognizer: Recognizer<*, *>?,
            offendingSymbol: Any?,
            line: Int,
            charPositionInLine: Int,
            msg: String?,
            e: RecognitionException?
        ) {
            throw ParseError(msg ?: "Script cannot be parsed")
        }

        override fun reportAmbiguity(
            recognizer: Parser?,
            dfa: DFA?,
            startIndex: Int,
            stopIndex: Int,
            exact: Boolean,
            ambigAlts: BitSet?,
            configs: ATNConfigSet?
        ) {
            throw ParseError("Ambiguous script")
        }

        override fun reportContextSensitivity(
            recognizer: Parser?,
            dfa: DFA?,
            startIndex: Int,
            stopIndex: Int,
            prediction: Int,
            configs: ATNConfigSet?
        ) {
            throw ParseError("Script cannot be parsed")
        }

    })
    return parser
}

fun main(args: Array<String>) {
    if (args.size > 1) {
        println("Arguments: [script file]")
        return
    }
    fun ScriptEvaluationContext.evaluate(scriptStream: InputStream) {
        val parser = makeScriptParser(scriptStream)
        val scriptTree = parser.script()
        val script = IRQueryLanguageVisitor.visitScript(scriptTree)
        val result = evaluate(script).results.filterIsInstance<ResultOutput>()
        result.forEach {
            println(it.text)
        }
    }

    val context = ScriptEvaluationContext()
    if (args.size == 1) {
        try {
            val scriptStream = File(args[0]).inputStream()
            context.evaluate(scriptStream)
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    } else {
        do {
            print("> ")
            val line = readLine() ?: break
            try {
                val scriptStream = line.byteInputStream()
                context.evaluate(scriptStream)
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        } while (true)
    }
}