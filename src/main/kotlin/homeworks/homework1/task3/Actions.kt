package homeworks.homework1.task3

interface Action {
    fun doAction(list: ArrayDeque<Int>)
    fun undoAction(list: ArrayDeque<Int>)
}

class AddFirstAction(private val number: Int) : Action {
    override fun doAction(list: ArrayDeque<Int>) {
        list.addFirst(number)
    }

    override fun undoAction(list: ArrayDeque<Int>) {
        list.removeFirst()
    }
}

class AddLastAction(private val number: Int) : Action {
    override fun doAction(list: ArrayDeque<Int>) {
        list.addLast(number)
    }

    override fun undoAction(list: ArrayDeque<Int>) {
        list.removeLast()
    }
}

class SwapAction(private val index1: Int, private val index2: Int) : Action {
    private fun ArrayDeque<Int>.swap(index1: Int, index2: Int) {
        this[index1] = this[index2].also { this[index2] = this[index1] }
    }

    override fun doAction(list: ArrayDeque<Int>) {
        require(list.lastIndex >= index1 && list.lastIndex >= index2) { "Index(-es) out of list bounds" }
        list.swap(index1, index2)
    }

    override fun undoAction(list: ArrayDeque<Int>) {
        list.swap(index1, index2)
    }
}
