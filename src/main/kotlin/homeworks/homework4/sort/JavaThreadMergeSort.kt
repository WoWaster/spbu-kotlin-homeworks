package homeworks.homework4.sort

internal class JavaThreadMergeSort<E : Comparable<E>>(override val list: MutableList<E>, var nOfThreads: Int) :
    MergeSort<E>() {
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
