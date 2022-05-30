package homeworks.homework4

import jetbrains.letsPlot.geom.geomSmooth
import jetbrains.letsPlot.ggsize
import jetbrains.letsPlot.intern.Plot
import jetbrains.letsPlot.label.labs
import jetbrains.letsPlot.letsPlot
import jetbrains.letsPlot.scale.scaleXContinuous
import jetbrains.letsPlot.scale.scaleYContinuous
import jetbrains.letsPlot.tooltips.layerTooltips
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

private const val MAX_THREADS = 64
private const val LIST_SIZE_STEP = 100_000
private const val MIN_LIST_SIZE = 100_000
private const val MAX_LIST_SIZE = 1_000_000

@OptIn(ExperimentalTime::class)
fun timeFromListSize(): Plot {
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

            val time = measureTime { listToSort.toMutableList().mergeSort(nOfThreads) }
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
