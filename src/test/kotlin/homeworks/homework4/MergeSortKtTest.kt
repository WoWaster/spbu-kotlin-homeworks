package homeworks.homework4

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.RepeatedTest
import kotlin.random.Random

internal class MergeSortKtTest {

    @RepeatedTest(10, name = "Mergesort with single thread {currentRepetition}/{totalRepetitions}")
    fun mergeSortSingleThread() {
        val list = generateRandomList(Random.nextInt(10, 1000)).toMutableList()
        val stdlibSortedList = list.sorted()
        list.mergeSort()
        assertEquals(stdlibSortedList, list)
    }

    @RepeatedTest(10, name = "Mergesort with multithreading {currentRepetition}/{totalRepetitions}")
    fun mergeSortMultiThread() {
        val list = generateRandomList(Random.nextInt(10, 1000)).toMutableList()
        val stdlibSortedList = list.sorted()
        val threadCount = Random.nextInt(2, Runtime.getRuntime().availableProcessors())
        list.mergeSort(threadCount)
        assertEquals(stdlibSortedList, list)
    }

    @RepeatedTest(10, name = "Mergesorted with single thread {currentRepetition}/{totalRepetitions}")
    fun mergeSortedSingleThread() {
        val list = generateRandomList(Random.nextInt(10, 1000))
        val stdlibSortedList = list.sorted()
        val sortedList = list.mergeSorted()
        assertEquals(stdlibSortedList, sortedList)
    }

    @RepeatedTest(10, name = "Mergesorted with multithreading {currentRepetition}/{totalRepetitions}")
    fun mergeSortedMultiThread() {
        val list = generateRandomList(Random.nextInt(10, 1000))
        val stdlibSortedList = list.sorted()
        val threadCount = Random.nextInt(2, Runtime.getRuntime().availableProcessors())
        val sortedList = list.mergeSorted(threadCount)
        assertEquals(stdlibSortedList, sortedList)
    }
}
