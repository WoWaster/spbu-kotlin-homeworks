package homeworks.homework4.sort

/**
 * Sorts list using merge sort.
 * [nOfThreads] controls used algorithm:
 * 1 will use basic single core algorithm, while number >= 2 will use multithreaded algorithm.
 * [ThreadingType.SINGLE_THREAD] with [nOfThreads] = 1 means nothing.
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
 * [nOfThreads] controls used algorithm:
 * 1 will use basic single core algorithm, while number >= 2 will use multithreaded algorithm.
 * [ThreadingType.SINGLE_THREAD] with [nOfThreads] = 1 means nothing.
 */
fun <E : Comparable<E>> List<E>.mergeSorted(
    threadingType: ThreadingType = ThreadingType.SINGLE_THREAD,
    nOfThreads: Int = 1
): List<E> {
    val list = this.toMutableList()
    list.mergeSort(threadingType, nOfThreads)
    return list
}
