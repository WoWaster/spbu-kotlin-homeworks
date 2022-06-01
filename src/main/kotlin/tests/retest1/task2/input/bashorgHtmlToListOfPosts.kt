package tests.retest1.task2.input

import org.jsoup.Jsoup
import tests.retest1.task2.models.Post

fun bashorgHtmlToListOfPosts(html: String): List<Post> {
    val page = Jsoup.parse(html)
    val postsHtml =
        page.getElementById("quotes") ?: throw IllegalArgumentException("Incorrect page. Can't find quotes div.")

    val posts = mutableListOf<Post>()

    val idRegex = Regex("""#\d*""")

    for (post in postsHtml.children()) {
        if (post.hasClass("q")) {
            val id = idRegex.find(post.children()[0].html())?.groups?.get(0)?.value?.replace("#", "")?.toInt()
                ?: throw IllegalArgumentException("Incorrect page. Can't find post id.")
            val body = post.children()[1].wholeText()
            posts.add(Post(id, body))
        }
    }

    return posts
}
