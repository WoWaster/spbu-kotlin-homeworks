package homeworks.homework1

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
    println("This app will print every prime below given.")
    print("Please enter n: ")
    val number = readln().toInt()
    val primes = sieveOfEratosthenes(number)
    print("All primes below $number: ")
    for (prime in primes)
        print("$prime ")
}
