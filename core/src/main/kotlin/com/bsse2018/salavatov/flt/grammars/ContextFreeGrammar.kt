package com.bsse2018.salavatov.flt.grammars

import java.util.*
import kotlin.collections.MutableSet

class EmptyLanguageException : Exception("Grammar is not habitable")
class InvalidFormatException(desc: String) : Exception("Invalid format: $desc")

open class ContextFreeGrammar(val start: String, rules_: MutableSet<Rule>) {
    val rules: MutableSet<Rule> = mutableSetOf<Rule>()

    init {
        for (rule_ in rules_) {
            rules.add(Rule(
                rule_.from,
                rule_.to
                    .filterNot { it == Epsilon }
                    .ifEmpty { listOf(Epsilon) }
            ))
        }
    }

    data class Rule(val from: String, val to: List<String>) {
        fun isEpsilon() = to.size == 1 && to[0] == Epsilon
        fun isUnit() = to.size == 1 && isNonTerminal(to[0])
        fun isTerminal() = to.all { isTerminal(it) }
    }

    class NodeAccountant {
        val nonTerminals = mutableSetOf<String>()
        private var index = 0

        fun consume(node: String) {
            nonTerminals.add(node)
        }

        fun consume(rule: Rule) {
            nonTerminals.add(rule.from)
            nonTerminals.addAll(rule.to.filter { isTerminal(it) })
        }

        fun consume(cfg: ContextFreeGrammar) {
            consume(cfg.start)
            cfg.rules.forEach { consume(it) }
        }

        fun freshNonTerminal(): String {
            while (true) {
                val name = "S$index"
                if (!nonTerminals.contains(name)) {
                    nonTerminals.add(name)
                    index++
                    return name
                }
                index++
            }
        }
    }

    fun shrinkLongRules(): ContextFreeGrammar {
        val accountant = NodeAccountant()
        accountant.consume(this)
        val newRules = mutableSetOf<Rule>()
        rules.forEach { rule ->
            val rhs = mutableListOf<String>().apply {
                addAll(rule.to)
            }
            var lhs = rule.from
            while (rhs.size > 2) {
                val fresh = accountant.freshNonTerminal()
                newRules.add(Rule(lhs, listOf(fresh, rhs.last())))
                rhs.removeAt(rhs.size - 1)
                lhs = fresh
            }
            newRules.add(Rule(lhs, rhs))
        }
        return ContextFreeGrammar(start, newRules)
    }

    fun hasOnlySmallRules(): Boolean = rules.all { it.to.size <= 2 }

    fun epsilonProducers(): MutableSet<String> {
        val produceEps = hashMapOf<String, Boolean>()
        val concernedRules = hashMapOf<String, MutableSet<Rule>>()
        val nonProducingNonTerminals = hashMapOf<Rule, MutableSet<String>>()

        produceEps[start] = false
        rules.forEach { rule ->
            produceEps[rule.from] = false
            rule.to.forEach { elem ->
                if (isNonTerminal(elem)) {
                    produceEps[elem] = false
                    concernedRules.getOrPut(elem, { mutableSetOf() }).add(rule)
                }
                // add even a terminal node so that we won't mark that rule eps-producing
                nonProducingNonTerminals.getOrPut(rule, { mutableSetOf() }).add(elem)
            }
        }

        val queue = ArrayDeque<String>()

        nonProducingNonTerminals
            .filter { it.key.isEpsilon() }
            .forEach {
                val rule = it.key
                if (produceEps[rule.from] == false) {
                    produceEps[rule.from] = true
                    queue.add(rule.from)
                }
            }

        while (queue.isNotEmpty()) {
            val nonTerm = queue.pop()
            concernedRules[nonTerm]?.forEach {
                nonProducingNonTerminals[it]?.remove(nonTerm)
                if (nonProducingNonTerminals[it]?.isEmpty() == true) {
                    val lhs = it.from
                    if (produceEps[lhs] == false) {
                        produceEps[lhs] = true
                        queue.add(lhs)
                    }
                }
            }
        }

        return produceEps.filter { it.value }.keys.toMutableSet()
    }

    fun reduceEpsilonRules(): ContextFreeGrammar {
        if (!hasOnlySmallRules())
            return shrinkLongRules().reduceEpsilonRules()

        val epsilonProducers = epsilonProducers()
        var newStart = start
        val newRules = mutableSetOf<Rule>()

        rules.forEach { rule ->
            if (!rule.isEpsilon()) { // drop all eps-rules
                val rhs = rule.to
                if (rhs.size == 1) {
                    newRules.add(rule.copy())
                } else {
                    assert(rhs.size == 2)
                    if (epsilonProducers.contains(rhs[0])) {
                        newRules.add(Rule(rule.from, listOf(rhs[1])))
                    }
                    if (epsilonProducers.contains(rhs[1])) {
                        newRules.add(Rule(rule.from, listOf(rhs[0])))
                    }
                    newRules.add(rule.copy())
                }
            }
        }
        if (epsilonProducers.contains(start)) {
            val accountant = NodeAccountant()
            accountant.consume(this)
            newStart = accountant.freshNonTerminal()
            newRules.add(Rule(newStart, listOf(Epsilon)))
            newRules.add(Rule(newStart, listOf(start)))
        }
        return ContextFreeGrammar(newStart, newRules)
    }

    fun isEpsilonReduced(): Boolean {
        return hasOnlySmallRules() && rules.all {
            !it.isEpsilon() || (it.isEpsilon() && it.from == start)
        }
    }

    fun reduceUnitRules(): ContextFreeGrammar {
        if (!hasOnlySmallRules())
            return shrinkLongRules().reduceUnitRules()

        val nonUnitRules = hashMapOf<String, MutableList<Rule>>()
        val unitRules = hashMapOf<String, MutableList<Rule>>()
        val newRules = mutableSetOf<Rule>()

        rules.forEach {
            if (!it.isUnit()) {
                nonUnitRules.getOrPut(it.from, { mutableListOf() }).add(it)
            } else {
                unitRules.getOrPut(it.from, { mutableListOf() }).add(it)
            }
        }

        val accountant = NodeAccountant()
        accountant.consume(this)
        accountant.nonTerminals.forEach { origin ->
            val queue = ArrayDeque<String>()
            val visited = mutableSetOf<String>()
            queue.add(origin)
            visited.add(origin)
            while (queue.isNotEmpty()) {
                val v = queue.pop()
                nonUnitRules[v]?.forEach {
                    newRules.add(Rule(origin, it.to))
                }
                unitRules[v]?.forEach {
                    if (!visited.contains(it.to[0])) {
                        queue.add(it.to[0])
                        visited.add(it.to[0])
                    }
                }
            }
        }

        return ContextFreeGrammar(start, newRules)
    }

    fun isUnitReduced(): Boolean {
        return rules.all { !it.isUnit() }
    }

    fun generatingRules(): MutableSet<Rule> {
        val generatingNonTerminals = mutableSetOf<String>()
        val concernedRules = hashMapOf<String, MutableSet<Rule>>()
        val nonGeneratingNonTerminals = hashMapOf<Rule, MutableSet<String>>()

        val queue = ArrayDeque<String>()

        rules.forEach { rule ->
            if (rule.isTerminal()) {
                if (!generatingNonTerminals.contains(rule.from)) {
                    generatingNonTerminals.add(rule.from)
                    queue.add(rule.from)
                }
                // add a rule with empty deps so that we can easily filter wanted rules later
                nonGeneratingNonTerminals.getOrPut(rule, { mutableSetOf() })
            } else {
                rule.to.filter { isNonTerminal(it) }
                    .forEach {
                        concernedRules.getOrPut(it, { mutableSetOf() }).add(rule)
                        nonGeneratingNonTerminals.getOrPut(rule, { mutableSetOf() }).add(it)
                    }
            }
        }

        while (queue.isNotEmpty()) {
            val nonTerm = queue.pop()
            concernedRules[nonTerm]?.forEach { rule ->
                nonGeneratingNonTerminals[rule]?.remove(nonTerm)
                if (nonGeneratingNonTerminals[rule]?.isEmpty() == true) {
                    val lhs = rule.from
                    if (!generatingNonTerminals.contains(lhs)) {
                        generatingNonTerminals.add(lhs)
                        queue.add(lhs)
                    }
                }
            }
        }

        return nonGeneratingNonTerminals.filter { it.value.isEmpty() }.keys.toMutableSet()
    }

    fun reduceNonGeneratingRules(): ContextFreeGrammar {
        val generatingRules = generatingRules()
        if (generatingRules.isEmpty() || !generatingRules.any { it.from == start })
            throw EmptyLanguageException()
        return ContextFreeGrammar(start, generatingRules)
    }

    fun reduceUnreachable(): ContextFreeGrammar {
        val queue = ArrayDeque<String>()
        val reachable = mutableSetOf<String>()
        queue.add(start)
        reachable.add(start)
        val graph = rules.groupBy { it.from }
        while (queue.isNotEmpty()) {
            val v = queue.pop()
            graph[v]?.forEach { rule ->
                rule.to.filter { isNonTerminal(it) }
                    .forEach {
                        if (!reachable.contains(it)) {
                            reachable.add(it)
                            queue.add(it)
                        }
                    }
            }
        }
        return ContextFreeGrammar(start, rules.filter { reachable.contains(it.from) }.toMutableSet())
    }

    fun reduceLongTerminalRules(): ContextFreeGrammar {
        val accountant = NodeAccountant()
        accountant.consume(this)

        val terminalMapping = hashMapOf<String, String>()
        val newRules = mutableSetOf<Rule>()

        rules.forEach { rule ->
            if (rule.to.size >= 2) {
                assert(rule.to.size == 2)
                val s1 = rule.to[0]
                val s2 = rule.to[1]
                newRules.add(
                    Rule(
                        rule.from,
                        listOf(
                            if (isTerminal(s1)) {
                                terminalMapping.getOrPut(s1, { accountant.freshNonTerminal() })
                            } else {
                                s1
                            },
                            if (isTerminal(s2)) {
                                terminalMapping.getOrPut(s2, { accountant.freshNonTerminal() })
                            } else {
                                s2
                            }
                        )
                    )
                )
            } else {
                newRules.add(rule.copy())
            }
        }
        terminalMapping.forEach {
            newRules.add(Rule(it.value, listOf(it.key)))
        }

        return ContextFreeGrammar(start, newRules)
    }

    fun toChomskyNormalForm(): ContextFreeGrammar {
        var grammar = this
        if (rules.any { it.to.contains(start) }) {
            val accountant = NodeAccountant()
            accountant.consume(this)
            val newStart = accountant.freshNonTerminal()
            val newRules = mutableSetOf<Rule>()
            rules.forEach { newRules.add(it.copy()) }
            newRules.add(Rule(newStart, listOf(start)))
            grammar = ContextFreeGrammar(newStart, newRules)
        }
        return grammar
            .shrinkLongRules()
            .reduceEpsilonRules()
            .reduceUnitRules()
            .reduceNonGeneratingRules()
            .reduceUnreachable()
            .reduceLongTerminalRules()
    }

    fun toWeakChomskyNormalForm(): ContextFreeGrammar {
        return this
            .shrinkLongRules()
            .reduceUnitRules()
            .reduceNonGeneratingRules()
            .reduceUnreachable()
            .reduceLongTerminalRules()
    }

    fun dumpAsStrings(): List<String> {
        val arrRules = rules.toMutableList()
        arrRules.sortBy {
            if (it.from == start) {
                ""
            } else {
                it.from
            }
        }
        return arrRules.map { "${it.from} ${it.to.joinToString(" ")}" }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ContextFreeGrammar

        if (start != other.start) return false
        if (rules != other.rules) return false

        return true
    }

    override fun hashCode(): Int {
        var result = start.hashCode()
        result = 31 * result + rules.hashCode()
        return result
    }

    companion object {
        const val Epsilon = "eps"
        val terminalMatcher = Regex("$Epsilon|[a-z_]+[0-9]*")
        val nonTerminalMatcher = Regex("[A-Z_]+[0-9]*")

        fun isTerminal(node: String) = terminalMatcher.matches(node)
        fun isNonTerminal(node: String) = nonTerminalMatcher.matches(node)

        fun parseRule(desc: String): Rule {
            val tokens = desc.trim().split(Regex("\\s+"))
            if (tokens.size <= 1)
                throw InvalidFormatException("at least 1 token must be presented on rhs")
            if (!isNonTerminal(tokens[0]))
                throw InvalidFormatException("lhs must be a nonterminal")
            val rhs = tokens.drop(1)
            if (rhs.any { !isNonTerminal(it) && !isTerminal(it) })
                throw InvalidFormatException("unsupported symbol in rhs")
            return Rule(tokens[0], rhs)
        }

        fun rulesFromStrings(desc: List<String>) = desc.map(::parseRule).toMutableSet()

        fun fromStrings(desc: List<String>): ContextFreeGrammar {
            val rules = rulesFromStrings(desc)
            if (rules.isEmpty())
                throw EmptyLanguageException()
            val startRule = parseRule(desc[0])
            return ContextFreeGrammar(startRule.from, rules)
        }
    }
}