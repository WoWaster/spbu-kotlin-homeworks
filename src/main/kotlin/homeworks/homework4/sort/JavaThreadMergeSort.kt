package homeworks.homework4.sort

internal class JavaThreadMergeSort<E : Comparable<E>>(list: MutableList<E>, private val nOfThreads: Int) :
    MergeSort<E>(list) {
    override fun sort() {
        if (nOfThreads == 1) {
            SingleThreadMergeSort(list).sort()
            return
        }

        val (leftThreads, rightThreads) = divideThreads(nOfThreads)
        val left = Thread { JavaThreadMergeSort(list.subList(0, list.size / 2), leftThreads).sort() }
        val right = Thread { JavaThreadMergeSort(list.subList(list.size / 2, list.size), rightThreads).sort() }
        left.start()
        right.start()
        left.join()
        right.join()
        merge()
    }
}
