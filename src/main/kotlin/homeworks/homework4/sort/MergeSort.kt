package homeworks.homework4.sort

import kotlin.math.ceil
import kotlin.math.floor

internal abstract class MergeSort<E : Comparable<E>>(val list: MutableList<E>) {
    abstract fun sort()

    fun merge() {
        val middle = list.size / 2
        val end = list.size

        var leftCounter = 0
        var rightCounter = middle
        val source = list.toMutableList()

        for (i in 0 until list.size) {
            if (leftCounter < middle && (rightCounter >= end || source[leftCounter] <= source[rightCounter])) {
                list[i] = source[leftCounter++]
            } else {
                list[i] = source[rightCounter++]
            }
        }
    }

    companion object {
        fun divideThreads(nOfThreads: Int): Pair<Int, Int> {
            val left = ceil(nOfThreads.toDouble() / 2).toInt()
            val right = floor(nOfThreads.toDouble() / 2).toInt()
            return Pair(left, right)
        }
    }
}
