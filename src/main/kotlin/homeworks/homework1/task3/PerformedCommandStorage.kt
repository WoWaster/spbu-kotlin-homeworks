package homeworks.homework1.task3

class PerformedCommandStorage {
    private val _numbers = mutableListOf<Int>()
    val numbers: List<Int>
        get() = _numbers

    private val actions = mutableListOf<Action>()

    fun newAction(action: Action) {
        try {
            action.doAction(_numbers)
            actions.add(action)
        } catch (e: IllegalArgumentException) {
            println(e.message)
        }
    }

    fun undoAction() {
        require(actions.isNotEmpty()) { "No actions to undo." }
        val action = actions.removeLast()
        action.undoAction(_numbers)
    }
}
