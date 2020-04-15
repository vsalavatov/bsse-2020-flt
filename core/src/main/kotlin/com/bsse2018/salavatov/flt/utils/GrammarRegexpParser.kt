package com.bsse2018.salavatov.flt.utils

class GrammarRegexpInvalidFormatException(desc: String) : Exception("Invalid grammar regexp: $desc")

sealed class GrammarRegexpNode
data class GRNDeclaration(val from: String, val rhs: GrammarRegexpNode) : GrammarRegexpNode()
data class GRNSequence(val sequence: Array<GrammarRegexpNode>) : GrammarRegexpNode() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GRNSequence

        if (!sequence.contentEquals(other.sequence)) return false

        return true
    }

    override fun hashCode(): Int {
        return sequence.contentHashCode()
    }
}

data class GRNStar(val nested: GrammarRegexpNode) : GrammarRegexpNode()
data class GRNAlternatives(val alternatives: Array<GrammarRegexpNode>) : GrammarRegexpNode() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GRNAlternatives

        if (!alternatives.contentEquals(other.alternatives)) return false

        return true
    }

    override fun hashCode(): Int {
        return alternatives.contentHashCode()
    }
}

data class GRNUnit(val to: String) : GrammarRegexpNode()

object GrammarRegexpParser {
    fun parseGrammarRegexp(regexp: String): GRNDeclaration {
        val index = regexp.indexOfFirst { it == ' ' }
        if (index == -1)
            throw GrammarRegexpInvalidFormatException("no rhs in '$regexp'")
        return GRNDeclaration(regexp.substring(0, index), parse(regexp.substring(index + 1)))
    }

    private fun parse(rhs_: String): GrammarRegexpNode {
        val rhs = rhs_.trim()
        if (rhs.isEmpty())
            throw GrammarRegexpInvalidFormatException("empty sub-regexp")
        var balance = 0
        var iter = 0
        do {
            if (rhs[iter] == '(') {
                balance++
            } else if (rhs[iter] == ')') {
                balance--
                if (balance < 0) throw GraphInvalidFormatException("inconsistent bracket sequence")
            } else if (balance == 0) break
            iter++
        } while (iter < rhs.length && balance != 0)
        if (balance != 0) throw GraphInvalidFormatException("inconsistent bracket sequence")

        if (iter == rhs.length)
            return parse(rhs.substring(1, rhs.length - 1))
        if ((iter == rhs.length - 1 && rhs[iter] == '*') || Regex("[a-zA-Z0-9]+\\*").matches(rhs))
            return GRNStar(parse(rhs.substring(0, rhs.length - 1)))

        // decide whether it sequence or alternatives node
        var hasAlternatives = false
        while (iter < rhs.length) {
            if (rhs[iter] == '(') {
                balance++
            } else if (rhs[iter] == ')') {
                balance--
                if (balance < 0) throw GraphInvalidFormatException("inconsistent bracket sequence")
            } else if (balance == 0 && rhs[iter] == '|') {
                hasAlternatives = true
            }
            iter++
        }
        if (balance != 0) throw GraphInvalidFormatException("inconsistent bracket sequence")

        when {
            hasAlternatives -> {
                val alternatives = ArrayList<GrammarRegexpNode>()
                var left = 0
                for (i in rhs.indices) {
                    if (rhs[i] == '(') balance++
                    else if (rhs[i] == ')') balance--
                    else if (balance == 0 && rhs[i] == '|') {
                        alternatives.add(parse(rhs.substring(left, i)))
                        left = i + 1
                    }
                }
                alternatives.add(parse(rhs.substring(left, rhs.length)))
                return GRNAlternatives(alternatives.toTypedArray())
            }
            rhs.contains(" ") -> {
                val sequence = ArrayList<GrammarRegexpNode>()
                var left = 0
                for (i in rhs.indices) {
                    if (rhs[i] == '(') balance++
                    else if (rhs[i] == ')') balance--
                    else if (balance == 0 && rhs[i] == ' ') {
                        if (left != i)
                            sequence.add(parse(rhs.substring(left, i)))
                        left = i + 1
                    }
                }
                sequence.add(parse(rhs.substring(left, rhs.length)))
                return GRNSequence(sequence.toTypedArray())
            }
            else -> {
                return GRNUnit(rhs)
            }
        }
    }
}