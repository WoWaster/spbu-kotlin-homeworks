package homeworks.homework1.task3

interface Action {
    fun doAction(list: MutableList<Int>)
    fun undoAction(list: MutableList<Int>)
}

class AddFirstAction(private val number: Int) : Action {
    override fun doAction(list: MutableList<Int>) {
        list.add(0, number)
    }

    override fun undoAction(list: MutableList<Int>) {
        list.removeFirst()
    }
}

class AddLastAction(private val number: Int) : Action {
    override fun doAction(list: MutableList<Int>) {
        list.add(number)
    }

    override fun undoAction(list: MutableList<Int>) {
        list.removeLast()
    }
}

class SwapAction(private val indexFrom: Int, private val indexTo: Int) : Action {
    override fun doAction(list: MutableList<Int>) {
        require(indexFrom in 0..list.lastIndex && indexTo in 0..list.lastIndex) { "Index(-es) out of list bounds" }
        list.add(indexTo, list.removeAt(indexFrom))
    }

    override fun undoAction(list: MutableList<Int>) {
        list.add(indexFrom, list.removeAt(indexTo))
    }
}
