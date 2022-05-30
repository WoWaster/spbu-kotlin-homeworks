package homeworks.homework4.sort

internal class SingleThreadMergeSort<E : Comparable<E>>(override val list: MutableList<E>) : MergeSort<E>() {
    override fun sort() {
        if (list.size <= 1) return

        val left = list.subList(0, list.size / 2)
        val right = list.subList(list.size / 2, list.size)

        SingleThreadMergeSort(left).sort()
        SingleThreadMergeSort(right).sort()

        merge()
    }
}
