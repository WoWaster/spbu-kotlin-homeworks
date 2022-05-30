package homeworks.homework4

import jetbrains.letsPlot.GGBunch
import jetbrains.letsPlot.export.ggsave

fun main() {
    val timeThreadCountGraph = timeFromThreadCountGraph()
    val timeListSizeGraph = timeFromListSize()

    val plots = GGBunch().addPlot(timeThreadCountGraph, 0, 0).addPlot(timeListSizeGraph, 0, GRAPH_HEIGHT)

    val file = ggsave(plots, "plot.html")
    present(file)
}
