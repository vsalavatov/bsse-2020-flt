package com.bsse2018.salavatov.dotdsl

import java.io.OutputStream
import java.io.PrintStream

class DotTree {
    var label: String? = null
    val children = mutableListOf<DotTree>()

    fun print(stream: PrintStream = System.out) {
        stream.apply {
            println("digraph parse_tree {")
            println("  rankdir=\"TB\";")
            subprint(stream, 0)
            println("}")
        }
    }

    private fun subprint(stream: PrintStream, index: Int): Int {
        stream.apply {
            println("  N${index} [label=\"${(label ?: "N${index}").replace("\"", "\\\"")}\"];")
            var ind = index + 1
            children.forEach {
                println("  N${index} -> N${ind};")
                ind = it.subprint(stream, ind)
            }
            return ind
        }
    }
}

object dotTree {
    operator fun invoke(f: DotTree.() -> Unit) = DotTree().apply { f() }
    infix fun DotTree.`--`(subtree: DotTree) {
        children.add(subtree)
    }
}