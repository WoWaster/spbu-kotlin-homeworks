package homeworks.homework5.task2

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

data class Matrix(
    private val matrix: List<List<Int>>
) {
    constructor(vararg rows: List<Int>) : this(rows.asList())

    val nOfRows: Int = matrix.size
    val nOfColumns: Int = matrix[0].size

    init {
        for (row in matrix) {
            if (row.size != nOfColumns) {
                throw IllegalStateException("Matrix rows have not equal column count.")
            }
        }
    }

    override fun toString(): String {
        val matrixView = StringBuilder()

        matrixView.append(matrix.joinToString(separator = "\n") { it.joinToString(separator = " ") })

        return matrixView.toString()
    }

    operator fun get(row: Int, column: Int): Int = matrix[row][column]

    /**
     * By default, matrix multiplication is using coroutines, if you want to use single threaded algorithm
     * consider switching [useCoroutines] flag.
     */
    operator fun times(other: Matrix): Matrix {
        require(nOfColumns == other.nOfRows) {
            "The number of columns in the first matrix must be equal to the number of rows in the second matrix."
        }

        return when (useCoroutines) {
            true -> coroutineTimes(other)
            false -> basicTimes(other)
        }
    }

    private fun basicTimes(other: Matrix): Matrix {
        val resultMatrix = MutableList(nOfRows) { MutableList(other.nOfColumns) { 0 } }

        for (thisRow in 0 until nOfRows) {
            for (otherColumn in 0 until other.nOfColumns) {
                var sum = 0
                for (i in 0 until nOfColumns) {
                    sum += this[thisRow, i] * other[i, otherColumn]
                }
                resultMatrix[thisRow][otherColumn] = sum
            }
        }
        return Matrix(resultMatrix)
    }

    private fun coroutineTimes(other: Matrix): Matrix {
        val resultMatrix = MutableList(nOfRows) { MutableList(other.nOfColumns) { 0 } }

        runBlocking(Dispatchers.Default) {
            for (thisRow in 0 until nOfRows) {
                for (otherColumn in 0 until other.nOfColumns) {
                    launch {
                        var sum = 0
                        for (i in 0 until nOfColumns) {
                            sum += get(thisRow, i) * other[i, otherColumn]
                        }
                        resultMatrix[thisRow][otherColumn] = sum
                    }
                }
            }
        }

        return Matrix(resultMatrix)
    }

    companion object {
        var useCoroutines = true
    }
}
