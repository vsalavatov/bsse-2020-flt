package com.bsse2018.salavatov.flt.graphdb.evaluator

import com.bsse2018.salavatov.flt.graphdb.ir.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class ScriptEvaluationContextTest {
    private fun String.asResource() = ScriptEvaluationContextTest::class.java.classLoader.getResource(this)
    val datasetPath = "graph-dataset/".asResource()!!.path

    @Test
    fun `connect and list`() {
        val context = ScriptEvaluationContext()
        context.evaluate(IRStatementConnect('"' + datasetPath + '"'))
        val result = context.evaluate(IRStatementList) as ResultOutput
        assertEquals(7, result.text.trim().split("\n").size)
    }

    @Test
    fun `throws on list without connect`() {
        val context = ScriptEvaluationContext()
        assertThrows<NotConnectedException> { context.evaluate(IRStatementList) }
    }

    @Test
    fun `throws on select without connect`() {
        val context = ScriptEvaluationContext()
        assertThrows<NotConnectedException> {
            context.evaluate(
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
            )
        }
    }

    @Nested
    inner class ParserIntegrated {
        @Test
        fun `fullgraph a*`() {
            checkOneOutput(
                """
                CONNECT TO "${datasetPath}";
                S = a*;
                SELECT COUNT (u, v) FROM "fullgraph_10" WHERE u--|S|->v;
            """.trimIndent(),
                "100"
            )
        }

        @Test
        fun `fullgraph a* projected`() {
            checkOneOutput(
                """
                CONNECT TO "${datasetPath}";
                S = a*;
                SELECT COUNT u FROM "fullgraph_10" WHERE u--|S|->v;
            """.trimIndent(),
                "10"
            )
        }

        @Test
        fun `fullgraph b+`() {
            checkOneOutput(
                """
                CONNECT TO "${datasetPath}";
                SELECT COUNT (u, v) FROM "fullgraph_10" WHERE u--|b+|->v;
            """.trimIndent(),
                "0"
            )
        }

        @Test
        fun `fullgraph b+ exist`() {
            checkOneOutput(
                """
                CONNECT TO "${datasetPath}";
                SELECT EXISTS (u, v) FROM "fullgraph_10" WHERE u--|b+|->v;
            """.trimIndent(),
                "no"
            )
        }

        @Test
        fun `fullgraph a+ exist`() {
            checkOneOutput(
                """
                CONNECT TO "${datasetPath}";
                SELECT EXISTS (u, v) FROM "fullgraph_10" WHERE u--|a+|->v;
            """.trimIndent(),
                "yes"
            )
        }

        @Test
        fun `fullgraph cbs`() {
            checkOneOutput(
                """
                CONNECT TO "${datasetPath}";
                S = () | a S b S;
                SELECT COUNT (u, v) FROM "fullgraph_10" WHERE u--|S|->v;
            """.trimIndent(),
                "10"
            )
        }

        @Test
        fun `custom cbs`() {
            checkOneOutput(
                """
                CONNECT TO "${datasetPath}";
                S = () | a S b S;
                SELECT COUNT (u, v) FROM "custom_cbs" WHERE u--|S|->v;
            """.trimIndent(),
                "16"
            )
        }

        @Test
        fun `custom cbs print`() {
            checkOneOutput(
                """
                CONNECT TO "${datasetPath}";
                S = () | a S b S;
                SELECT (u, v) FROM "custom_cbs" WHERE u--|S|->v;
            """.trimIndent(),
                """
                (0, 5)
                (0, 4)
                (1, 5)
                (1, 3)
                (1, 0)
                (1, 4)
                (3, 0)
                (3, 5)
                (3, 4)
                (0, 0)
                (1, 1)
                (2, 2)
                (3, 3)
                (4, 4)
                (5, 5)
                (6, 6)
            """.trimIndent(),
                linesAsSets = true
            )
        }

        @Test
        fun `inherently ambiguous grammar`() {
            checkOneOutput(
                """
                CONNECT TO "${datasetPath}";
                Fa = a Ia d | a Fa d;
                Ia = b c | b Ia c;
                Fb = L R;
                L = a b | a L b;
                R = c d | c R d;
                SELECT (u, v) FROM "inherently_ambiguous_grammar_graph" WHERE u--|Fa | Fb|->v;
            """.trimIndent(),
                """
                (0, 8)
                (0, 7)
                (2, 8)
                (2, 7)
            """.trimIndent(),
                linesAsSets = true
            )
        }

        @Test
        fun `inherently ambiguous grammar inverted order`() {
            checkOneOutput(
                """
                CONNECT TO "${datasetPath}";
                Fa = a Ia d | a Fa d;
                Ia = b c | b Ia c;
                Fb = L R;
                L = a b | a L b;
                R = c d | c R d;
                SELECT (v, u) FROM "inherently_ambiguous_grammar_graph" WHERE u--|Fa | Fb|->v;
            """.trimIndent(),
                """
                (8, 0)
                (7, 0)
                (8, 2)
                (7, 2)
            """.trimIndent(),
                linesAsSets = true
            )
        }

        @Test
        fun `inherently ambiguous grammar projection`() {
            checkOneOutput(
                """
                CONNECT TO "${datasetPath}";
                Fa = a Ia d | a Fa d;
                Ia = b c | b Ia c;
                Fb = L R;
                L = a b | a L b;
                R = c d | c R d;
                SELECT v FROM "inherently_ambiguous_grammar_graph" WHERE u--|Fa | Fb|->v;
            """.trimIndent(),
                """
                8
                7
            """.trimIndent(),
                linesAsSets = true
            )
        }

        @Test
        fun `inherently ambiguous grammar id predicate`() {
            checkOneOutput(
                """
                CONNECT TO "${datasetPath}";
                Fa = a Ia d | a Fa d;
                Ia = b c | b Ia c;
                Fb = L R;
                L = a b | a L b;
                R = c d | c R d;
                SELECT (u, v) FROM "inherently_ambiguous_grammar_graph" WHERE (u: id=2)--|Fa | Fb|->v;
            """.trimIndent(),
                """
                (2, 8)
                (2, 7)
            """.trimIndent(),
                linesAsSets = true
            )
        }
    }

    private fun checkOneOutput(scriptText: String, expectedResult: String, linesAsSets: Boolean = false) {
        val script = parseScriptStrict(scriptText)
        val context = ScriptEvaluationContext()
        val result = context.evaluate(script).results.filter { it !is ResultUnit }
        assertTrue(result.size == 1)
        assertTrue(result[0] is ResultOutput)
        if (linesAsSets) {
            assertEquals(
                expectedResult.split("\n").sorted(),
                (result[0] as ResultOutput).text.trim().split("\n").sorted()
            )
        } else {
            assertEquals(expectedResult, (result[0] as ResultOutput).text.trim())
        }
    }
}