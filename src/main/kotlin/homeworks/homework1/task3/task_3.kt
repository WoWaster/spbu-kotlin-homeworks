package homeworks.homework1.task3

fun main() {
    val storage = PerformedCommandStorage()

    val cli = PerformedCommandStorageCLI(storage)

    cli.mainLoop()
}

class PerformedCommandStorageCLI(private val storage: PerformedCommandStorage) {
    private val validCommands = listOf("help", "print", "addFirst", "addLast", "swap", "undo", "exit")
    private val amountOfArguments = mapOf(
        "help" to 0, "print" to 0, "addFirst" to 1, "addLast" to 1, "swap" to 2, "undo" to 0, "exit" to 0
    )
    private val help = """
        Available commands:
        * print - prints current numbers in storage
        * addFirst N - adds N to the head of the numbers list
        * addLast N - adds N to the tail of the numbers list
        * swap N M - moves element at index N to index M
        * undo - reverts last command
        * exit 
    """.trimIndent()

    private var isRunning = true

    private fun String.isInt() = toIntOrNull()?.let { true } ?: false

    private fun isValidCommand(command: String) = command in validCommands
    private fun isArgumentNumberCorrect(input: List<String>) = input.size == amountOfArguments.getValue(input[0]) + 1
    private fun isArgumentsIntegers(input: List<String>) =
        input.subList(1, amountOfArguments.getValue(input[0]) + 1).all { it.isInt() }

    private fun runCommand(input: List<String>) {
        when {
            input[0] == "help" -> println(help)
            input[0] == "print" -> println(
                storage.numbers.takeIf { it.isNotEmpty() }?.toString()?.removeSurrounding("[", "]")
                    ?: "Storage is empty"
            )
            input[0] == "addFirst" -> storage.newAction(AddFirstAction(input[1].toInt()))
            input[0] == "addLast" -> storage.newAction(AddLastAction(input[1].toInt()))
            input[0] == "swap" -> storage.newAction(
                SwapAction(
                    input[1].toInt(), input[2].toInt()
                )
            )
            input[0] == "undo" -> {
                try {
                    storage.undoAction()
                } catch (e: IllegalArgumentException) {
                    println(e.message)
                }
            }
            input[0] == "exit" -> isRunning = false
        }
    }

    private fun executeCommand(input: List<String>) {
        when {
            !isValidCommand(input[0]) -> println("Not a valid command.")
            !isArgumentNumberCorrect(input) -> println("Incorrect number of arguments.")
            !isArgumentsIntegers(input) -> println("All arguments must be integers.")
        }

        if (!isValidCommand(input[0]) || !isArgumentNumberCorrect(input) || !isArgumentsIntegers(input)) {
            return
        }

        runCommand(input)
    }

    fun mainLoop() {
        while (isRunning) {
            print("Enter command or 'help' for list of available commands: ")
            val command = readln().trim().split(" ")
            this.executeCommand(command)
        }
    }
}
