package dev.bluemethyst.bots.kubebot.wiki

import com.google.gson.reflect.TypeToken
import dev.bluemethyst.bots.kubebot.KubeBot.CONFIG
import dev.bluemethyst.bots.kubebot.KubeBot.GSON
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

data class SearchRequestBody(
    val json: String,
    val query: String,
    val page: String
)

data class JsonResponse(
    val name: String,
    val url: String,
)

suspend fun searchWiki(query: String): List<JsonResponse>? {
    val client = HttpClient(CIO)
    val requestBody = GSON.toJson(SearchRequestBody("json", query,""))
    val response: HttpResponse = client.post("${CONFIG.kubejsUrl}/wiki/search") {
        contentType(ContentType.Application.Json)
        setBody(requestBody)
    }
    if (response.status == HttpStatusCode.OK) {
        val jsonResponse: List<JsonResponse> = GSON.fromJson(response.bodyAsText(), object : TypeToken<List<JsonResponse>>() {}.type)
        return jsonResponse
    } else {
        println("Failed to fetch response.")
        println(response)
    }
    client.close()
    return null
}