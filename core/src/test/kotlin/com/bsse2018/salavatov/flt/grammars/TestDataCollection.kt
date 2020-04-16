package com.bsse2018.salavatov.flt.grammars

object TestDataCollection {
    fun correctBracketSequenceGrammar() = ContextFreeGrammar.fromStrings(
        listOf(
            "S a S b S", "S eps"
        )
    )

    fun correctBracketSequenceAmbiguousGrammar() = ContextFreeGrammar.fromStrings(
        listOf(
            "S eps", "S a S b", "S S S"
        )
    )

    fun distinctNumberABGrammar() = ContextFreeGrammar.fromStrings(
        listOf(
            "S T", "S U",
            "T V a T", "T V a V", "T T a V",
            "U V b U", "U V b V", "U U b V",
            "V a V b V", "V b V a V", "V eps"
        )
    )

    // { a^n b^m c^m d^n | n,m > 0 } U { a^n b^n c^m d^m | n,m > 0 }
    fun inherentlyAmbiguousGrammar() = ContextFreeGrammar.fromStrings(
        listOf(
            "S F1", "S F2",
            "F1 a I1 d", "F1 a F1 d",
            "I1 b c", "I1 b I1 c",
            "F2 L R",
            "L a b", "L a L b",
            "R c d", "R c R d"
        )
    )

    fun CYKWorstCaseGraph() = listOf(
        listOf(Pair("a", 1)),
        listOf(Pair("a", 2)),
        listOf(Pair("a", 0), Pair("b", 3)),
        listOf(Pair("b", 2))
    )

    fun CYKWorstCaseGrammar() = ContextFreeGrammar.fromStrings(
        listOf(
            "S A B", "S A S1",
            "S1 S B",
            "A a",
            "B b"
        )
    )
}