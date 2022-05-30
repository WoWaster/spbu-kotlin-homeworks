package homeworks.homework5.task2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Named
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class MatrixTest {
    @Test
    fun `incorrect input`() {
        val exception = assertThrows<IllegalStateException> { Matrix(listOf(1, 2, 3), listOf(4, 5)) }
        assertEquals("Matrix rows have not equal column count.", exception.message)
    }

    @ParameterizedTest(name = "Simple matrix multiplication: {0}")
    @MethodSource("timesInput")
    fun simpleMatrixMultiplication(matrix1: Matrix, matrix2: Matrix, expected: Matrix) {
        Matrix.useCoroutines = false
        val actual = matrix1 * matrix2
        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "Coroutine matrix multiplication: {0}")
    @MethodSource("timesInput")
    fun coroutineMatrixMultiplication(matrix1: Matrix, matrix2: Matrix, expected: Matrix) {
        Matrix.useCoroutines = true
        val actual = matrix1 * matrix2
        assertEquals(expected, actual)
    }

    @Test
    fun `incompatible matrices multiplication`() {
        val matrix1 = Matrix(
            listOf(3, 8, 3),
            listOf(3, 9, 9),
            listOf(1, 5, 5),
        )
        val matrix2 = Matrix(
            listOf(80), listOf(58), listOf(74), listOf(100), listOf(78)
        )

        val exception = assertThrows<IllegalArgumentException> { matrix1 * matrix2 }
        assertEquals(
            "The number of columns in the first matrix must be equal to the number of rows in the second matrix.",
            exception.message
        )
    }

    companion object {
        @JvmStatic
        fun timesInput() = listOf(
            Arguments.of(
                Named.of(
                    "square matrix",
                    Matrix(
                        listOf(3, 8, 3), listOf(3, 9, 9), listOf(1, 5, 5),
                    )
                ),
                Matrix(
                    listOf(1, 3, 6), listOf(2, 6, 7), listOf(5, 1, 8),
                ),
                Matrix(
                    listOf(34, 60, 98), listOf(66, 72, 153), listOf(36, 38, 81)
                )
            ),
            Arguments.of(
                Named.of(
                    "tall matrix",
                    Matrix(
                        listOf(7, 4, 6),
                        listOf(1, 6, 5),
                        listOf(6, 5, 5),
                        listOf(8, 5, 8),
                        listOf(2, 4, 9),
                    )
                ),
                Matrix(
                    listOf(4), listOf(4), listOf(6),
                ),
                Matrix(
                    listOf(80), listOf(58), listOf(74), listOf(100), listOf(78)
                )
            ),
            Arguments.of(
                Named.of(
                    "wide matrix",
                    Matrix(
                        listOf(5, 1, 1, 6, 4, 8, 5),
                        listOf(9, 8, 8, 7, 1, 8, 4),
                        listOf(4, 1, 3, 9, 2, 7, 9),
                    )
                ),
                Matrix(
                    listOf(9, 3, 7, 7),
                    listOf(7, 4, 8, 9),
                    listOf(3, 8, 7, 8),
                    listOf(9, 3, 4, 5),
                    listOf(9, 7, 2, 6),
                    listOf(6, 9, 8, 4),
                    listOf(1, 9, 4, 5),
                ),
                Matrix(
                    listOf(198, 190, 166, 163), listOf(285, 259, 293, 292), listOf(202, 225, 189, 191)
                )
            )
        )
    }
}
