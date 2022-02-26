package homeworks.homework1

import kotlin.system.exitProcess

fun sieveOfEratosthenes(bound: Int): IntArray {
    require(bound > -1) { "bound can't be below 0" }

    val isPrimeNumber = BooleanArray(bound + 1) { true }
    isPrimeNumber[0] = false
    isPrimeNumber[1] = false

    var number = 2
    while (number * number <= bound) {
        if (isPrimeNumber[number]) {
            for (i in (number * number)..bound step number) {
                isPrimeNumber[i] = false
            }
        }
        number++
    }

    val primes = mutableListOf<Int>()
    for ((index, isPrime) in isPrimeNumber.withIndex()) {
        if (isPrime) {
            primes.add(index)
        }
    }

    return primes.toIntArray()
}

fun main() {
    println("This app will print every prime below given number.")
    print("Please enter number: ")
    val number = readln().toIntOrNull()

    if (number == null) {
        println("Non integer input are not allowed.")
        exitProcess(0)
    }

    if (number < 2) {
        println("There are no primes below $number.")
        exitProcess(0)
    }

    val primes = sieveOfEratosthenes(number)
    print("All primes below $number: ${primes.joinToString(separator = " ")}")
}
