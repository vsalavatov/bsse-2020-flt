package com.bsse2018.salavatov.flt.graphdb.ir

import QueryLanguageLexer
import QueryLanguageParser
import com.bsse2018.salavatov.flt.queryparser.cli.ParseError
import org.antlr.v4.runtime.*
import org.antlr.v4.runtime.atn.ATNConfigSet
import org.antlr.v4.runtime.dfa.DFA
import java.util.*

fun parseScriptStrict(text: String): IRScript {
    val lexer = QueryLanguageLexer(CharStreams.fromStream(text.byteInputStream()))
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
            throw ParseError("Parse error")
        }

        override fun syntaxError(
            recognizer: Recognizer<*, *>?,
            offendingSymbol: Any?,
            line: Int,
            charPositionInLine: Int,
            msg: String?,
            e: RecognitionException?
        ) {
            throw ParseError("Parse error: $msg")
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
            throw ParseError("Parse error (ambiguity)")
        }

        override fun reportContextSensitivity(
            recognizer: Parser?,
            dfa: DFA?,
            startIndex: Int,
            stopIndex: Int,
            prediction: Int,
            configs: ATNConfigSet?
        ) {
            throw ParseError("Parse error")
        }

    })
    val scriptNode = parser.script()
    return IRQueryLanguageVisitor.visitScript(scriptNode)
}