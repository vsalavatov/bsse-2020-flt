package com.bsse2018.salavatov.flt.querylang

import com.bsse2018.salavatov.flt.grammars.ContextFreeGrammar
import org.eclipse.rdf4j.query.QueryLanguage

private val definition = """
SCRIPT eps 
SCRIPT STATEMENT semicolon SCRIPT
STATEMENT STMT_CONNECT
STATEMENT STMT_LIST 
STATEMENT STMT_SELECT 
STATEMENT STMT_PATTERN_DECL
 
STMT_CONNECT kw_connect kw_to string_desc 
STMT_LIST kw_list kw_graphs
STMT_LIST kw_list kw_graphs string_desc
STMT_LIST kw_list kw_labels string_desc
STMT_SELECT kw_select OBJECT_EXPR FROM_EXPR WHERE_EXPR 
STMT_PATTERN_DECL nonterminal eq PATTERN

OBJECT_EXPR VERTICES_EXPR 
OBJECT_EXPR COUNT_EXPR 
OBJECT_EXPR EXISTS_EXPR 
FROM_EXPR kw_from string_desc 
WHERE_EXPR kw_where PATH_DESC

VERTICES_EXPR VERTICES_DESC
COUNT_EXPR kw_count VERTICES_DESC
EXISTS_EXPR kw_exists VERTICES_DESC

VERTICES_DESC PAIR_VERTICES_DESC 
VERTICES_DESC SINGLE_VERTEX_DESC
PAIR_VERTICES_DESC lbr VERTEX_NAME comma VERTEX_NAME rbr
SINGLE_VERTEX_DESC VERTEX_NAME

PATH_DESC VERTEX_COND arrow_left PATTERN arrow_right VERTEX_COND
VERTEX_COND VERTEX_NAME 
VERTEX_COND lbr VERTEX_NAME colon kw_id eq integer rbr

PATTERN nonterminal 
PATTERN nonterminal ptrn_star
PATTERN nonterminal ptrn_plus
PATTERN nonterminal ptrn_option
PATTERN TERMINAL 
PATTERN TERMINAL ptrn_star
PATTERN TERMINAL ptrn_plus
PATTERN TERMINAL ptrn_option
PATTERN lbr PATTERN rbr 
PATTERN lbr PATTERN rbr ptrn_star
PATTERN lbr PATTERN rbr ptrn_plus
PATTERN lbr PATTERN rbr ptrn_option
PATTERN PATTERN ptrn_alt PATTERN
PATTERN PATTERN PATTERN 
 
TERMINAL lowercase_word
TERMINAL EPSILON
EPSILON lbr rbr

VERTEX_NAME lowercase_word
"""

fun QueryLanguagePart(start: String) = ContextFreeGrammar(start, ContextFreeGrammar.fromStrings(
    definition.split("\n").map { it.trim() }.filter { it.isNotEmpty() }
).rules).toChomskyNormalForm()

val QueryLanguage = ContextFreeGrammar.fromStrings(
    definition.split("\n").map { it.trim() }.filter { it.isNotEmpty() }
)
    .toChomskyNormalForm()