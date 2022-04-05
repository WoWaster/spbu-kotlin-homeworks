package tests.test1.task1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PriorityQueueTest {
    @Test
    fun enqueue() {
        val queue = PriorityQueue<Int, String>()
        queue.enqueue(1, "one")
        queue.enqueue(100, "hundred")
        queue.enqueue(20, "twenty")

        assertEquals(listOf("one", "twenty", "hundred"), queue.list)
    }

    @Test
    fun peek() {
        val queue = PriorityQueue<Int, String>()
        queue.enqueue(1, "one")
        queue.enqueue(100, "hundred")
        queue.enqueue(20, "twenty")

        assertEquals("hundred", queue.peek())
    }

    @Test
    fun remove() {
        val queue = PriorityQueue<Int, String>()
        queue.enqueue(1, "one")
        queue.enqueue(100, "hundred")
        queue.enqueue(20, "twenty")

        queue.remove()
        assertEquals(listOf("one", "twenty"), queue.list)
    }

    @Test
    fun roll() {
        val queue = PriorityQueue<Int, String>()
        queue.enqueue(1, "one")
        queue.enqueue(100, "hundred")
        queue.enqueue(20, "twenty")

        assertEquals("hundred", queue.roll())
        assertEquals(listOf("one", "twenty"), queue.list)
    }
}
