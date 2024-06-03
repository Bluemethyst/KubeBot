package dev.bluemethyst.bots.kubebot.commands

import me.jakejmattson.discordkt.commands.commands
import me.jakejmattson.discordkt.util.addInlineField
import oshi.SystemInfo
import oshi.hardware.CentralProcessor
import oshi.hardware.GlobalMemory
import java.lang.management.ManagementFactory

fun misc() = commands("Misc") {
    text("help"){ execute { } } // removes help command

    slash("ping", "Check the bot's latency.") {
        execute {
            val ping = discord.kord.gateway.averagePing.toString()
            respondPublic(ping)
        }
    }

    slash("info", "Get information about the bot.") {
        execute {
            val systemInfo = SystemInfo()
            val processor: CentralProcessor.ProcessorIdentifier = systemInfo.hardware.processor.processorIdentifier
            val memory: GlobalMemory = systemInfo.hardware.memory
            val startupTime = ManagementFactory.getRuntimeMXBean().startTime
            val usedMemory = memory.total - memory.available
            val totalMemory = memory.total
            val memoryUsagePercentage = (usedMemory.toDouble() / totalMemory.toDouble()) * 100

            respondPublic {
                title = "Information"
                addInlineField("Bot", "Written in Kotlin using the DiscordKt wrapper for the Kord. [Source](https://github.com/Bluemethyst/KubeBot)")
                addInlineField("CPU", processor.name)
                addInlineField("Memory", "${usedMemory / (1024 * 1024)}MB / ${totalMemory / (1024 * 1024)}MB (${"%.2f".format(memoryUsagePercentage)}%)")
                addInlineField("Kotlin", "${KotlinVersion.CURRENT}")
                addInlineField("Startup Time", "<t:${startupTime / 1000}:R>")
            }
        }
    }
}