package dev.bluemethyst.bots.kubebot.wiki

import dev.bluemethyst.bots.kubebot.KubeBot.CONFIG
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

suspend fun fetchGithub(url: String) {
    val client = HttpClient(CIO)
    val ghLink = "${CONFIG.githubWikiUrl}$url/page.kubedoc"
    val rawLink = convertToRawGithubLink(ghLink)
    val response: HttpResponse = client.get(rawLink)
    if (response.status == HttpStatusCode.OK) {
        println(response.bodyAsText())
    } else {
        println("Failed to fetch response.")
        println(response)
    }
}

internal fun convertToRawGithubLink(normalLink: String): String {
    return normalLink.replace("https://github.com/", "https://raw.githubusercontent.com/")
        .replace("/blob/", "/")
        .replace("/tree/", "/")
}