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
        assertEquals(8, result.text.trim().split("\n").size)
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

        @Test
        fun `multiple connects`() {
            assertThrows<GraphDoesNotExistException> {
                checkOneOutput(
                    """
                    CONNECT TO "${datasetPath}";
                    CONNECT TO "${datasetPath}/empty/";
                    SELECT EXISTS u FROM "graph" WHERE u--|a|->v;
                """.trimIndent(), ""
                )
            }
            checkOneOutput(
                """
                    CONNECT TO "${datasetPath}/empty/";
                    CONNECT TO "${datasetPath}";
                    SELECT EXISTS u FROM "worstcase_4" WHERE u--|a|->v;
                """.trimIndent(), "yes"
            )
        }

        @Test
        fun `pattern does not affect context`() {
            checkOutputs(
                """
                CONNECT TO "${datasetPath}";
                S = b (b b)*;
                SELECT EXISTS u FROM "worstcase_4" WHERE (u: id=3)--|S|->(v: id=3);
                SELECT EXISTS u FROM "worstcase_4" WHERE (u: id=3)--|S b|->(v: id=3);
                SELECT EXISTS u FROM "worstcase_4" WHERE (u: id=3)--|S|->(v: id=3);
                SELECT EXISTS u FROM "worstcase_4" WHERE (u: id=3)--|S S|->(v: id=3);
                F = b S;
                SELECT EXISTS u FROM "worstcase_4" WHERE u--|F S|->v;
            """.trimIndent(), "no", "yes", "no", "yes", "yes"
            )
        }

        @Test
        fun `undeclared pattern`() {
            checkOutputs(
                """
                CONNECT TO "${datasetPath}";
                S = b (b b)*;
                SELECT EXISTS u FROM "worstcase_4" WHERE (u: id=3)--|S S|->(v: id=3);
                SELECT EXISTS u FROM "worstcase_4" WHERE (u: id=3)--|S S|->(v: id=0);
                SELECT EXISTS u FROM "worstcase_4" WHERE (u: id=3)--|S|->(v: id=0);
                F = b S;
                SELECT EXISTS u FROM "worstcase_4" WHERE u--|F S|->v;
                SELECT EXISTS u FROM "worstcase_4" WHERE (u: id=1)--|Q S|->(v: id=2);
            """.trimIndent(), "yes", "no", "yes", "yes", "no"
            )
        }

        @Test
        fun `path declaration using same variable name must throw`() {
            assertThrows<LogicErrorException> {
                checkOutputs(
                    """
                    CONNECT TO "${datasetPath}";
                    SELECT EXISTS u FROM "worstcase_4" WHERE (u: id=3)--|S S|->(u: id=3);
                """.trimIndent()
                )
            }
        }

        @Test
        fun `select using same variable name must throw`() {
            assertThrows<LogicErrorException> {
                checkOutputs(
                    """
                    CONNECT TO "${datasetPath}";
                    SELECT COUNT (u, u) FROM "worstcase_4" WHERE u--|b (b b)*|->v;
                """.trimIndent()
                )
            }
        }

        @Test
        fun `unknown variable in select must throw`() {
            assertThrows<LogicErrorException> {
                checkOutputs(
                    """
                    CONNECT TO "${datasetPath}";
                    SELECT COUNT (u, g) FROM "worstcase_4" WHERE u--|b (b b)*|->v;
                """.trimIndent()
                )
            }
        }

        @Test
        fun `declaration of existing pattern is actually an alternative`() {
            checkOutputs(
                """
                CONNECT TO "${datasetPath}";
                S = b;
                SELECT EXISTS u FROM "worstcase_4" WHERE (u: id=3)--|S|->(v: id=0);
                SELECT EXISTS u FROM "worstcase_4" WHERE (u: id=3)--|S|->(v: id=3);
                SELECT EXISTS u FROM "worstcase_4" WHERE (u: id=3)--|S S|->(v: id=0);
                SELECT EXISTS u FROM "worstcase_4" WHERE (u: id=3)--|S S|->(v: id=3);
                S = a;
                SELECT EXISTS u FROM "worstcase_4" WHERE (u: id=3)--|S|->(v: id=0);
                SELECT EXISTS u FROM "worstcase_4" WHERE (u: id=3)--|S|->(v: id=3);
                SELECT EXISTS u FROM "worstcase_4" WHERE (u: id=3)--|S S|->(v: id=0);
                SELECT EXISTS u FROM "worstcase_4" WHERE (u: id=3)--|S S|->(v: id=3);
            """.trimIndent(), "yes", "no", "no", "yes", "yes", "no", "no", "yes"
            )
        }

        @Test
        fun `pattern option behavior`() {
            checkOutputs(
                """
                CONNECT TO "${datasetPath}";
                SELECT EXISTS u FROM "worstcase_4" WHERE (u: id=3)--|b?|->(v: id=0);
                SELECT EXISTS u FROM "worstcase_4" WHERE (u: id=3)--|b?|->(v: id=3);
                SELECT EXISTS u FROM "worstcase_4" WHERE (u: id=2)--|a?|->(v: id=3);
                SELECT EXISTS u FROM "worstcase_4" WHERE (u: id=2)--|a?|->(v: id=1);
            """.trimIndent(), "yes", "yes", "no", "yes"
            )
        }

        @Test
        fun `pattern plus behavior`() {
            checkOutputs(
                """
                CONNECT TO "${datasetPath}";
                SELECT EXISTS u FROM "custom_cbs" WHERE (u: id=0)--|a a b|->(v: id=0);
                SELECT EXISTS u FROM "custom_cbs" WHERE (u: id=0)--|a a b a b|->(v: id=0);
                SELECT EXISTS u FROM "custom_cbs" WHERE (u: id=0)--|a (a b)+|->(v: id=0);
            """.trimIndent(), "no", "yes", "yes"
            )
        }

        @Test
        fun `pattern star behavior`() {
            checkOutputs(
                """
                CONNECT TO "${datasetPath}";
                SELECT EXISTS u FROM "custom_cbs" WHERE (u: id=2)--|b a b a a|->(v: id=2);
                SELECT EXISTS u FROM "custom_cbs" WHERE (u: id=2)--|b a b a*|->(v: id=2);
                SELECT EXISTS u FROM "custom_cbs" WHERE (u: id=2)--|(b a b a)*|->(v: id=2);
                SELECT EXISTS u FROM "custom_cbs" WHERE (u: id=2)--|b a (b a)* a|->(v: id=2);
                SELECT EXISTS u FROM "custom_cbs" WHERE (u: id=2)--|b a (b a)* a a|->(v: id=2);
                SELECT EXISTS u FROM "custom_cbs" WHERE (u: id=2)--|((b a)* a)+|->(v: id=2);
            """.trimIndent(), "yes", "yes", "yes", "yes", "no", "yes"
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

    private fun checkOutputs(scriptText: String, vararg expectedResults: String) {
        val script = parseScriptStrict(scriptText)
        val context = ScriptEvaluationContext()
        val result = context.evaluate(script).results.filter { it !is ResultUnit }
        assertTrue(result.size == expectedResults.size)
        assertTrue(result.all { it is ResultOutput })
        for (i in result.indices) {
            assertEquals(expectedResults[i], (result[i] as ResultOutput).text.trim())
        }
    }
}