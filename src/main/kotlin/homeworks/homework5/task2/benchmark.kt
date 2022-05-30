package homeworks.homework5.task2

import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun benchmarkMatrixSquaring(matrix: Matrix, repeatCount: Int = 10): Duration {
    var timeSum = Duration.ZERO

    repeat(repeatCount) {
        timeSum += measureTime { matrix * matrix }
    }

    return timeSum / repeatCount
}
