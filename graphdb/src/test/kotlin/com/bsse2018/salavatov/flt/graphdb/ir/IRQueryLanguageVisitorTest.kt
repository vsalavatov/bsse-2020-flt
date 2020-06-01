package com.bsse2018.salavatov.flt.graphdb.ir

import com.bsse2018.salavatov.flt.queryparser.cli.ParseError
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class IRQueryLanguageVisitorTest {
    @Test
    fun `empty script`() {
        assertEquals(
            IRScript(),
            parseScriptStrict("")
        )
    }

    @Test
    fun `incorrect script`() {
        assertThrows<ParseError> {
            parseScriptStrict("as af  a agkrjgio ajws")
        }
    }

    @Test
    fun `connect statement`() {
        assertEquals(
            IRScript(IRStatementConnect("\"/home/graphs/\"")),
            parseScriptStrict(
                """
                CONNECT TO "/home/graphs/";
            """.trimIndent()
            )
        )
        assertEquals(
            IRScript(IRStatementConnect("\"/home/graphs/\\\"_123$\"")),
            parseScriptStrict(
                """
                CONNECT TO "/home/graphs/\"_123$";
            """.trimIndent()
            )
        )
        assertEquals(
            IRScript(IRStatementConnect("\"\"")),
            parseScriptStrict(
                """
                CONNECT TO "";
            """.trimIndent()
            )
        )
    }

    @Test
    fun `no semicolon throws`() {
        assertThrows<ParseError> {
            parseScriptStrict("CONNECT TO \"path\"")
        }
    }

    @Test
    fun `list graphs statement`() {
        assertEquals(
            IRScript(IRStatementListGraphs()),
            parseScriptStrict(
                """
                LIST GRAPHS;
            """.trimIndent()
            )
        )
        assertEquals(
            IRScript(IRStatementListGraphs("\"~/db/graphs/\"")),
            parseScriptStrict(
                """
                LIST GRAPHS "~/db/graphs/";
            """.trimIndent()
            )
        )
    }

    @Test
    fun `list labels statement`() {
        assertEquals(
            IRScript(IRStatementListLabels("\"sample-graph\"")),
            parseScriptStrict(
                """
                LIST LABELS "sample-graph";
            """.trimIndent()
            )
        )
        assertThrows<ParseError> {
            parseScriptStrict(
                """
                LIST LABELS;
            """.trimIndent()
            )
        }
    }

    @Test
    fun `multiple connect statements`() {
        assertEquals(
            IRScript(
                IRStatementConnect("\"/home/graphs/\""),
                IRStatementConnect("\"https://graphdbs.com/\"")
            ),
            parseScriptStrict(
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
                IRStatementListGraphs()
            ),
            parseScriptStrict(
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
            parseScriptStrict(
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
            parseScriptStrict(
                """
                V = ((S | eps) b)* (S | ())? a+ b? c*;
            """.trimIndent()
            )
        )
    }

    @Test
    fun `incorrect pattern`() {
        assertThrows<ParseError> {
            parseScriptStrict("V = ((S | eps) b)* (S | ())? a+ b? c*))))));")
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
            parseScriptStrict("""SELECT u FROM "graph-example.txt" WHERE u--|S|->(v: id=15);""")
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
            parseScriptStrict("""SELECT EXISTS (u,v) FROM "graph-example.txt" WHERE u--|S|->v;""")
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
            parseScriptStrict("""SELECT COUNT (u,v) FROM "graph-example.txt" WHERE u--|S|->v;""")
        )
    }
}