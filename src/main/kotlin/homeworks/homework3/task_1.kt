@file:Suppress("MagicNumber")
package homeworks.homework3

fun main() {
    val avl = avlTreeOf(
        "star_fruit" to 129,
        "dragon_fruit" to 297,
        "pear" to 280,
        "jujube" to 219,
        "guava" to 108,
        "peach" to 107,
        "papaya" to 169,
        "huckleberry" to 289,
        "boysenberry" to 180,
        "tangerine" to 176,
        "passion_fruit" to 138,
        "apricot" to 256,
        "tomato" to 127,
        "lime" to 113,
        "kiwi" to 285,
        "date" to 106,
        "blackberry" to 257,
        "lemon" to 252,
        "nectarine" to 187,
        "apple" to 197,
        "cherry" to 185,
        "pomegranate" to 277,
        "banana" to 142,
        "orange" to 207,
        "kumquat" to 233,
        "fig" to 162,
        "plum" to 139,
        "avocado" to 262,
        "coconut" to 223,
        "mango" to 166,
    )

    println("Initial tree:")
    avl.prettyPrint()

    avl["lime"] = 58
    avl["mango"] = 247
    avl["apple"] = 100
    avl["tomato"] = 177

    avl.remove("boysenberry")
    avl.remove("huckleberry")
    avl.remove("tomato")
    avl.remove("tangerine")

    println("Tree after some value updates and removals:")
    avl.prettyPrint()
}
