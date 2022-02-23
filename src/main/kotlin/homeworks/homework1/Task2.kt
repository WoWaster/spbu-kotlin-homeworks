package homeworks.homework1

import kotlin.system.exitProcess

fun sieveOfEratosthenes(bound: Int): IntArray {
    val numbers = BooleanArray(bound + 1) { true }
    numbers[0] = false
    numbers[1] = false

    var number = 2
    while (number * number <= bound) {
        if (numbers[number]) {
            for (i in number * number..bound step number) {
                numbers[i] = false
            }
        }
        number++
    }

    val primes = IntArray(numbers.count { it })
    var j = 0
    for (i in 2..bound) {
        if (numbers[i]) {
            primes[j] = i
            j++
        }
    }

    return primes
}

fun main() {
    println("This app will print every prime below given number.")
    print("Please enter number: ")
    val number = readln().toInt()

    if (number < 2) {
        println("There are no primes below $number.")
        exitProcess(0)
    }

    val primes = sieveOfEratosthenes(number)
    print("All primes below $number: ")
    for (prime in primes)
        print("$prime ")
}
