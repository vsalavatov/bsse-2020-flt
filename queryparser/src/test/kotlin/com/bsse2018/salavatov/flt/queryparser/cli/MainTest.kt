package com.bsse2018.salavatov.flt.queryparser.cli

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.ByteArrayOutputStream
import java.io.PrintStream


internal class MainTest {
    @Test
    fun connect() {
        val baos = ByteArrayOutputStream()
        val ps = PrintStream(baos)
        translate2Dot("CONNECT TO \"path\";".trimIndent().byteInputStream(), ps)
        assertEquals(
            """
            digraph parse_tree {
              rankdir="TB";
              N0 [label="script"];
              N0 -> N1;
              N1 [label="statement"];
              N1 -> N2;
              N2 [label="stmt_connect"];
              N2 -> N3;
              N3 [label="CONNECT"];
              N2 -> N4;
              N4 [label="TO"];
              N2 -> N5;
              N5 [label="\"path\""];
              N0 -> N6;
              N6 [label=";"];
              N0 -> N7;
              N7 [label="<EOF>"];
            }
            
        """.trimIndent(), baos.toString()
        )
    }

    @Test
    fun script() {
        val baos = ByteArrayOutputStream()
        val ps = PrintStream(baos)
        translate2Dot(
            """
            CONNECT TO "~/graphs/";
            LIST GRAPHS;
            S = a S b S | ();
            Eps = ();
            SELECT u FROM "graph-example.txt" WHERE u--|S|->(v: id=15);
        """.trimIndent().byteInputStream(), ps
        )
        assertEquals(
            """
  digraph parse_tree {
    rankdir="TB";
    N0 [label="script"];
    N0 -> N1;
    N1 [label="statement"];
    N1 -> N2;
    N2 [label="stmt_connect"];
    N2 -> N3;
    N3 [label="CONNECT"];
    N2 -> N4;
    N4 [label="TO"];
    N2 -> N5;
    N5 [label="\"~/graphs/\""];
    N0 -> N6;
    N6 [label=";"];
    N0 -> N7;
    N7 [label="statement"];
    N7 -> N8;
    N8 [label="stmt_list"];
    N8 -> N9;
    N9 [label="LIST"];
    N8 -> N10;
    N10 [label="GRAPHS"];
    N0 -> N11;
    N11 [label=";"];
    N0 -> N12;
    N12 [label="statement"];
    N12 -> N13;
    N13 [label="stmt_pattern_decl"];
    N13 -> N14;
    N14 [label="S"];
    N13 -> N15;
    N15 [label="="];
    N13 -> N16;
    N16 [label="pattern"];
    N16 -> N17;
    N17 [label="pattern"];
    N17 -> N18;
    N18 [label="pattern"];
    N18 -> N19;
    N19 [label="pattern"];
    N19 -> N20;
    N20 [label="pattern"];
    N20 -> N21;
    N21 [label="terminal"];
    N21 -> N22;
    N22 [label="a"];
    N19 -> N23;
    N23 [label="pattern"];
    N23 -> N24;
    N24 [label="S"];
    N18 -> N25;
    N25 [label="pattern"];
    N25 -> N26;
    N26 [label="terminal"];
    N26 -> N27;
    N27 [label="b"];
    N17 -> N28;
    N28 [label="pattern"];
    N28 -> N29;
    N29 [label="S"];
    N16 -> N30;
    N30 [label="|"];
    N16 -> N31;
    N31 [label="pattern"];
    N31 -> N32;
    N32 [label="terminal"];
    N32 -> N33;
    N33 [label="epsilon"];
    N33 -> N34;
    N34 [label="("];
    N33 -> N35;
    N35 [label=")"];
    N0 -> N36;
    N36 [label=";"];
    N0 -> N37;
    N37 [label="statement"];
    N37 -> N38;
    N38 [label="stmt_pattern_decl"];
    N38 -> N39;
    N39 [label="Eps"];
    N38 -> N40;
    N40 [label="="];
    N38 -> N41;
    N41 [label="pattern"];
    N41 -> N42;
    N42 [label="terminal"];
    N42 -> N43;
    N43 [label="epsilon"];
    N43 -> N44;
    N44 [label="("];
    N43 -> N45;
    N45 [label=")"];
    N0 -> N46;
    N46 [label=";"];
    N0 -> N47;
    N47 [label="statement"];
    N47 -> N48;
    N48 [label="stmt_select"];
    N48 -> N49;
    N49 [label="SELECT"];
    N48 -> N50;
    N50 [label="object_expr"];
    N50 -> N51;
    N51 [label="vertices_expr"];
    N51 -> N52;
    N52 [label="vertices_desc"];
    N52 -> N53;
    N53 [label="single_vertex_desc"];
    N53 -> N54;
    N54 [label="vertex_name"];
    N54 -> N55;
    N55 [label="u"];
    N48 -> N56;
    N56 [label="from_expr"];
    N56 -> N57;
    N57 [label="FROM"];
    N56 -> N58;
    N58 [label="\"graph-example.txt\""];
    N48 -> N59;
    N59 [label="where_expr"];
    N59 -> N60;
    N60 [label="WHERE"];
    N59 -> N61;
    N61 [label="path_desc"];
    N61 -> N62;
    N62 [label="vertex_cond"];
    N62 -> N63;
    N63 [label="vertex_name"];
    N63 -> N64;
    N64 [label="u"];
    N61 -> N65;
    N65 [label="--|"];
    N61 -> N66;
    N66 [label="pattern"];
    N66 -> N67;
    N67 [label="S"];
    N61 -> N68;
    N68 [label="|->"];
    N61 -> N69;
    N69 [label="vertex_cond"];
    N69 -> N70;
    N70 [label="("];
    N69 -> N71;
    N71 [label="vertex_name"];
    N71 -> N72;
    N72 [label="v"];
    N69 -> N73;
    N73 [label=":"];
    N69 -> N74;
    N74 [label="id"];
    N69 -> N75;
    N75 [label="="];
    N69 -> N76;
    N76 [label="15"];
    N69 -> N77;
    N77 [label=")"];
    N0 -> N78;
    N78 [label=";"];
    N0 -> N79;
    N79 [label="<EOF>"];
  }
  
""".trimIndent().trim(), baos.toString().trim()
        )
    }

    @Test
    fun error() {
        val baos = ByteArrayOutputStream()
        val ps = PrintStream(baos)
        assertThrows<ParseError> { translate2Dot("asdasfasfa".trimIndent().byteInputStream(), ps) }
    }

    @Test
    fun empty() {
        val baos = ByteArrayOutputStream()
        val ps = PrintStream(baos)
        translate2Dot("".byteInputStream(), ps)
        assertEquals(
            """
            digraph parse_tree {
              rankdir="TB";
              N0 [label="script"];
              N0 -> N1;
              N1 [label="<EOF>"];
            }
            
        """.trimIndent(), baos.toString()
        )
    }
}