package homeworks.homework1.task3

class PerformedCommandStorage {
    private val numbers = mutableListOf<Int>()
    private val actions = mutableListOf<Action>()
    fun newAction(action: Action) {
        try {
            action.doAction(numbers)
            actions.add(action)
        } catch (e: IllegalArgumentException) {
            println(e.message)
        }
    }

    fun undoAction() {
        require(this.hasActions()) { "No actions to undo." }
        val action = actions.removeLast()
        action.undoAction(numbers)
    }

    fun getNumbers(): List<Int> {
        return numbers.toList()
    }

    fun hasActions(): Boolean {
        return actions.isNotEmpty()
    }
}
