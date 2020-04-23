package com.bsse2018.salavatov.flt.querylang

import com.bsse2018.salavatov.flt.algorithms.CYKQuery
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class QueryLanguageTest {
    private fun String.toQuery() = trimMargin().split("\n", " ").filter { it.isNotEmpty() }

    @Test
    fun sample() {
        /*
CONNECT TO "~/graphs/";
LIST GRAPHS;
S = a S b S | ();
Eps = ();
SELECT u FROM "graph-example.txt" WHERE u--|S|->(v: id=15);
SELECT COUNT (u,v) FROM "another-example.txt" WHERE u--|Eps|->v;
SELECT EXISTS v FROM "third-example.txt" WHERE f--|a (a b)+ b|->v;
         */
        val query = """
kw_connect kw_to string_desc semicolon
kw_list kw_graphs semicolon
nonterminal eq lowercase_word nonterminal lowercase_word nonterminal ptrn_alt lbr rbr semicolon
nonterminal eq lbr rbr semicolon
kw_select lowercase_word kw_from string_desc kw_where lowercase_word arrow_left nonterminal arrow_right lbr lowercase_word colon kw_id eq integer rbr semicolon
kw_select kw_count lbr lowercase_word comma lowercase_word rbr kw_from string_desc kw_where lowercase_word arrow_left nonterminal arrow_right lowercase_word semicolon
kw_select kw_exists lowercase_word kw_from string_desc kw_where lowercase_word arrow_left lowercase_word lbr lowercase_word lowercase_word rbr ptrn_plus lowercase_word arrow_right lowercase_word semicolon
        """.toQuery()
        assertTrue(CYKQuery(QueryLanguage, query))
    }

    @Test
    fun empty() {
        val query = listOf<String>()
        assertTrue(CYKQuery(QueryLanguage, query))
    }

    @Test
    fun connect() {
        val query = "kw_connect kw_to string_desc semicolon".toQuery()
        assertTrue(CYKQuery(QueryLanguage, query))
    }

    @Test
    fun list() {
        val query = "kw_list kw_graphs semicolon".toQuery()
        assertTrue(CYKQuery(QueryLanguage, query))
    }

    @Test
    fun `pattern def 1`() {
        val query =
            "nonterminal eq lowercase_word nonterminal lowercase_word nonterminal ptrn_alt lbr rbr semicolon".toQuery()
        assertTrue(CYKQuery(QueryLanguage, query))
    }

    @Test
    fun `pattern def 2`() {
        val query = "nonterminal eq lbr rbr semicolon".toQuery()
        assertTrue(CYKQuery(QueryLanguage, query))
    }

    @Test
    fun `select 1`() {
        val query =
            "kw_select lowercase_word kw_from string_desc kw_where lowercase_word arrow_left nonterminal arrow_right lbr lowercase_word colon kw_id eq integer rbr semicolon".toQuery()
        assertTrue(CYKQuery(QueryLanguage, query))
    }

    @Test
    fun `select 2`() {
        val query =
            "kw_select kw_count lbr lowercase_word comma lowercase_word rbr kw_from string_desc kw_where lowercase_word arrow_left nonterminal arrow_right lowercase_word semicolon".toQuery()
        assertTrue(CYKQuery(QueryLanguage, query))
    }

    @Test
    fun `select 3`() {
        val query =
            "kw_select kw_exists lowercase_word kw_from string_desc kw_where lowercase_word arrow_left lowercase_word lbr lowercase_word lowercase_word rbr ptrn_plus lowercase_word arrow_right lowercase_word semicolon".toQuery()
        assertTrue(CYKQuery(QueryLanguage, query))
    }

    @Test
    fun `pattern a (a b)+ b`() {
        val query = "lowercase_word lbr lowercase_word lowercase_word rbr ptrn_plus lowercase_word".toQuery()
        assertTrue(CYKQuery(QueryLanguagePart("PATTERN"), query))
    }

    @Test
    fun `forgotten semicolon 1`() {
        val query = "kw_connect kw_to string_desc".toQuery()
        assertFalse(CYKQuery(QueryLanguage, query))
    }

    @Test
    fun `forgotten semicolon 2`() {
        val query = "kw_connect kw_to string_desc kw_list kw_graphs kw_semicolon".toQuery()
        assertFalse(CYKQuery(QueryLanguage, query))
    }

    @Test
    fun `pattern decl`() {
        assertFalse(CYKQuery(QueryLanguagePart("STMT_PATTERN_DECL"), "lowercase_word eq lowercase_word".toQuery()))
        assertTrue(CYKQuery(QueryLanguagePart("STMT_PATTERN_DECL"), "nonterminal eq lowercase_word".toQuery()))
    }

    @Test
    fun `pattern bad brackets`() {
        assertFalse(
            CYKQuery(
                QueryLanguagePart("STMT_PATTERN_DECL"),
                "nonterminal eq lbr lbr lowercase_word rbr ptrn_plus".toQuery()
            )
        )
        assertTrue(
            CYKQuery(
                QueryLanguagePart("STMT_PATTERN_DECL"),
                "nonterminal eq lbr lbr lowercase_word rbr ptrn_plus rbr ptrn_option".toQuery()
            )
        )
    }
}