package homeworks.homework4.sort

/**
 * Sorts mutable list in-place using merge sort.
 * [threadingType] decides which algorithm to use.
 * [nOfThreads] = 1 always use single thread algorithm.
 * [ThreadingType.SINGLE_THREAD] with any [nOfThreads] means single threaded algorithm.
 */
fun <E : Comparable<E>> MutableList<E>.mergeSort(
    threadingType: ThreadingType = ThreadingType.SINGLE_THREAD,
    nOfThreads: Int = 1
) {
    require(nOfThreads >= 1) { "Number of threads can't be less than 1." }
    val mergeSort = when (threadingType) {
        ThreadingType.SINGLE_THREAD -> SingleThreadMergeSort(this)
        ThreadingType.JAVA_THREADS -> JavaThreadMergeSort(this, nOfThreads)
        ThreadingType.COROUTINES -> CoroutineMergeSort(this, nOfThreads)
    }
    mergeSort.sort()
}

/**
 * Sorts list using merge sort.
 * [threadingType] decides which algorithm to use.
 * [nOfThreads] = 1 always use single thread algorithm.
 * [ThreadingType.SINGLE_THREAD] with any [nOfThreads] means single threaded algorithm.
 */
fun <E : Comparable<E>> List<E>.mergeSorted(
    threadingType: ThreadingType = ThreadingType.SINGLE_THREAD,
    nOfThreads: Int = 1
): List<E> {
    val list = this.toMutableList()
    list.mergeSort(threadingType, nOfThreads)
    return list
}
