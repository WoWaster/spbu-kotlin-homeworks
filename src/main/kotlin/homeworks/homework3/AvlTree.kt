package homeworks.homework3

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

    override fun put(key: K, value: V): V? {
        val previousValue = get(key)
        root = putNode(root, key, value, root)
        if (previousValue == null) {
            size++
        }
        return previousValue
    }

    override fun putAll(from: Map<out K, V>) = from.forEach { put(it.key, it.value) }

    override fun remove(key: K): V? {
        val value = get(key)
        if (value != null) {
            root = removeNode(root, key, root?.parent)
            size--
        }
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

    override fun toString(): String = root?.prettyPrint() ?: ""

    companion object {
        private fun <K : Comparable<K>, V> putNode(
            node: AvlNode<K, V>?,
            key: K,
            value: V,
            parentNode: AvlNode<K, V>?
        ): AvlNode<K, V> {
            if (node == null) {
                return AvlNode(key, value, parentNode)
            }

            when {
                key < node.key -> node.leftChild = putNode(node.leftChild, key, value, node)
                key > node.key -> node.rightChild = putNode(node.rightChild, key, value, node)
                key == node.key -> node.value = value
            }

            return node.balance()
        }

        private fun <K : Comparable<K>, V> removeMatchingNode(
            node: AvlNode<K, V>,
            parentNode: AvlNode<K, V>?
        ): AvlNode<K, V>? {
            val leftChild = node.leftChild
            val rightChild = node.rightChild
            if (rightChild == null) {
                leftChild?.parent = parentNode
                return leftChild
            }
            val minimumInRightTree = rightChild.minimum()
            minimumInRightTree.rightChild = rightChild.removeMinimum()
            minimumInRightTree.rightChild?.parent = minimumInRightTree
            minimumInRightTree.leftChild = leftChild
            leftChild?.parent = minimumInRightTree
            minimumInRightTree.parent = parentNode
            return minimumInRightTree.balance()
        }

        private fun <K : Comparable<K>, V> removeNode(
            node: AvlNode<K, V>?,
            key: K,
            parentNode: AvlNode<K, V>?
        ): AvlNode<K, V>? {
            if (node == null) {
                return null
            }

            return when {
                key < node.key -> {
                    node.leftChild = removeNode(node.leftChild, key, node)
                    node.balance()
                }
                key > node.key -> {
                    node.rightChild = removeNode(node.rightChild, key, node)
                    node.balance()
                }
                key == node.key -> removeMatchingNode(node, parentNode)
                else -> null
            }
        }
    }
}

fun <K : Comparable<K>, V> avlTreeOf(vararg pairs: Pair<K, V>): AvlTree<K, V> =
    AvlTree<K, V>().apply { this.putAll(pairs) }
