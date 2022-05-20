package homeworks.homework4

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.math.ceil
import kotlin.math.floor

private class MergeSort<E : Comparable<E>>(
    var list: MutableList<E>,
    var nOfThreads: Int,
    val useCoroutines: Boolean = false
) {
    fun sort() =
        when (nOfThreads) {
            1 -> singleThreadSort()
            else -> when (useCoroutines) {
                true -> coroutineSort()
                false -> multiThreadSort()
            }
        }

    fun singleThreadSort() {
        if (list.size <= 1) return

        val left = list.subList(0, list.size / 2)
        val right = list.subList(list.size / 2, list.size)

        MergeSort(left, 1).sort()
        MergeSort(right, 1).sort()

        merge()
    }

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

    fun multiThreadSort() {
        val (leftThreads, rightThreads) = divideThreads()
        val left = Thread { MergeSort(list.subList(0, list.size / 2), leftThreads).sort() }
        val right = Thread { MergeSort(list.subList(list.size / 2, list.size), rightThreads).sort() }
        left.start()
        right.start()
        left.join()
        right.join()
        merge()
    }

    fun coroutineSort() = runBlocking(Dispatchers.Default) {
        insideCoroutineSort()
    }

    suspend fun insideCoroutineSort() {
        if (nOfThreads == 1) {
            singleThreadSort()
            return
        }
        coroutineScope {
            val (leftThreads, rightThreads) = divideThreads()

            launch { MergeSort(list.subList(0, list.size / 2), leftThreads, true).insideCoroutineSort() }
            launch { MergeSort(list.subList(list.size / 2, list.size), rightThreads, true).insideCoroutineSort() }
        }
        merge()
    }

    fun divideThreads(): Pair<Int, Int> {
        val leftThreads = nOfThreads
        val left = ceil(leftThreads.toDouble() / 2).toInt()
        val right = floor(leftThreads.toDouble() / 2).toInt()
        return Pair(left, right)
    }
}

/**
 * Sorts list using merge sort.
 * [nOfThreads] controls used algorithm:
 * 1 will use basic single core algorithm, while number >= 2 will use multithreaded algorithm.
 * [useCoroutines] with [nOfThreads] = 1 means nothing.
 */
fun <E : Comparable<E>> MutableList<E>.mergeSort(nOfThreads: Int = 1, useCoroutines: Boolean = false) {
    require(nOfThreads >= 1) { "Number of threads can't be less than 1." }
    val mergeSort = MergeSort(this, nOfThreads, useCoroutines)
    mergeSort.sort()
}

/**
 * Sorts list using merge sort.
 * [nOfThreads] controls used algorithm:
 * 1 will use basic single core algorithm, while number >= 2 will use multithreaded algorithm.
 * [useCoroutines] with [nOfThreads] = 1 means nothing.
 */
fun <E : Comparable<E>> List<E>.mergeSorted(nOfThreads: Int = 1, useCoroutines: Boolean = false): List<E> {
    require(nOfThreads >= 1) { "Number of threads can't be less than 1." }
    val mergeSort = MergeSort(this.toMutableList(), nOfThreads, useCoroutines)
    mergeSort.sort()
    return mergeSort.list
}
