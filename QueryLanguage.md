Query Language definition in [ABNF](https://tools.ietf.org/html/rfc5234) form:
```EBNF
SCRIPT = "" / STATEMENT semicolon SCRIPT

STATEMENT = STMT_CONNECT 
          / STMT_LIST 
          / STMT_SELECT 
          / STMT_PATTERN_DECL
 
STMT_CONNECT = kw_connect kw_to string_desc 
STMT_LIST = kw_list kw_graphs [string_desc] 
          / kw_list kw_labels string_desc
STMT_SELECT = kw_select OBJECT_EXPR FROM_EXPR WHERE_EXPR 
STMT_PATTERN_DECL = nonterminal eq PATTERN

OBJECT_EXPR = VERTICES_EXPR / COUNT_EXPR / EXISTS_EXPR 
FROM_EXPR = kw_from string_desc 
WHERE_EXPR = kw_where PATH_DESC

VERTICES_EXPR = VERTICES_DESC
COUNT_EXPR = kw_count VERTICES_DESC
EXISTS_EXPR = kw_exists VERTICES_DESC

VERTICES_DESC = PAIR_VERTICES_DESC / SINGLE_VERTEX_DESC
PAIR_VERTICES_DESC = lbr VERTEX_NAME comma VERTEX_NAME rbr
SINGLE_VERTEX_DESC = VERTEX_NAME

PATH_DESC = VERTEX_COND arrow_left PATTERN arrow_right VERTEX_COND
VERTEX_COND = VERTEX_NAME
            / lbr VERTEX_NAME colon kw_id eq integer rbr

PATTERN = (TERMINAL / nonterminal) [ptrn_star / ptrn_plus / ptrn_option]
        / lbr PATTERN rbr [ptrn_star / ptrn_plus / ptrn_option]
        / PATTERN ptrn_alt PATTERN
        / PATTERN PATTERN 
 
TERMINAL = lowercase_word / EPSILON
EPSILON = lbr rbr

VERTEX_NAME = lowercase_word

; Terminals
ualpha = %X41-5A ; %X41-5A ~ A-Z RANGE
lalpha = %X61-7A ; %X61-7A ~ A-Z RANGE
lowercase_word = lalpha *lalpha
integer = DIGIT *DIGIT
string_desc = DQUOTE *VCHAR DQUOTE ; VCHAR ~ ALL PRINTABLE CHARACTERS
nonterminal = ualpha *ALPHA

eq = "="
lbr = "("
rbr = ")"
comma = ","
colon = ":"
semicolon = ";"
arrow_left = "--|"
arrow_right = "|->"
ptrn_star = "*"
ptrn_plus = "+"
ptrn_alt = "|"
ptrn_option = "?"
kw_connect = "CONNECT"
kw_to = "TO"
kw_list = "LIST"
kw_graphs = "GRAPHS"
kw_select = "SELECT"
kw_from = "FROM"
kw_where = "WHERE"
kw_count = "COUNT"
kw_exists = "EXISTS"
kw_id = "ID"
kw_labels = "LABELS"
```

Example:
```
CONNECT TO "~/graphs/";
LIST GRAPHS;
LIST GRAPHS "~/graphs-2/";
LIST LABELS "worstcase_4";
S = a S b S | ();
Eps = ();
SELECT u FROM "graph-example.txt" WHERE u--|S|->(v: id=15);
SELECT COUNT (u,v) FROM "another-example.txt" WHERE u--|Eps|->v;
SELECT EXISTS v FROM "third-example.txt" WHERE f--|a (a b)+ b|->v;
```
