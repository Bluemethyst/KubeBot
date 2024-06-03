package dev.bluemethyst.bots.kubebot.commands

import dev.bluemethyst.bots.kubebot.KubeBot.CONFIG
import dev.bluemethyst.bots.kubebot.wiki.fetchGithub
import dev.bluemethyst.bots.kubebot.wiki.searchWiki
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.rest.builder.message.actionRow
import dev.kord.rest.builder.message.embed
import dev.kord.x.emoji.DiscordEmoji
import dev.kord.x.emoji.Emojis
import me.jakejmattson.discordkt.arguments.AnyArg
import me.jakejmattson.discordkt.arguments.ChoiceArg
import me.jakejmattson.discordkt.commands.subcommand
import me.jakejmattson.discordkt.dsl.menu

fun wiki() = subcommand("Wiki") {
    sub("Search", "Search the KubeJS Wiki") {
        execute(AnyArg("search", "Search query")) {
            val searchQuery = args.first
            val interactionResponse = interaction?.deferPublicResponse() ?: return@execute
            val response = searchWiki(searchQuery)
            if (response == null) {
                interactionResponse.respond {
                    content = "Failed to fetch response."
                }
            } else {
                println("${CONFIG.githubWikiUrl}${response[0].url}/page.kubedoc")
                interactionResponse.respond {
                    menu {
                        embed {
                            title = "Search Results"
                            description = response.joinToString("\n") {
                                "[${it.name}](${CONFIG.kubejsUrl}${it.url})"
                            }
                        }
                        buttons {
                            for (result in response) {
                                button(result.name, Emojis.`100`) {
                                    fetchGithub(result.url)
                                }
                            }
                        }
                    }
                }
            }
        }
        sub("View", "View a specific page from the KubeJS wiki") {
            execute(
                ChoiceArg(
                    "page",
                    "Page to view",
                    "items",
                    "recipes",
                    "tags",
                    "blocks",
                    "fluids",
                    "interaction",
                    "tooltips"
                )
            ) {
                val interactionResponse = interaction?.deferPublicResponse() ?: return@execute
                fetchGithub("/wiki/tutorials/recipes")
                interactionResponse.respond {
                    content = "complete"
                }
            }
        }
    }
}