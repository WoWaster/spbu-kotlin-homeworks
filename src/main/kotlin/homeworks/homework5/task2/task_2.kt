//        @Suppress("LongMethod")
package homeworks.homework5.task2

import kotlin.random.Random
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

const val SMALL_MATRIX_SIZE = 32
const val BIG_MATRIX_SIZE = 1024

const val REPEAT_TIMES = 10

// Use this of you want to see generated matrices,
// but be careful with BIG_MATRIX_SIZE, instead consider using a debugger
const val PRINT_MATRICES = false

@OptIn(ExperimentalTime::class)
fun main() {
    val smallMatrix = Matrix(List(SMALL_MATRIX_SIZE) { List(SMALL_MATRIX_SIZE) { Random.nextInt() } })
    val bigMatrix = Matrix(List(BIG_MATRIX_SIZE) { List(BIG_MATRIX_SIZE) { Random.nextInt() } })

    if (PRINT_MATRICES) {
        println("Small matrix:\n${smallMatrix}\n\nBig matrix:\n${bigMatrix}\n")
    }

    val smallMatrixCoroutineSquareTimes = mutableListOf<Long>()
    val bigMatrixCoroutineSquareTimes = mutableListOf<Long>()

    repeat(REPEAT_TIMES) {
        smallMatrixCoroutineSquareTimes.add(measureTime { smallMatrix * smallMatrix }.inWholeMilliseconds)
        bigMatrixCoroutineSquareTimes.add(measureTime { bigMatrix * bigMatrix }.inWholeMilliseconds)
    }

    Matrix.useCoroutines = false

    val smallMatrixSimpleSquareTimes = mutableListOf<Long>()
    val bigMatrixSimpleSquareTimes = mutableListOf<Long>()

    repeat(REPEAT_TIMES) {
        smallMatrixSimpleSquareTimes.add(measureTime { smallMatrix * smallMatrix }.inWholeMilliseconds)
        bigMatrixSimpleSquareTimes.add(measureTime { bigMatrix * bigMatrix }.inWholeMilliseconds)
    }

    println("Single threaded algorithm vs. coroutine algorithm")
    println(
        "$SMALL_MATRIX_SIZE*$SMALL_MATRIX_SIZE matrix: ${smallMatrixSimpleSquareTimes.average()} ms " +
            "vs. ${smallMatrixCoroutineSquareTimes.average()} ms"
    )
    println(
        "$BIG_MATRIX_SIZE*$BIG_MATRIX_SIZE matrix: ${bigMatrixSimpleSquareTimes.average()} ms " +
            "vs. ${bigMatrixCoroutineSquareTimes.average()} ms"
    )
}
