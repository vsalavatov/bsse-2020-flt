package com.bsse2018.salavatov.flt.graphdb.evaluator

class NotConnectedException : Exception("Not connected!")
class GraphDoesNotExistException(name: String) : Exception("Graph \"$name\" does not exist")
class LogicErrorException(desc: String) : Exception(desc)

sealed class ScriptEvaluationResult
object ResultUnit : ScriptEvaluationResult()
data class ResultList(val results: List<ScriptEvaluationResult>) : ScriptEvaluationResult() {
    constructor(vararg results: ScriptEvaluationResult) : this(listOf(*results))
}

data class ResultOutput(val text: String) : ScriptEvaluationResult()
