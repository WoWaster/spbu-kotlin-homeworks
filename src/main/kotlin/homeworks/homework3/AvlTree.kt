package homeworks.homework3

@Suppress("TooManyFunctions")
class AvlTree<K : Comparable<K>, V> : MutableMap<K, V> {

    private var root: AvlNode<K, V>? = null

    override var size: Int = 0
        private set

    override fun containsKey(key: K): Boolean = get(key) != null

    override fun containsValue(value: V): Boolean {
        iterator().forEach { if (it.value == value) return true }
        return false
    }

    override fun get(key: K): V? = root?.get(key)

    override fun isEmpty(): Boolean = size == 0

    override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
        get() = iterator().asSequence().toMutableSet()
    override val keys: MutableSet<K>
        get() = iterator().asSequence().map { it.key }.toMutableSet()
    override val values: MutableCollection<V>
        get() = iterator().asSequence().map { it.value }.toMutableList()

    override fun clear() {
        root = null
        size = 0
    }

    @Suppress("ReturnCount")
    private fun putNode(node: AvlNode<K, V>?, key: K, value: V, parentNode: AvlNode<K, V>?): AvlNode<K, V> {
        if (node == null) {
            size++
            return AvlNode(key, value, parentNode)
        }

        when (sign(key.compareTo(node.key))) {
            -1 -> node.leftChild = putNode(node.leftChild, key, value, node)
            1 -> node.rightChild = putNode(node.rightChild, key, value, node)
            0 -> return node.also { it.value = value }
        }

        return node.balance()
    }

    override fun put(key: K, value: V): V? {
        val previousValue = get(key)
        root = putNode(root, key, value, root)
        return previousValue
    }

    override fun putAll(from: Map<out K, V>) = from.forEach { put(it.key, it.value) }

    @Suppress("ReturnCount")
    private fun removeNode(node: AvlNode<K, V>?, key: K, parentNode: AvlNode<K, V>?): AvlNode<K, V>? {
        if (node == null) {
            return null
        }

        when (sign(key.compareTo(node.key))) {
            -1 -> node.leftChild = removeNode(node.leftChild, key, node)
            1 -> node.rightChild = removeNode(node.rightChild, key, node)
            0 -> {
                val leftChild = node.leftChild
                val rightChild = node.rightChild
                if (leftChild == null && rightChild == null) {
                    size--
                    return null
                }
                if (rightChild == null) {
                    leftChild?.parent = parentNode
                    size--
                    return leftChild
                }
                val minimumInRightTree = rightChild.minimum()
                minimumInRightTree.rightChild = rightChild.removeMinimum()
                minimumInRightTree.rightChild?.parent = minimumInRightTree
                minimumInRightTree.leftChild = leftChild
                leftChild?.parent = minimumInRightTree
                minimumInRightTree.parent = parentNode
                size--
                return minimumInRightTree.balance()
            }
        }

        return node.balance()
    }

    override fun remove(key: K): V? {
        val value = get(key)
        root = removeNode(root, key, root?.parent)
        return value
    }

    /*
    In-order iterator for AVL tree. It's private because kotlin stdlib iterator for map is based
    on entries field and having two public iterators confuses compiler and IDE.
     */
    private fun iterator(): Iterator<AvlNode<K, V>> =
        iterator {
            val stack = ArrayDeque<AvlNode<K, V>>()
            var current = root
            while (stack.isNotEmpty() || current != null) {
                if (current != null) {
                    stack.addFirst(current)
                    current = current.leftChild
                } else {
                    current = stack.removeFirst()
                    yield(current)
                    current = current.rightChild
                }
            }
        }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("{")
        var i = 0
        iterator().forEach {
            if (i > 0) sb.append(", ")
            sb.append(it.toString())
            i++
        }
        sb.append("}")
        return sb.toString()
    }

    fun prettyPrint() {
        if (root == null) return
        root?.prettyPrint()
    }
}

fun <K : Comparable<K>, V> avlTreeOf(vararg pairs: Pair<K, V>): AvlTree<K, V> =
    AvlTree<K, V>().apply { this.putAll(pairs) }
