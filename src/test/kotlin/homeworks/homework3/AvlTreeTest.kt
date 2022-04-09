package homeworks.homework3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class AvlTreeTest {
    @Test
    fun `getSize() and isEmpty() on empty tree`() {
        val avl = avlTreeOf<Int, Int>()
        assertEquals(0, avl.size)
        assertTrue(avl.isEmpty())
    }

    @Test
    fun `getSize() after addition`() {
        val avl = avlTreeOf("step" to 3, "climb" to 1, "fall" to 42, "work" to 0, "lie" to 70)
        assertEquals(5, avl.size)
    }

    @Test
    fun `getSize() after deletion`() {
        val avl = avlTreeOf("step" to 3, "climb" to 1, "fall" to 42, "work" to 0, "lie" to 70)
        avl.remove("work")
        assertEquals(4, avl.size)
    }

    @Test
    fun `getSize() after value update`() {
        val avl = avlTreeOf("step" to 3, "climb" to 1, "fall" to 42, "work" to 0, "lie" to 70)
        avl["work"] = 8
        assertEquals(5, avl.size)
    }

    @Test
    fun `containsKey() true`() {
        val avl = avlTreeOf("step" to 3, "climb" to 1, "fall" to 42, "work" to 0, "lie" to 70)
        assertTrue(avl.containsKey("fall"))
    }

    @Test
    fun `containsKey() false`() {
        val avl = avlTreeOf<Int, Int>()
        assertFalse(avl.containsKey(42))
    }

    @Test
    fun `containsKey() after deletion`() {
        val avl = avlTreeOf("step" to 3, "climb" to 1, "fall" to 42, "work" to 0, "lie" to 70)
        avl.remove("work")
        assertFalse(avl.containsKey("work"))
    }

    @Test
    fun `containsValue() true`() {
        val avl = avlTreeOf("step" to 3, "climb" to 1, "fall" to 42, "work" to 0, "lie" to 70)
        assertTrue(avl.containsValue(42))
    }

    @Test
    fun `containsValue() false`() {
        val avl = avlTreeOf<Int, Int>()
        assertFalse(avl.containsValue(42))
    }

    @Test
    fun `containsValue() after deletion`() {
        val avl = avlTreeOf("step" to 3, "climb" to 1, "fall" to 42, "work" to 0, "lie" to 70)
        avl.remove("work")
        assertFalse(avl.containsValue(0))
    }

    @Test
    fun `containsValue() after update`() {
        val avl = avlTreeOf("step" to 3, "climb" to 1, "fall" to 42, "work" to 0, "lie" to 70)
        avl["work"] = 8
        assertFalse(avl.containsValue(0))
        assertTrue(avl.containsValue(8))
    }

    @Test
    fun `isEmpty() after deletion`() {
        val avl = avlTreeOf("step" to 3, "climb" to 1)
        assertFalse(avl.isEmpty())
        avl.remove("step")
        avl.remove("climb")
        assertTrue(avl.isEmpty())
    }

    @Test
    fun getEntries() {
        val avl = avlTreeOf("step" to 3, "climb" to 1, "fall" to 42, "work" to 0, "lie" to 70)
        val entries = mapOf("climb" to 1, "fall" to 42, "lie" to 70, "step" to 3, "work" to 0).entries
        assertEquals(entries, avl.entries)
    }

    @Test
    fun getKeys() {
        val avl = avlTreeOf("step" to 3, "climb" to 1, "fall" to 42, "work" to 0, "lie" to 70)
        val keys = mutableSetOf("step", "climb", "fall", "work", "lie")
        assertEquals(keys, avl.keys)
    }

    @Test
    fun getValues() {
        val avl = avlTreeOf("step" to 3, "climb" to 1, "fall" to 42, "work" to 0, "lie" to 70)
        val values = listOf(3, 1, 42, 0, 70).sorted()
        assertEquals(values, avl.values.sorted())
    }

    @Test
    fun `getValues() with duplicates`() {
        val avl = avlTreeOf("step" to 1, "climb" to 1, "fall" to 1, "work" to 0, "lie" to 70)
        val values = listOf(1, 1, 1, 0, 70).sorted()
        assertEquals(values, avl.values.sorted())
    }

    @Test
    fun clear() {
        val avl = avlTreeOf("step" to 3, "climb" to 1, "fall" to 42, "work" to 0, "lie" to 70)
        avl.clear()
        assertTrue(avl.isEmpty())
        assertEquals(0, avl.size)
    }

    @Test
    fun get() {
        val avl = avlTreeOf("step" to 3, "climb" to 1, "fall" to 42, "work" to 0, "lie" to 70)
        assertEquals(42, avl["fall"])
    }

    @Test
    fun `get() null`() {
        val avl = avlTreeOf("step" to 3, "climb" to 1, "fall" to 42, "work" to 0, "lie" to 70)
        assertNull(avl["run"])
    }

    @Test
    fun put() {
        val avl = avlTreeOf<Int, String>()
        avl[7] = "seven"
        avl[2] = "two"
        avl[1] = "one"
        avl[10] = "ten"
        val prev = avl.put(20, "twenty")
        assertNull(prev)
        val entries = mapOf(1 to "one", 2 to "two", 7 to "seven", 10 to "ten", 20 to "twenty").entries
        assertEquals(entries, avl.entries)
    }

    @Test
    fun `put() update value`() {
        val avl = avlTreeOf("step" to 3, "climb" to 1, "fall" to 42, "work" to 0, "lie" to 70)
        val prev = avl.put("work", 8)
        val entries = mapOf("climb" to 1, "fall" to 42, "lie" to 70, "step" to 3, "work" to 8).entries
        assertEquals(entries, avl.entries)
        assertEquals(prev, 0)
    }

    @Test
    fun remove() {
        val avl = avlTreeOf("step" to 3, "climb" to 1, "fall" to 42, "work" to 0, "lie" to 70)
        avl.remove("work")
        val entries = mapOf("step" to 3, "climb" to 1, "fall" to 42, "lie" to 70).entries
        assertEquals(entries, avl.entries)
    }

    @Test
    fun `remove() null`() {
        val avl = avlTreeOf("step" to 3, "climb" to 1, "fall" to 42, "work" to 0, "lie" to 70)
        val entries = mapOf("climb" to 1, "fall" to 42, "lie" to 70, "step" to 3, "work" to 0).entries
        val prev = avl.remove("sleep")
        assertEquals(entries, avl.entries)
        assertNull(prev)
    }
}
