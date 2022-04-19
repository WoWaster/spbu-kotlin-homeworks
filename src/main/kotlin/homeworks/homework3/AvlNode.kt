package homeworks.homework3

internal class AvlNode<K : Comparable<K>, V>(
    override val key: K,
    override var value: V,
    var parent: AvlNode<K, V>?
) : MutableMap.MutableEntry<K, V> {

    private var height = 0
    var leftChild: AvlNode<K, V>? = null
    var rightChild: AvlNode<K, V>? = null

    private fun updateHeight() {
        val heightLeft = leftChild?.height ?: -1
        val heightRight = rightChild?.height ?: -1
        height = maxOf(heightLeft, heightRight) + 1
    }

    private val balanceFactor: Int
        get() {
            val heightLeft = leftChild?.height ?: -1
            val heightRight = rightChild?.height ?: -1
            return heightRight - heightLeft
        }

    private fun rotateRight(): AvlNode<K, V> {
        val child = leftChild ?: throw IllegalStateException("Impossible right turn.")
        leftChild = child.rightChild
        child.rightChild = this
        child.parent = parent
        parent = child
        leftChild?.parent = this
        updateHeight()
        child.updateHeight()
        return child
    }

    private fun rotateLeft(): AvlNode<K, V> {
        val child = rightChild ?: throw IllegalStateException("Impossible left turn.")
        rightChild = child.leftChild
        child.leftChild = this
        child.parent = parent
        parent = child
        rightChild?.parent = this
        updateHeight()
        child.updateHeight()
        return child
    }

    companion object {
        const val LEFT_OVERLOAD = -2
        const val LEFT_HEAVY = -1
        const val RIGHT_HEAVY = 1
        const val RIGHT_OVERLOAD = 2
    }

    fun balance(): AvlNode<K, V> {
        updateHeight()
        return when (balanceFactor) {
            RIGHT_OVERLOAD -> {
                if ((rightChild?.balanceFactor ?: 0) == LEFT_HEAVY) {
                    rightChild = rightChild?.rotateRight()
                }
                rotateLeft()
            }
            LEFT_OVERLOAD -> {
                if ((leftChild?.balanceFactor ?: 0) == RIGHT_HEAVY) {
                    leftChild = leftChild?.rotateLeft()
                }
                rotateRight()
            }
            else -> this
        }
    }

    fun minimum(): AvlNode<K, V> {
        return leftChild?.minimum() ?: this
    }

    fun removeMinimum(): AvlNode<K, V>? {
        if (leftChild == null) {
            return rightChild
        }
        leftChild = leftChild?.removeMinimum()
        return this.balance()
    }

    override fun setValue(newValue: V): V {
        val oldValue = value
        value = newValue
        return oldValue
    }

    override fun toString(): String = "$key=$value"

    fun get(key: K): V? = when {
        key < this.key -> leftChild?.get(key)
        key > this.key -> rightChild?.get(key)
        key == this.key -> value
        else -> null
    }

    fun prettyPrint(level: Int = 0): String {
        /* These characters are written as unicode hexadecimals because they
         can easily be confused to their analogs which will not produce ligatures in terminals */
        val indentation = "\u251C" + "\u2500".repeat(level)

        val treeView = StringBuilder()
        treeView.append("$indentation$key: $value\n")

        leftChild?.prettyPrint(level + 1).let { if (it != null) treeView.append(it) }
        rightChild?.prettyPrint(level + 1).let { if (it != null) treeView.append(it) }

        return treeView.toString()
    }
}
