package homeworks.homework1.task3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class PerformedCommandStorageTest {
    @Test
    fun `swap out of bounds`() {
        val list = ArrayDeque(listOf(1, 2, 3))
        val exception = assertThrows<IllegalArgumentException> { SwapAction(0, 5).doAction(list) }
        assertEquals("Index(-es) out of list bounds", exception.message)
    }

    @Test
    fun `empty storage`() {
        val storage = PerformedCommandStorage()
        assertEquals(listOf<Int>(), storage.getNumbers())
        assertFalse(storage.hasActions())
    }

    @Test
    fun `element addition`() {
        val storage = PerformedCommandStorage()

        storage.newAction(AddFirstAction(1))
        assertEquals(listOf(1), storage.getNumbers())

        storage.newAction(AddLastAction(2))
        assertEquals(listOf(1, 2), storage.getNumbers())

        storage.newAction(AddFirstAction(3))
        assertEquals(listOf(3, 1, 2), storage.getNumbers())
    }

    @Test
    fun `swapping elements`() {
        val storage = PerformedCommandStorage()
        storage.newAction(AddLastAction(1))
        storage.newAction(AddLastAction(2))
        storage.newAction(SwapAction(0, 1))
        assertEquals(listOf(2, 1), storage.getNumbers())
    }

    @Test
    fun `undo addition to head`() {
        val storage = PerformedCommandStorage()

        storage.newAction(AddLastAction(1))
        storage.newAction(AddFirstAction(2))
        storage.undoAction()

        assertEquals(listOf(1), storage.getNumbers())
    }

    @Test
    fun `undo addition to tail`() {
        val storage = PerformedCommandStorage()

        storage.newAction(AddFirstAction(2))
        storage.newAction(AddLastAction(1))
        storage.undoAction()

        assertEquals(listOf(2), storage.getNumbers())
    }

    @Test
    fun `undo swap`() {
        val storage = PerformedCommandStorage()

        storage.newAction(AddFirstAction(1))
        storage.newAction(AddLastAction(2))
        storage.newAction(SwapAction(0, 1))

        storage.undoAction()
        assertEquals(listOf(1, 2), storage.getNumbers())
    }

    @Test
    fun `undo on empty storage`() {
        val storage = PerformedCommandStorage()
        val exception = assertThrows<IllegalArgumentException> { storage.undoAction() }
        assertEquals("No actions to undo.", exception.message)
    }
}
