package tests.retest1.task2

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tests.retest1.task2.input.bashorgHtmlToListOfPosts
import tests.retest1.task2.input.getHtml
import tests.retest1.task2.models.RANDOM_URL
import java.lang.IllegalArgumentException

internal class Task2 {
    @Test
    fun `correct bashorg page`() {
        val html = runBlocking { return@runBlocking getHtml(RANDOM_URL) }
        assertTrue(html.isNotEmpty())
        val posts = bashorgHtmlToListOfPosts(html)
        assertTrue(posts.isNotEmpty())
    }

    @Test
    fun `not bashorg page`() {
        val html = runBlocking { return@runBlocking getHtml("https://google.com") }
        assertTrue(html.isNotEmpty())
        val exception = assertThrows<IllegalArgumentException> { bashorgHtmlToListOfPosts(html) }
        assertEquals("Incorrect page. Can't find quotes div.", exception.message)
    }

    @Test
    fun `incorrect bashorg page`() {
        var html = runBlocking { return@runBlocking getHtml(RANDOM_URL) }
        assertTrue(html.isNotEmpty())
        html = html.replace("#", "")
        val exception = assertThrows<IllegalArgumentException> { bashorgHtmlToListOfPosts(html) }
        assertEquals("Incorrect page. Can't find post id.", exception.message)
    }
}
