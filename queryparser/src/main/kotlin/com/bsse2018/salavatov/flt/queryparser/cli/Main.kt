package com.bsse2018.salavatov.flt.queryparser.cli

import antlr.QueryLanguageLexer
import antlr.QueryLanguageParser
import com.bsse2018.salavatov.dotdsl.DotTree
import com.bsse2018.salavatov.dotdsl.dotTree
import com.bsse2018.salavatov.dotdsl.dotTree.`--`
import org.antlr.v4.runtime.*
import org.antlr.v4.runtime.CharStreams.fromStream
import org.antlr.v4.runtime.atn.ATNConfigSet
import org.antlr.v4.runtime.dfa.DFA
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.TerminalNode
import java.io.FileInputStream
import java.io.InputStream
import java.io.OutputStream
import java.io.PrintStream
import java.lang.System.exit
import java.util.*

object Config {
    var dotFileLocation: String? = null
    var inputStream = System.`in`
    fun parse(args: Array<String>) {
        var i = 0
        while (i < args.size) {
            when {
                args[i] == "--help" -> {
                    println("Arguments: [--dot <path>] [script file]")
                    println("If no script file is specified then script is read from standard input stream.")
                    exit(0)
                }
                args[i] == "--dot" -> {
                    i++
                    if (i == args.size)
                        error("Specify dot file location")
                    dotFileLocation = args[i]
                    i++
                }
                else -> {
                    inputStream = FileInputStream(args[i])
                    i++
                }
            }
        }
    }
}

fun QueryLanguageParser.toDot(node: ParseTree?): DotTree = dotTree {
    node!!
    if (node is TerminalNode) {
        label = (node).text
    } else {
        node as ParserRuleContext
        label = ruleNames[node.ruleIndex]
        node.children.forEach {
            this `--` toDot(it)
        }
    }
}

class ParseError(desc: String) : Exception(desc)

fun translate2Dot(input: InputStream, output: OutputStream) {
    val lexer = QueryLanguageLexer(fromStream(input))
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
    val ast = parser.script()
    parser.toDot(ast).print(PrintStream(output))
}

fun main(args: Array<String>) {
    Config.parse(args)
    try {
        translate2Dot(
            Config.inputStream,
            Config.dotFileLocation?.let { filename -> PrintStream(filename) } ?: System.out)
    } catch (pe: ParseError) {
        println("Parse error: ${pe.message}")
    } catch (e: Exception) {
        println("ERROR: ${e.message}")
    }
}