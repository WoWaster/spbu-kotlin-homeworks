package homeworks.homework4

import homeworks.homework4.sort.ThreadingType
import jetbrains.letsPlot.GGBunch
import jetbrains.letsPlot.export.ggsave

fun main() {
    val timeThreadCountGraphThreads = timeFromThreadCountGraph()
    val timeListSizeGraphThreads = timeFromListSize()
    val timeThreadCountGraphCoroutines = timeFromThreadCountGraph(ThreadingType.COROUTINES)
    val timeListSizeGraphCoroutines = timeFromListSize(ThreadingType.COROUTINES)

    var plotCount = 0
    val plots = GGBunch().addPlot(timeThreadCountGraphThreads, 0, plotCount++)
        .addPlot(timeListSizeGraphThreads, 0, GRAPH_HEIGHT * plotCount++)
        .addPlot(timeThreadCountGraphCoroutines, 0, GRAPH_HEIGHT * plotCount++)
        .addPlot(timeListSizeGraphCoroutines, 0, GRAPH_HEIGHT * plotCount)

    val file = ggsave(plots, "plot.html")
    present(file)
}
