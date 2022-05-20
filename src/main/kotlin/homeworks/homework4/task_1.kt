package homeworks.homework4

import jetbrains.letsPlot.GGBunch
import jetbrains.letsPlot.export.ggsave
import jetbrains.letsPlot.geom.geomHLine
import jetbrains.letsPlot.geom.geomLine
import jetbrains.letsPlot.geom.geomSmooth
import jetbrains.letsPlot.geom.geomText
import jetbrains.letsPlot.ggsize
import jetbrains.letsPlot.intern.Plot
import jetbrains.letsPlot.label.labs
import jetbrains.letsPlot.letsPlot
import jetbrains.letsPlot.scale.scaleXContinuous
import jetbrains.letsPlot.scale.scaleYContinuous
import jetbrains.letsPlot.tooltips.layerTooltips
import java.awt.Desktop
import java.io.File
import kotlin.random.Random
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

// Constants for timeFromThreadCountGraph()
const val LIST_SIZE = 1_000_000

const val SMALL_STEP = 4
const val BIG_STEP = 64

const val MIN_THREAD_COUNT = 4 // From min to middle small step is used, and from middle to max big step is used
const val MIDDLE_THREAD_COUNT = 64
const val MAX_THREAD_COUNT = 1024

// Constants for timeFromListSize()
const val MAX_THREADS = 64
const val LIST_SIZE_STEP = 100_000
const val MIN_LIST_SIZE = 100_000
const val MAX_LIST_SIZE = 1_000_000

// Common look-and-feel constants
const val TEXT_VERTICAL_OFFSET = 5
const val GRAPH_WIDTH = 1280
const val GRAPH_HEIGHT = 720

fun main() {
    val timeThreadCountGraphThreads = timeFromThreadCountGraph()
    val timeListSizeGraphThreads = timeFromListSize()
    val timeThreadCountGraphCoroutines = timeFromThreadCountGraph(true)
    val timeListSizeGraphCoroutines = timeFromListSize(true)

    var plotCount = 0
    val plots = GGBunch().addPlot(timeThreadCountGraphThreads, 0, plotCount++)
        .addPlot(timeListSizeGraphThreads, 0, GRAPH_HEIGHT * plotCount++)
        .addPlot(timeThreadCountGraphCoroutines, 0, GRAPH_HEIGHT * plotCount++)
        .addPlot(timeListSizeGraphCoroutines, 0, GRAPH_HEIGHT * plotCount)

    val file = ggsave(plots, "plot.html")
    present(file)
}

@OptIn(ExperimentalTime::class)
fun timeFromThreadCountGraph(useCoroutines: Boolean = false): Plot {
    val listToSort = generateRandomList(LIST_SIZE)
    val results = linkedMapOf<Int, Long>() // there's need to preserve the insertion order

    val nOfThreadsSet = mutableSetOf<Int>().apply {
        this.add(1)
        this.add(2)
        this.addAll(MIN_THREAD_COUNT..MIDDLE_THREAD_COUNT step SMALL_STEP)
        this.addAll(MIDDLE_THREAD_COUNT..MAX_THREAD_COUNT step BIG_STEP)
    }

    for (nOfThreads in nOfThreadsSet) {
        val time = measureTime { listToSort.toMutableList().mergeSort(nOfThreads, useCoroutines) }
        results[nOfThreads] = time.inWholeMilliseconds
    }

    val data = mapOf<String, Any>(
        "threads" to results.keys, "time" to results.values,
        "smooth" to List(nOfThreadsSet.size) { "Smoothed line" },
        "real" to List(nOfThreadsSet.size) { "Original values" }
    )

    val maxTime = results.maxByOrNull { it.value }
    val minTime = results.minByOrNull { it.value }

    val basicPlot = geomLine(tooltips = layerTooltips().format("time", "{} ms")) { color = "real" } + geomSmooth(
        tooltips = layerTooltips().format("time", "{.0f} ms"),
        method = "loess",
        se = false,
    ) {
        color = "smooth"
    }

    val minMaxLines = geomHLine(yintercept = maxTime?.value, linetype = 2) + geomText(
        label = "Maximum: threads=${maxTime?.key}, time=${maxTime?.value} ms",
        x = MAX_THREAD_COUNT / 2,
        y = (maxTime?.value ?: 0) + TEXT_VERTICAL_OFFSET
    ) + geomHLine(
        yintercept = minTime?.value,
        linetype = 2
    ) + geomText(
        label = "Minimum: threads=${minTime?.key}, time=${minTime?.value} ms",
        x = MAX_THREAD_COUNT / 2,
        y = (minTime?.value ?: 0) + TEXT_VERTICAL_OFFSET
    )

    val threadingMechanism = if (useCoroutines) "Kotlin coroutines" else "Java threads"
    val lookAndFeel = scaleYContinuous(format = "{} ms") + scaleXContinuous(
        format = "{}",
        breaks = nOfThreadsSet.toList(),
    ) + ggsize(GRAPH_WIDTH, GRAPH_HEIGHT) + labs(
        title = "Multithreaded mergesort with $threadingMechanism.",
        subtitle = "Time from thread count plot with fixed list size = $LIST_SIZE",
        color = "",
        x = "Number of threads",
        y = "Time to sort"
    )

    return letsPlot(data) {
        x = "threads"
        y = "time"
    } + basicPlot + minMaxLines + lookAndFeel
}

@OptIn(ExperimentalTime::class)
fun timeFromListSize(useCoroutines: Boolean = false): Plot {
    val data = mutableMapOf<String, Any>()
    var plot = letsPlot(data)

    val nOfThreadsSet = mutableSetOf<Int>().apply {
        var i = 1
        while (i <= MAX_THREADS) {
            this.add(i)
            i *= 2
        }
    }

    for (nOfThreads in nOfThreadsSet) {
        val results = linkedMapOf<Int, Long>() // there's need to preserve the insertion order

        for (listSize in MIN_LIST_SIZE..MAX_LIST_SIZE step LIST_SIZE_STEP) {
            val listToSort = generateRandomList(listSize)

            val time = measureTime { listToSort.toMutableList().mergeSort(nOfThreads, useCoroutines) }
            results[listSize] = time.inWholeMilliseconds
        }

        data["nOfElements:$nOfThreads"] = results.keys
        data["time:$nOfThreads"] = results.values
        data["color:$nOfThreads"] = List(results.size) { "Thread count: $nOfThreads" }

        plot += geomSmooth(
            tooltips = layerTooltips().format("time:$nOfThreads", "{.0f} ms"),
            method = "loess",
            se = false,
        ) {
            x = "nOfElements:$nOfThreads"
            y = "time:$nOfThreads"
            color = "color:$nOfThreads"
        }
    }

    val lookAndFeel = scaleYContinuous(format = "{} ms") + scaleXContinuous(
        format = "{,d}",
        breaks = (MIN_LIST_SIZE..MAX_LIST_SIZE step LIST_SIZE_STEP).toList()
    ) + ggsize(GRAPH_WIDTH, GRAPH_HEIGHT) + labs(
        title = "",
        subtitle = "Time from list size plot, each line represents fixed core count.",
        color = "",
        x = "List size",
        y = "Time to sort"
    )

    return plot + lookAndFeel
}

fun generateRandomList(nOfElements: Int): List<Int> = List(nOfElements) { Random.nextInt(Int.MIN_VALUE, Int.MAX_VALUE) }

fun present(filePath: String) {
    println("Trying to open file in browser...")
    val file = File(filePath)
    try {
        Desktop.getDesktop().browse(file.toURI())
    } catch (e: UnsupportedOperationException) {
        println("Sorry, ${e.message} Please open file yourself.")
    }
    println("Path to file is: ${file.canonicalPath}")
}
