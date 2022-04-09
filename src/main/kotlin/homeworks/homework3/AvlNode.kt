package homeworks.homework3

@Suppress("TooManyFunctions")
class AvlNode<K : Comparable<K>, V> internal constructor(key: K, value: V) : MutableMap.MutableEntry<K, V> {
    internal constructor(key: K, value: V, parentNode: AvlNode<K, V>?) : this(key, value) {
        parent = parentNode
    }

    override var key: K = key
        internal set
    override var value: V = value
        internal set

    private var height = 0
    internal var parent: AvlNode<K, V>? = null
    internal var leftChild: AvlNode<K, V>? = null
    internal var rightChild: AvlNode<K, V>? = null

    private fun updateHeight() {
        val heightLeft = leftChild?.height ?: -1
        val heightRight = rightChild?.height ?: -1
        height = maxOf(heightLeft, heightRight) + 1
    }

    private fun getBalanceFactor(): Int {
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

    @Suppress("MagicNumber")
    internal fun balance(): AvlNode<K, V> {
        updateHeight()
        return when (getBalanceFactor()) {
            2 -> {
                if ((rightChild?.getBalanceFactor() ?: 0) == -1) {
                    rightChild = rightChild?.rotateRight()
                }
                rotateLeft()
            }
            -2 -> {
                if ((leftChild?.getBalanceFactor() ?: 0) == 1) {
                    leftChild = leftChild?.rotateLeft()
                }
                rotateRight()
            }
            else -> this
        }
    }

    internal fun minimum(): AvlNode<K, V> {
        return leftChild?.minimum() ?: this
    }

    internal fun removeMinimum(): AvlNode<K, V>? {
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

    @Suppress("ReturnCount")
    internal fun get(key: K): V? {
        when (sign(key.compareTo(this.key))) {
            0 -> return value
            -1 -> if (leftChild != null) return leftChild?.get(key)
            1 -> if (rightChild != null) return rightChild?.get(key)
        }
        return null
    }

    internal fun prettyPrint(level: Int = 0) {
        /* These characters are written as unicode hexadecimals because they
         can easily be confused to their analogs which will not produce ligatures in terminals */
        val append = "\u251C" + "\u2500".repeat(level)

        println("$append$key: $value")
        if (leftChild != null) leftChild?.prettyPrint(level + 1)
        if (rightChild != null) rightChild?.prettyPrint(level + 1)
    }
}
