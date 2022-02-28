package homeworks.homework1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class Task2KtTest {

    @Test
    fun `primes below 100`() {
        val primes =
            listOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97)
        assertEquals(primes, sieveOfEratosthenes(100))
    }

    @Test
    fun `primes below prime 59`() {
        val primes = listOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59)
        assertEquals(primes, sieveOfEratosthenes((59)))
    }

    @Test
    fun `no primes`() {
        assertEquals(listOf<Int>(), sieveOfEratosthenes(1))
    }

    @Test
    fun `input below 0`() {
        val exception = assertThrows<IllegalArgumentException> { sieveOfEratosthenes(-42) }
        assertEquals("bound can't be below 0", exception.message)
    }
}
