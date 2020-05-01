grammar QueryLanguage;

script : (statement SEMICOLON)* EOF ;

statement : stmt_connect
          | stmt_list
          | stmt_select
          | stmt_pattern_decl
          ;

stmt_connect : KW_CONNECT KW_TO STRING_DESC ;
stmt_list : KW_LIST KW_GRAPHS ;
stmt_select : KW_SELECT object_expr from_expr where_expr ;
stmt_pattern_decl : NONTERMINAL EQ pattern ;

object_expr : vertices_expr
            | count_expr
            | exists_expr
            ;
from_expr : KW_FROM STRING_DESC ;
where_expr : KW_WHERE path_desc ;

vertices_expr : vertices_desc ;
count_expr : KW_COUNT vertices_desc ;
exists_expr : KW_EXISTS vertices_desc ;

vertices_desc : pair_vertices_desc
              | single_vertex_desc
              ;
pair_vertices_desc : LBR vertex_name COMMA vertex_name RBR ;
single_vertex_desc : vertex_name ;

path_desc : vertex_cond ARROW_LEFT pattern ARROW_RIGHT vertex_cond ;
vertex_cond : vertex_name
            | LBR vertex_name COLON KW_ID EQ INTEGER RBR
            ;

pattern : (terminal | NONTERMINAL) (PTRN_STAR | PTRN_PLUS | PTRN_OPTION)?
        | LBR pattern RBR (PTRN_STAR | PTRN_PLUS | PTRN_OPTION)?
        | pattern PTRN_ALT pattern
        | pattern pattern
        ;

terminal : LOWERCASE_WORD
         | epsilon
         ;
epsilon : LBR RBR
        ;

vertex_name : LOWERCASE_WORD ;

// Terminals
EQ : '=' ;
LBR : '(' ;
RBR : ')' ;
COMMA : ',' ;
COLON : ':' ;
SEMICOLON : ';' ;
ARROW_LEFT : '--|' ;
ARROW_RIGHT : '|->' ;
PTRN_STAR : '*' ;
PTRN_PLUS : '+' ;
PTRN_ALT : '|' ;
PTRN_OPTION : '?' ;
KW_CONNECT : 'CONNECT' ;
KW_TO : 'TO' ;
KW_LIST : 'LIST' ;
KW_GRAPHS : 'GRAPHS' ;
KW_SELECT : 'SELECT' ;
KW_FROM : 'FROM' ;
KW_WHERE : 'WHERE' ;
KW_COUNT : 'COUNT' ;
KW_EXISTS : 'EXISTS' ;
KW_ID : 'id' ;
LOWERCASE_WORD : [a-z]+ ;
INTEGER : [0-9]+ ;
NONTERMINAL : [A-Z] [a-zA-Z]* ;
STRING_DESC : '"' (~[\r\n"] | '\\"')* '"' ;

WS : [ \r\n\t]+ -> skip ;