package homeworks.homework4

import jetbrains.letsPlot.GGBunch
import jetbrains.letsPlot.export.ggsave

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
