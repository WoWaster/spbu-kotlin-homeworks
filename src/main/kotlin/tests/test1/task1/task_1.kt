package tests.test1.task1

fun main() {
    val queue = PriorityQueue<Int, String>()

    val cli = PriorityQueueCLI(queue)

    cli.mainLoop()
}

class PriorityQueueCLI(private val queue: PriorityQueue<Int, String>) {
    private val validCommands = listOf("help", "print", "enqueue", "peek", "remove", "roll", "exit")
    private val amountOfArguments = mapOf(
        "help" to 0, "print" to 0, "enqueue" to 2, "peek" to 0, "remove" to 0, "roll" to 0, "exit" to 0
    )
    private val help = """
        Available commands:
        * print - prints current numbers in queue
        * enqueue priority element - add String element with integer priority
        * peek - get element with highest priority
        * remove - remove element with highest priority
        * roll - get and remove element with highest priority
        * exit 
    """.trimIndent()

    private var isRunning = true

    private fun isValidCommand(command: String) = command in validCommands
    private fun isArgumentNumberCorrect(input: List<String>) = input.size == amountOfArguments.getValue(input[0]) + 1

    private fun runCommand(input: List<String>) {
        when {
            input[0] == "help" -> println(help)
            input[0] == "print" -> println(
                queue.list.takeIf { it.isNotEmpty() }?.toString()?.removeSurrounding("[", "]")
                    ?: "queue is empty"
            )
            input[0] == "enqueue" -> queue.enqueue(input[1].toInt(), input[2])
            input[0] == "peek" -> println(queue.peek())
            input[0] == "remove" -> queue.remove()
            input[0] == "roll" -> println(queue.roll())
            input[0] == "exit" -> isRunning = false
        }
    }

    private fun executeCommand(input: List<String>) {
        when {
            !isValidCommand(input[0]) -> println("Not a valid command.")
            !isArgumentNumberCorrect(input) -> println("Incorrect number of arguments.")
        }

        if (!isValidCommand(input[0]) || !isArgumentNumberCorrect(input)) {
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
