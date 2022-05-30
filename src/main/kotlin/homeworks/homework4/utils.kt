package homeworks.homework4

import java.awt.Desktop
import java.io.File
import kotlin.random.Random

fun generateRandomList(nOfElements: Int): List<Int> = List(nOfElements) { Random.nextInt(Int.MIN_VALUE, Int.MAX_VALUE) }

fun present(filePath: String) {
    println("Trying to open file in browser...")
    val file = File(filePath)
    try {
        Desktop.getDesktop().browse(file.toURI())
    } catch (e: UnsupportedOperationException) {
        println("Sorry, ${e.message} Please open file yourself.")
    }
    println("Path to file is: ${file.canonicalPath}")
}
