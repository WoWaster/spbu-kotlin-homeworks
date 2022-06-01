package homeworks.homework3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Named
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class AvlTreeTest {
    @Test
    fun `getSize() and isEmpty() on empty tree`() {
        val avl = avlTreeOf<Int, Int>()
        assertEquals(0, avl.size)
        assertTrue(avl.isEmpty())
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("getSizeInput")
    fun <K : Comparable<K>, V> getSize(avl: AvlTree<K, V>, expected: Int) {
        assertEquals(expected, avl.size)
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("containsKeyInput")
    fun <K : Comparable<K>, V> containsKey(avl: AvlTree<K, V>, key: K, expected: Boolean) {
        assertEquals(expected, avl.containsKey(key))
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("containsValueInput")
    fun <K : Comparable<K>, V> containsValue(avl: AvlTree<K, V>, value: V, expected: Boolean) {
        assertEquals(expected, avl.containsValue(value))
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

    @ParameterizedTest(name = "{0}")
    @MethodSource("getValuesInput")
    fun getValues(avl: AvlTree<String, Int>, expected: List<Int>) {
        assertEquals(expected, avl.values.sorted())
    }

    @Test
    fun clear() {
        val avl = avlTreeOf("step" to 3, "climb" to 1, "fall" to 42, "work" to 0, "lie" to 70)
        avl.clear()
        assertTrue(avl.isEmpty())
        assertEquals(0, avl.size)
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("getInput")
    fun <K : Comparable<K>, V> get(avl: AvlTree<K, V>, key: K, expected: V?) {
        assertEquals(expected, avl[key])
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("putInput")
    fun <K : Comparable<K>, V> put(avl: AvlTree<K, V>, entries: Set<Map.Entry<K, V>>, key: K, value: V, expected: V?) {
        val prev = avl.put(key, value)
        assertEquals(expected, prev)
        assertEquals(entries, avl.entries)
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("removeInput")
    fun <K : Comparable<K>, V> remove(avl: AvlTree<K, V>, entries: Set<Map.Entry<K, V>>, key: K, expected: V?) {
        val prev = avl.remove(key)
        assertEquals(expected, prev)
        assertEquals(entries, avl.entries)
    }

    companion object {
        @JvmStatic
        fun getSizeInput() = listOf(
            Arguments.of(
                Named.of(
                    "addition",
                    avlTreeOf("step" to 3, "climb" to 1, "fall" to 42, "work" to 0, "lie" to 70)
                ),
                5
            ),
            Arguments.of(
                Named.of(
                    "removal",
                    avlTreeOf(
                        "step" to 3,
                        "climb" to 1,
                        "fall" to 42,
                        "work" to 0,
                        "lie" to 70
                    ).apply { this.remove("work") }
                ),
                4
            ),
            Arguments.of(
                Named.of(
                    "value update",
                    avlTreeOf("step" to 3, "climb" to 1, "fall" to 42, "work" to 0, "lie" to 70).apply {
                        this["work"] = 8
                    }
                ),
                5
            )
        )

        @JvmStatic
        fun containsKeyInput() = listOf(
            Arguments.of(
                Named.of("true", avlTreeOf("step" to 3, "climb" to 1, "fall" to 42, "work" to 0, "lie" to 70)),
                "fall", true
            ),
            Arguments.of(
                Named.of("false", avlTreeOf<Int, Int>()),
                42, false
            ),
            Arguments.of(
                Named.of(
                    "removal",
                    avlTreeOf(
                        "step" to 3,
                        "climb" to 1,
                        "fall" to 42,
                        "work" to 0,
                        "lie" to 70
                    ).apply { this.remove("work") }
                ),
                "work", false
            )
        )

        @JvmStatic
        fun containsValueInput() = listOf(
            Arguments.of(
                Named.of("true", avlTreeOf("step" to 3, "climb" to 1, "fall" to 42, "work" to 0, "lie" to 70)),
                42, true
            ),
            Arguments.of(
                Named.of("false", avlTreeOf<Int, Int>()),
                42, false
            ),
            Arguments.of(
                Named.of(
                    "removal",
                    avlTreeOf(
                        "step" to 3,
                        "climb" to 1,
                        "fall" to 42,
                        "work" to 0,
                        "lie" to 70
                    ).apply { this.remove("work") }
                ),
                0, false
            ),
            Arguments.of(
                Named.of(
                    "value update",
                    avlTreeOf("step" to 3, "climb" to 1, "fall" to 42, "work" to 0, "lie" to 70).apply {
                        this["work"] = 8
                    }.also { assertTrue(it.containsValue(8)) }
                ),
                0, false
            )
        )

        @JvmStatic
        fun getValuesInput() = listOf(
            Arguments.of(
                Named.of("basic", avlTreeOf("step" to 3, "climb" to 1, "fall" to 42, "work" to 0, "lie" to 70)),
                listOf(3, 1, 42, 0, 70).sorted(),
            ),
            Arguments.of(
                Named.of(
                    "with duplicates",
                    avlTreeOf("step" to 1, "climb" to 1, "fall" to 1, "work" to 0, "lie" to 70)
                ),
                listOf(1, 1, 1, 0, 70).sorted()
            )
        )

        @JvmStatic
        fun getInput() = listOf(
            Arguments.of(
                Named.of("basic", avlTreeOf("step" to 3, "climb" to 1, "fall" to 42, "work" to 0, "lie" to 70)),
                "fall", 42
            ),
            Arguments.of(
                Named.of("null", avlTreeOf("step" to 3, "climb" to 1, "fall" to 42, "work" to 0, "lie" to 70)),
                "run", null
            )
        )

        @JvmStatic
        fun putInput() = listOf(
            Arguments.of(
                Named.of(
                    "basic",
                    avlTreeOf<Int, String>().apply {
                        this[7] = "seven"
                        this[2] = "two"
                        this[1] = "one"
                        this[10] = "ten"
                    }
                ),
                mapOf(1 to "one", 2 to "two", 7 to "seven", 10 to "ten", 20 to "twenty").entries,
                20, "twenty", null
            ),
            Arguments.of(
                Named.of("value update", avlTreeOf("step" to 3, "climb" to 1, "fall" to 42, "work" to 0, "lie" to 70)),
                mapOf("climb" to 1, "fall" to 42, "lie" to 70, "step" to 3, "work" to 8).entries,
                "work", 8, 0
            )
        )

        @JvmStatic
        fun removeInput() = listOf(
            Arguments.of(
                Named.of("basic", avlTreeOf("step" to 3, "climb" to 1, "fall" to 42, "work" to 0, "lie" to 70)),
                mapOf("step" to 3, "climb" to 1, "fall" to 42, "lie" to 70).entries,
                "work", 0
            ),
            Arguments.of(
                Named.of("null", avlTreeOf("step" to 3, "climb" to 1, "fall" to 42, "work" to 0, "lie" to 70)),
                mapOf("climb" to 1, "fall" to 42, "lie" to 70, "step" to 3, "work" to 0).entries,
                "sleep", null
            )
        )
    }
}
