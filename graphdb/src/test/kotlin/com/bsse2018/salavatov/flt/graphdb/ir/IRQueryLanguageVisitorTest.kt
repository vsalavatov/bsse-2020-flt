package com.bsse2018.salavatov.flt.graphdb.ir

import QueryLanguageLexer
import QueryLanguageParser
import com.bsse2018.salavatov.flt.queryparser.cli.ParseError
import org.antlr.v4.runtime.*
import org.antlr.v4.runtime.atn.ATNConfigSet
import org.antlr.v4.runtime.dfa.DFA
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

internal class IRQueryLanguageVisitorTest {
    private fun parse(text: String): IRScript {
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

    @Test
    fun `empty script`() {
        assertEquals(
            IRScript(),
            parse("")
        )
    }

    @Test
    fun `incorrect script`() {
        assertThrows<ParseError> {
            parse("as af  a agkrjgio ajws")
        }
    }

    @Test
    fun `connect statement`() {
        assertEquals(
            IRScript(IRStatementConnect("\"/home/graphs/\"")),
            parse(
                """
                CONNECT TO "/home/graphs/";
            """.trimIndent()
            )
        )
        assertEquals(
            IRScript(IRStatementConnect("\"/home/graphs/\\\"_123$\"")),
            parse(
                """
                CONNECT TO "/home/graphs/\"_123$";
            """.trimIndent()
            )
        )
        assertEquals(
            IRScript(IRStatementConnect("\"\"")),
            parse(
                """
                CONNECT TO "";
            """.trimIndent()
            )
        )
    }

    @Test
    fun `no semicolon throws`() {
        assertThrows<ParseError> {
            parse("CONNECT TO \"path\"")
        }
    }

    @Test
    fun `list statement`() {
        assertEquals(
            IRScript(IRStatementList),
            parse(
                """
                LIST GRAPHS;
            """.trimIndent()
            )
        )
    }

    @Test
    fun `multiple connect statements`() {
        assertEquals(
            IRScript(
                IRStatementConnect("\"/home/graphs/\""),
                IRStatementConnect("\"https://graphdbs.com/\"")
            ),
            parse(
                """
                CONNECT TO "/home/graphs/";
                CONNECT TO "https://graphdbs.com/";
            """.trimIndent()
            )
        )
    }

    @Test
    fun `connect and list statements`() {
        assertEquals(
            IRScript(
                IRStatementConnect("\"/home/graphs/old/\""),
                IRStatementList
            ),
            parse(
                """
                CONNECT TO "/home/graphs/old/";
                LIST GRAPHS;
            """.trimIndent()
            )
        )
    }

    @Test
    fun `simple pattern declaration`() {
        assertEquals(
            IRScript(
                IRStatementPatternDeclaration(
                    "S", IRPatternTerminal(IREpsilon, null)
                )
            ),
            parse(
                """
                S = ();
            """.trimIndent()
            )
        )
    }

    @Test
    fun `sophisticated pattern declaration`() {
        assertEquals(
            IRScript(
                IRStatementPatternDeclaration(
                    "V",
                    IRPatternConcat(
                        IRPatternConcat(
                            IRPatternConcat(
                                IRPatternConcat(
                                    IRPatternParenthesis(
                                        IRPatternConcat(
                                            IRPatternParenthesis(
                                                IRPatternAlternative(
                                                    IRPatternNonterminal("S", null),
                                                    IRPatternTerminal(IRTerminalPure("eps"), null)
                                                ),
                                                null
                                            ),
                                            IRPatternTerminal(IRTerminalPure("b"), null)
                                        ),
                                        mod = IRPatternStar
                                    ),
                                    IRPatternParenthesis(
                                        IRPatternAlternative(
                                            IRPatternNonterminal("S", null),
                                            IRPatternTerminal(IREpsilon, null)
                                        ), IRPatternOptional
                                    )
                                ),
                                IRPatternTerminal(IRTerminalPure("a"), IRPatternPlus)
                            ),
                            IRPatternTerminal(IRTerminalPure("b"), IRPatternOptional)
                        ),
                        IRPatternTerminal(IRTerminalPure("c"), IRPatternStar)
                    )
                )
            ),
            parse(
                """
                V = ((S | eps) b)* (S | ())? a+ b? c*;
            """.trimIndent()
            )
        )
    }

    @Test
    fun `incorrect pattern`() {
        assertThrows<ParseError> {
            parse("V = ((S | eps) b)* (S | ())? a+ b? c*))))));")
        }
    }

    @Test
    fun `select statement`() {
        assertEquals(
            IRScript(
                IRStatementSelect(
                    IRVerticesExpression(IRSingleVertexDescription(IRVertexName("u"))),
                    IRFromExpression("\"graph-example.txt\""),
                    IRWhereExpression(
                        IRPathDescription(
                            IRVertexConditionPure(IRVertexName("u")),
                            IRPatternNonterminal("S", null),
                            IRVertexConditionWithId(IRVertexName("v"), 15)
                        )
                    )
                )
            ),
            parse("""SELECT u FROM "graph-example.txt" WHERE u--|S|->(v: id=15);""")
        )
    }

    @Test
    fun `select exists statement`() {
        assertEquals(
            IRScript(
                IRStatementSelect(
                    IRExistsExpression(IRPairVerticesDescription(IRVertexName("u"), IRVertexName("v"))),
                    IRFromExpression("\"graph-example.txt\""),
                    IRWhereExpression(
                        IRPathDescription(
                            IRVertexConditionPure(IRVertexName("u")),
                            IRPatternNonterminal("S", null),
                            IRVertexConditionPure(IRVertexName("v"))
                        )
                    )
                )
            ),
            parse("""SELECT EXISTS (u,v) FROM "graph-example.txt" WHERE u--|S|->v;""")
        )
    }

    @Test
    fun `select count statement`() {
        assertEquals(
            IRScript(
                IRStatementSelect(
                    IRCountExpression(IRPairVerticesDescription(IRVertexName("u"), IRVertexName("v"))),
                    IRFromExpression("\"graph-example.txt\""),
                    IRWhereExpression(
                        IRPathDescription(
                            IRVertexConditionPure(IRVertexName("u")),
                            IRPatternNonterminal("S", null),
                            IRVertexConditionPure(IRVertexName("v"))
                        )
                    )
                )
            ),
            parse("""SELECT COUNT (u,v) FROM "graph-example.txt" WHERE u--|S|->v;""")
        )
    }
}