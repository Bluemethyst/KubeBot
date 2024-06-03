package dev.bluemethyst.bots.kubebot.config

import dev.bluemethyst.bots.kubebot.KubeBot.GSON
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.Path

data class Config(
    val kubejsUrl: String,
    val githubWikiUrl: String
)

fun getConfig(): Config {
    val configPath = Path("config.json")
    if (Files.exists(configPath)) {
        val configString = String(Files.readAllBytes(configPath))
        return try {
            println("Config found, loading...")
            GSON.fromJson(configString, Config::class.java)
        } catch (e: Exception) {
            println("Config is invalid, resetting to default...")
            resetToDefault(configPath)
            GSON.fromJson(configString, Config::class.java)
        }
    } else {
        println("Config not found, creating...")
        Files.createFile(configPath)
        Files.write(configPath, GSON.toJson(Config("https://kubejs.com", "https://github.com/KubeJS-Mods/wiki/tree/main")).toByteArray(StandardCharsets.UTF_8))
        val configString = String(Files.readAllBytes(configPath))
        return GSON.fromJson(configString, Config::class.java)
    }
}

private fun resetToDefault(configPath: Path) {
    val defaultConfig = Config("https://kubejs.com", "https://github.com/KubeJS-Mods/wiki/tree/main")
    val defaultConfigJson = GSON.toJson(defaultConfig)
    Files.write(configPath, defaultConfigJson.toByteArray(StandardCharsets.UTF_8))
}
