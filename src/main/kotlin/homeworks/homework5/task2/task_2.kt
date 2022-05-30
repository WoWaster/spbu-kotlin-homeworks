package homeworks.homework5.task2

import kotlin.random.Random

private const val SMALL_MATRIX_SIZE = 32
private const val BIG_MATRIX_SIZE = 1024

// Use this of you want to see generated matrices,
// but be careful with BIG_MATRIX_SIZE, instead consider using a debugger
private const val PRINT_MATRICES = false

fun main() {
    val smallMatrix = Matrix(List(SMALL_MATRIX_SIZE) { List(SMALL_MATRIX_SIZE) { Random.nextInt() } })
    val bigMatrix = Matrix(List(BIG_MATRIX_SIZE) { List(BIG_MATRIX_SIZE) { Random.nextInt() } })

    if (PRINT_MATRICES) {
        println("Small matrix:\n${smallMatrix}\n\nBig matrix:\n${bigMatrix}\n")
    }

    val smallMatrixCoroutineTime = benchmarkMatrixSquaring(smallMatrix).inWholeMilliseconds
    val bigMatrixCoroutineTime = benchmarkMatrixSquaring(bigMatrix).inWholeMilliseconds

    Matrix.useCoroutines = false

    val smallMatrixSimpleTimes = benchmarkMatrixSquaring(smallMatrix).inWholeMilliseconds
    val bigMatrixSimpleTimes = benchmarkMatrixSquaring(bigMatrix).inWholeMilliseconds

    println("Matrix multiplication: matrix²")
    println("Single threaded algorithm vs. coroutine algorithm")
    println(
        "($SMALL_MATRIX_SIZE*$SMALL_MATRIX_SIZE)² matrix: $smallMatrixSimpleTimes ms vs. $smallMatrixCoroutineTime ms"
    )
    println(
        "($BIG_MATRIX_SIZE*$BIG_MATRIX_SIZE)² matrix: $bigMatrixSimpleTimes ms vs. $bigMatrixCoroutineTime ms"
    )
}
