package com.bsse2018.salavatov.flt.utils

import java.lang.Exception

typealias Graph = Array<Array<Pair<String, Int>>>

class GraphInvalidFormatException(desc: String) : Exception("Invalid graph format: $desc")

fun graphFromStrings(desc: Array<String>): Graph {
    val triples = desc.map {
        val tokens = it.trim().split(Regex("\\s+"))
        if (tokens.size != 3) {
            throw GraphInvalidFormatException(it)
        }
        try {
            val from = tokens[0].toInt()
            val sym = tokens[1]
            val to = tokens[2].toInt()
            Triple(from, sym, to)
        } catch (e: Exception) {
            throw GraphInvalidFormatException(it)
        }
    }
    var maxV = 0
    triples.forEach {
        maxV = maxOf(maxV, it.first, it.third)
    }
    val graph = Array(maxV + 1) { arrayListOf<Pair<String, Int>>() }
    triples.forEach {
        graph[it.first].add(Pair(it.second, it.third))
    }
    return graph.map { it.toTypedArray() }.toTypedArray()
}