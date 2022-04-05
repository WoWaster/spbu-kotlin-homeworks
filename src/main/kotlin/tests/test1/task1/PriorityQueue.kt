package tests.test1.task1

import java.util.TreeMap

class PriorityQueue<K : Comparable<K>, E> {
    private val _queue = TreeMap<K, E>()
    val list: List<E>
        get() = _queue.map { it.value }

    fun enqueue(priority: K, element: E) {
        _queue[priority] = element
    }

    fun peek(): E = _queue.lastEntry().value

    fun remove() = _queue.pollLastEntry()

    fun roll(): E = _queue.pollLastEntry().value
}
