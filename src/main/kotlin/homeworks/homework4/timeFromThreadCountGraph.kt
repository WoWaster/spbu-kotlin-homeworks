package homeworks.homework4

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
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

private const val LIST_SIZE = 1_000_000

private const val SMALL_STEP = 4
private const val BIG_STEP = 64

// From min to middle small step is used, and from middle to max big step is used
private const val MIN_THREAD_COUNT = 4
private const val MIDDLE_THREAD_COUNT = 64
private const val MAX_THREAD_COUNT = 1024

@OptIn(ExperimentalTime::class)
fun timeFromThreadCountGraph(): Plot {
    val listToSort = generateRandomList(LIST_SIZE)
    val results = linkedMapOf<Int, Long>() // there's need to preserve the insertion order

    val nOfThreadsSet = mutableSetOf<Int>().apply {
        this.add(1)
        this.add(2)
        this.addAll(MIN_THREAD_COUNT..MIDDLE_THREAD_COUNT step SMALL_STEP)
        this.addAll(MIDDLE_THREAD_COUNT..MAX_THREAD_COUNT step BIG_STEP)
    }

    for (nOfThreads in nOfThreadsSet) {
        val time = measureTime { listToSort.toMutableList().mergeSort(nOfThreads) }
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

    val lookAndFeel = scaleYContinuous(format = "{} ms") + scaleXContinuous(
        format = "{}",
        breaks = nOfThreadsSet.toList(),
    ) + ggsize(GRAPH_WIDTH, GRAPH_HEIGHT) + labs(
        title = "Multithreaded mergesort with Java threads.",
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
