package dev.bluemethyst.bots.kubebot

import com.google.gson.Gson
import dev.bluemethyst.bots.kubebot.KubeBot.CONFIG
import dev.bluemethyst.bots.kubebot.KubeBot.GSON
import dev.bluemethyst.bots.kubebot.config.getConfig
import io.github.cdimascio.dotenv.dotenv
import me.jakejmattson.discordkt.dsl.bot

object KubeBot {
    val GSON = Gson()
    val CONFIG = getConfig()
}

fun main() {
    val dotenv = dotenv()
    bot(dotenv["TOKEN"]) {
        mentionEmbed(null) // removes info command
        onStart {
            println("Logged in as ${kord.getSelf().tag}")
            GSON
            CONFIG
        }
        presence {
            watching("you struggle to code")
        }
    }
}