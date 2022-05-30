package homeworks.homework4

import homeworks.homework4.sort.ThreadingType
import homeworks.homework4.sort.mergeSort
import homeworks.homework4.sort.mergeSorted
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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
        val threadCount = Random.nextInt(2, Runtime.getRuntime().availableProcessors() + 1)
        list.mergeSort(ThreadingType.JAVA_THREADS, threadCount)
        assertEquals(stdlibSortedList, list)
    }

    @RepeatedTest(10, name = "Mergesort with coroutines {currentRepetition}/{totalRepetitions}")
    fun mergeSortCoroutine() {
        val list = generateRandomList(Random.nextInt(10, 1000)).toMutableList()
        val stdlibSortedList = list.sorted()
        val threadCount = Random.nextInt(2, Runtime.getRuntime().availableProcessors() + 1)
        list.mergeSort(ThreadingType.COROUTINES, threadCount)
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
        val threadCount = Random.nextInt(2, Runtime.getRuntime().availableProcessors() + 1)
        val sortedList = list.mergeSorted(ThreadingType.JAVA_THREADS, threadCount)
        assertEquals(stdlibSortedList, sortedList)
    }

    @RepeatedTest(10, name = "Mergesorted with coroutines {currentRepetition}/{totalRepetitions}")
    fun mergeSortedCoroutine() {
        val list = generateRandomList(Random.nextInt(10, 1000))
        val stdlibSortedList = list.sorted()
        val threadCount = Random.nextInt(2, Runtime.getRuntime().availableProcessors() + 1)
        val sortedList = list.mergeSorted(ThreadingType.COROUTINES, threadCount)
        assertEquals(stdlibSortedList, sortedList)
    }

    @Test
    fun `nOfThreads less than 1`() {
        val list = listOf(1, 3, 2)
        val exception = assertThrows<IllegalArgumentException> { list.mergeSorted(ThreadingType.JAVA_THREADS, -1) }
        assertEquals("Number of threads can't be less than 1.", exception.message)
    }
}
