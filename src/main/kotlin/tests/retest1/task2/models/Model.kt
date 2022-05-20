package tests.retest1.task2.models

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import tests.retest1.task2.input.bashorgHtmlToListOfPosts
import tests.retest1.task2.input.getHtml

const val BEST_URL = "http://bashorg.org/byrating"
const val LATEST_URL = "http://bashorg.org/"
const val RANDOM_URL = "http://bashorg.org/casual"

class Model {
    private val _posts: MutableList<Post> = mutableStateListOf()
    val posts = mutableStateOf(_posts)

    suspend fun getBest() {
        _posts.clear()

        val page = getHtml(BEST_URL)
        _posts.addAll(bashorgHtmlToListOfPosts(page))
    }

    suspend fun getLatest() {
        _posts.clear()

        val page = getHtml(LATEST_URL)
        _posts.addAll(bashorgHtmlToListOfPosts(page))
    }

    suspend fun getRandom() {
        _posts.clear()

        val page = getHtml(RANDOM_URL)
        _posts.addAll(bashorgHtmlToListOfPosts(page))
    }
}
