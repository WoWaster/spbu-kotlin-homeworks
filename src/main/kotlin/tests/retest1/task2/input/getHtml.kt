package tests.retest1.task2.input

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

suspend fun getHtml(url: String): String {
    val client = HttpClient(CIO)
    val response = client.get(url)
    return response.bodyAsText()
}
