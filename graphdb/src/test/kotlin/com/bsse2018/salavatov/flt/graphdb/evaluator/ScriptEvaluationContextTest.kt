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
            val script = parseScriptStrict(
                """
                CONNECT TO "${datasetPath}";
                S = a*;
                SELECT COUNT (u, v) FROM "fullgraph_10" WHERE u--|S|->v;
            """.trimIndent()
            )
            val context = ScriptEvaluationContext()
            val result = context.evaluate(script).results.filter { it !is ResultUnit }
            assertTrue(result.size == 1)
            assertTrue(result[0] is ResultOutput)
            assertEquals(100, (result[0] as ResultOutput).text.trim().toInt())
        }

        @Test
        fun `fullgraph a* projected`() {
            val script = parseScriptStrict(
                """
                CONNECT TO "${datasetPath}";
                S = a*;
                SELECT COUNT u FROM "fullgraph_10" WHERE u--|S|->v;
            """.trimIndent()
            )
            val context = ScriptEvaluationContext()
            val result = context.evaluate(script).results.filter { it !is ResultUnit }
            assertTrue(result.size == 1)
            assertTrue(result[0] is ResultOutput)
            assertEquals(10, (result[0] as ResultOutput).text.trim().toInt())
        }

        @Test
        fun `fullgraph b+`() {
            val script = parseScriptStrict(
                """
                CONNECT TO "${datasetPath}";
                SELECT COUNT (u, v) FROM "fullgraph_10" WHERE u--|b+|->v;
            """.trimIndent()
            )
            val context = ScriptEvaluationContext()
            val result = context.evaluate(script).results.filter { it !is ResultUnit }
            assertTrue(result.size == 1)
            assertTrue(result[0] is ResultOutput)
            assertEquals(0, (result[0] as ResultOutput).text.trim().toInt())
        }

        @Test
        fun `fullgraph b+ exist`() {
            val script = parseScriptStrict(
                """
                CONNECT TO "${datasetPath}";
                SELECT EXISTS (u, v) FROM "fullgraph_10" WHERE u--|b+|->v;
            """.trimIndent()
            )
            val context = ScriptEvaluationContext()
            val result = context.evaluate(script).results.filter { it !is ResultUnit }
            assertTrue(result.size == 1)
            assertTrue(result[0] is ResultOutput)
            assertEquals("no", (result[0] as ResultOutput).text.trim())
        }

        @Test
        fun `fullgraph a+ exist`() {
            val script = parseScriptStrict(
                """
                CONNECT TO "${datasetPath}";
                SELECT EXISTS (u, v) FROM "fullgraph_10" WHERE u--|a+|->v;
            """.trimIndent()
            )
            val context = ScriptEvaluationContext()
            val result = context.evaluate(script).results.filter { it !is ResultUnit }
            assertTrue(result.size == 1)
            assertTrue(result[0] is ResultOutput)
            assertEquals("yes", (result[0] as ResultOutput).text.trim())
        }

        @Test
        fun `fullgraph cbs`() {
            val script = parseScriptStrict(
                """
                CONNECT TO "${datasetPath}";
                S = () | a S b S;
                SELECT COUNT (u, v) FROM "fullgraph_10" WHERE u--|S|->v;
            """.trimIndent()
            )
            val context = ScriptEvaluationContext()
            val result = context.evaluate(script).results.filter { it !is ResultUnit }
            assertTrue(result.size == 1)
            assertTrue(result[0] is ResultOutput)
            assertEquals(10, (result[0] as ResultOutput).text.trim().toInt())
        }

        @Test
        fun `custom cbs`() {
            val script = parseScriptStrict(
                """
                CONNECT TO "${datasetPath}";
                S = () | a S b S;
                SELECT COUNT (u, v) FROM "custom_cbs" WHERE u--|S|->v;
            """.trimIndent()
            )
            val context = ScriptEvaluationContext()
            val result = context.evaluate(script).results.filter { it !is ResultUnit }
            assertTrue(result.size == 1)
            assertTrue(result[0] is ResultOutput)
            assertEquals(16, (result[0] as ResultOutput).text.trim().toInt())
        }

        @Test
        fun `custom cbs print`() {
            val script = parseScriptStrict(
                """
                CONNECT TO "${datasetPath}";
                S = () | a S b S;
                SELECT (u, v) FROM "custom_cbs" WHERE u--|S|->v;
            """.trimIndent()
            )
            val context = ScriptEvaluationContext()
            val result = context.evaluate(script).results.filter { it !is ResultUnit }
            assertTrue(result.size == 1)
            assertTrue(result[0] is ResultOutput)
            assertEquals(
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
            """.trimIndent().split("\n").sorted(),
                (result[0] as ResultOutput).text.trim().split("\n").sorted()
            )
        }

        @Test
        fun `inherently ambiguous grammar`() {
            val script = parseScriptStrict(
                """
                CONNECT TO "${datasetPath}";
                Fa = a Ia d | a Fa d;
                Ia = b c | b Ia c;
                Fb = L R;
                L = a b | a L b;
                R = c d | c R d;
                SELECT (u, v) FROM "inherently_ambiguous_grammar_graph" WHERE u--|Fa | Fb|->v;
            """.trimIndent()
            )
            val context = ScriptEvaluationContext()
            val result = context.evaluate(script).results.filter { it !is ResultUnit }
            assertTrue(result.size == 1)
            assertTrue(result[0] is ResultOutput)
            assertEquals(
                """
                (0, 8)
                (0, 7)
                (2, 8)
                (2, 7)
            """.trimIndent().split("\n").sorted(),
                (result[0] as ResultOutput).text.trim().split("\n").sorted()
            )
        }

        @Test
        fun `inherently ambiguous grammar inverted order`() {
            val script = parseScriptStrict(
                """
                CONNECT TO "${datasetPath}";
                Fa = a Ia d | a Fa d;
                Ia = b c | b Ia c;
                Fb = L R;
                L = a b | a L b;
                R = c d | c R d;
                SELECT (v, u) FROM "inherently_ambiguous_grammar_graph" WHERE u--|Fa | Fb|->v;
            """.trimIndent()
            )
            val context = ScriptEvaluationContext()
            val result = context.evaluate(script).results.filter { it !is ResultUnit }
            assertTrue(result.size == 1)
            assertTrue(result[0] is ResultOutput)
            assertEquals(
                """
                (8, 0)
                (7, 0)
                (8, 2)
                (7, 2)
            """.trimIndent().split("\n").sorted(),
                (result[0] as ResultOutput).text.trim().split("\n").sorted()
            )
        }

        @Test
        fun `inherently ambiguous grammar projection`() {
            val script = parseScriptStrict(
                """
                CONNECT TO "${datasetPath}";
                Fa = a Ia d | a Fa d;
                Ia = b c | b Ia c;
                Fb = L R;
                L = a b | a L b;
                R = c d | c R d;
                SELECT v FROM "inherently_ambiguous_grammar_graph" WHERE u--|Fa | Fb|->v;
            """.trimIndent()
            )
            val context = ScriptEvaluationContext()
            val result = context.evaluate(script).results.filter { it !is ResultUnit }
            assertTrue(result.size == 1)
            assertTrue(result[0] is ResultOutput)
            assertEquals(
                """
                8
                7
            """.trimIndent().split("\n").sorted(),
                (result[0] as ResultOutput).text.trim().split("\n").sorted()
            )
        }

        @Test
        fun `inherently ambiguous grammar id predicate`() {
            val script = parseScriptStrict(
                """
                CONNECT TO "${datasetPath}";
                Fa = a Ia d | a Fa d;
                Ia = b c | b Ia c;
                Fb = L R;
                L = a b | a L b;
                R = c d | c R d;
                SELECT (u, v) FROM "inherently_ambiguous_grammar_graph" WHERE (u: id=2)--|Fa | Fb|->v;
            """.trimIndent()
            )
            val context = ScriptEvaluationContext()
            val result = context.evaluate(script).results.filter { it !is ResultUnit }
            assertTrue(result.size == 1)
            assertTrue(result[0] is ResultOutput)
            assertEquals(
                """
                (2, 8)
                (2, 7)
            """.trimIndent().split("\n").sorted(),
                (result[0] as ResultOutput).text.trim().split("\n").sorted()
            )
        }
    }
}