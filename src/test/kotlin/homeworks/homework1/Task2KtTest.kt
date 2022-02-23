package homeworks.homework1

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test

internal class Task2KtTest {

    @Test
    fun `primes below 100`() {
        val primes =
            intArrayOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97)
        assertArrayEquals(sieveOfEratosthenes(100), primes)
    }

    @Test
    fun `primes below prime 59`() {
        val primes = intArrayOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59)
        assertArrayEquals(sieveOfEratosthenes((59)), primes)
    }

    @Test
    fun `no primes`() {
        assertArrayEquals(sieveOfEratosthenes(1), IntArray(0))
    }
}
