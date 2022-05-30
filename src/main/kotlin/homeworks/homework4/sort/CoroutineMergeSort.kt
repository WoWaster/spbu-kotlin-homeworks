package homeworks.homework4.sort

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

internal class CoroutineMergeSort<E : Comparable<E>>(list: MutableList<E>, private val nOfThreads: Int) :
    MergeSort<E>(list) {
    override fun sort() = runBlocking(Dispatchers.Default) {
        insideCoroutineSort()
    }

    private suspend fun insideCoroutineSort() {
        if (nOfThreads == 1) {
            SingleThreadMergeSort(list).sort()
            return
        }
        coroutineScope {
            val (leftThreads, rightThreads) = divideThreads(nOfThreads)

            launch { CoroutineMergeSort(list.subList(0, list.size / 2), leftThreads).insideCoroutineSort() }
            launch { CoroutineMergeSort(list.subList(list.size / 2, list.size), rightThreads).insideCoroutineSort() }
        }
        merge()
    }
}
