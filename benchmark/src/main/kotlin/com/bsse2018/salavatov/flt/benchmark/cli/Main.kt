package com.bsse2018.salavatov.flt.benchmark.cli

import com.bsse2018.salavatov.flt.automata.PushDownAutomaton
import com.bsse2018.salavatov.flt.algorithms.CFPQMatrixQuery
import com.bsse2018.salavatov.flt.algorithms.CFPQTensorQuery
import com.bsse2018.salavatov.flt.grammars.ContextFreeGrammar
import com.bsse2018.salavatov.flt.algorithms.HellingsQuery
import com.bsse2018.salavatov.flt.utils.Graph
import com.bsse2018.salavatov.flt.utils.graphFromStrings
import java.io.File
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

object StatusLine {
    val width = 100
    var lastProgress: Double? = null
    var lastExtra = ""

    fun statusLine(progress: Double, extra: String = "") {
        val filled = (width * progress).toInt()
        clear()
        print("[${"#".repeat(filled)}${" ".repeat(width - filled)}] ${"%.2f".format(progress * 100.0)}% $extra\r")
        lastProgress = progress
        lastExtra = extra
    }

    fun log(msg: String) {
        clear()
        println(msg)
        lastProgress?.let {
            statusLine(it, lastExtra)
        }
    }

    fun clear() {
        print("\r${" ".repeat(width + lastExtra.length + 10)}\r")
    }
}

class QueryHandler(
    val name: String,
    val handler: (grammarDesc: List<String>, graph: Graph) -> (() -> Set<Pair<Int, Int>>?)
)

var subjects = listOf(
    QueryHandler("Hellings") { grammarDesc, graph ->
        try {
            val wcnf = ContextFreeGrammar.fromStrings(grammarDesc).toWeakChomskyNormalForm()
            return@QueryHandler { HellingsQuery(graph, wcnf) }
        } catch (e: Exception) {
            { null }
        }
    },
    QueryHandler("Matrix") { grammarDesc, graph ->
        try {
            val wcnf = ContextFreeGrammar.fromStrings(grammarDesc).toWeakChomskyNormalForm()
            return@QueryHandler {
                CFPQMatrixQuery(
                    graph,
                    wcnf
                )
            }
        } catch (e: Exception) {
            { null }
        }
    },
    QueryHandler("Tensor") { grammarDesc, graph ->
        try {
            val pda = PushDownAutomaton.fromStrings(grammarDesc)
            return@QueryHandler { CFPQTensorQuery(graph, pda) }
        } catch (e: Exception) {
            { null }
        }
    }
)

@ExperimentalTime
fun runConfiguration(
    handler: (grammarDesc: List<String>, graph: Graph) -> () -> Set<Pair<Int, Int>>?,
    grammarDesc: List<String>,
    graph: Graph,
    samples: Int = 3
): Pair<Double, Int>? {
    val prepared = handler(grammarDesc, graph)

    // heat up JIT
    var result: Set<Pair<Int, Int>>? = null
    val duration = measureTime {
        result = prepared()
    }
    if (duration.inSeconds > 5.0)
        StatusLine.log("heated (${duration.inSeconds}s)")
    result ?: return null

    val durations = (1..samples).map {
        System.gc()
        val duration = measureTime {
            prepared()
        }
        if (duration.inSeconds > 5.0) {
            StatusLine.log("sample took ${"%.1f".format(duration.inSeconds)}s")
        }
        StatusLine.statusLine(StatusLine.lastProgress ?: 0.0, StatusLine.lastExtra + "+")
        duration
    }

    return Pair(
        durations.sumByDouble { it.inSeconds }.div(samples),
        result!!.size
    )
}

@ExperimentalTime
fun runBenchmark(path: String, reportDestination: String, graphOrder: (String) -> Double) {
    val data = File(path)
    val benchmarkName = data.name
    println("Benchmark: $benchmarkName")
    val grammarsFolder = File("$path/grammars")
    val graphsFolder = File("$path/graphs")

    val grammars = grammarsFolder.list()!!
        .filter { !it.startsWith("!") }
        .sorted()
    val graphs = graphsFolder.list()!!
        .filter { !it.startsWith("!") }
        .sortedBy(graphOrder)

    println("Grammars: ${grammars.size} | Graphs: ${graphs.size}")

    val totalActions = graphs.size * subjects.size * grammars.size
    var doneActions = 0

    StatusLine.statusLine(0.0)

    val reportFile = File("$reportDestination/$benchmarkName.csv")
    StatusLine.log("Generating report header")

    fun subjectFullName(subject: QueryHandler, grammarFilename: String) = "${subject.name}-$grammarFilename"

    reportFile.apply {
        writeText("")
        subjects.forEach { subject ->
            grammars.forEach { grammarFilename ->
                appendText(",${subjectFullName(subject, grammarFilename)},")
            }
        }
        appendText("\n")
    }

    StatusLine.log("Running measurements...")
    graphs.forEach { graphFilename ->
        reportFile.appendText(graphFilename)
        val graph = File("${graphsFolder.absolutePath}/$graphFilename").readLines()
            .filter { it.isNotEmpty() }
            .let {
                graphFromStrings(it)
            }
        subjects.forEach { subject ->
            grammars.forEach { grammarFilename ->
                StatusLine.statusLine(
                    doneActions.toDouble() / totalActions,
                    "$graphFilename - ${subject.name} - $grammarFilename"
                )
                val grammarDesc = File("${grammarsFolder.absolutePath}/$grammarFilename").readLines()
                    .filter { it.isNotEmpty() }

                val result = runConfiguration(
                    subject.handler,
                    grammarDesc,
                    graph
                )

                result?.apply {
                    reportFile.appendText(",${"%.6f".format(first)},${second}")
                } ?: run {
                    reportFile.appendText(",,")
                }

                doneActions++
                StatusLine.statusLine(
                    doneActions.toDouble() / totalActions,
                    "$graphFilename - ${subject.name} - $grammarFilename"
                )
            }
        }
        reportFile.appendText("\n")
    }
}

@ExperimentalTime
fun main(args: Array<String>) {
    if (args.size < 4) {
        println("Arguments: <path to benchmark dataset> <where to put report csv's> <dataset name> <algorithms... from {Hellings, Matrix, Tensor}>")
        return
    }
    val datasetPath = args[0]
    val reportDestination = args[1]
    val tests = listOf(args[2])

    subjects = subjects.filter { it.name in args.drop(3) }
    val ordering = mapOf(
        "FullGraph" to { str: String ->
            val num = str.substring("fullgraph_".length)
            num.toDouble()
        },
        "SparseGraph" to { str: String ->
            val num = str.substring(1).split("k-")
            num.map {
                try {
                    it.toDouble()
                } catch (e: Exception) {
                    0.0
                }
            }.sum()
        },
        "WorstCase" to { str: String ->
            val num = str.substring("worstcase_".length)
            num.toDouble()
        },
        "MemoryAliases" to { str: String ->
            str.hashCode().toDouble()
        }
    )

    println("CFPQ algorithms benchmark tool")
    println()
    println("Subjects: ${subjects.map { it.name }.joinToString(", ")}")
    println()

    tests.forEach {
        runBenchmark("$datasetPath/$it", reportDestination, ordering[it] ?: error("Unknown dataset"))
        println()
    } ?: println("No data found...")
}