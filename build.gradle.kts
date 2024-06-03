val discordKtVersion: String by project
val dotenvKotlinVersion: String by project
val ktorVersion: String by project
val oshiVersion: String by project
val gsonVersion: String by project
val group: String by project
val version: String by project
val description: String by project


plugins {
    kotlin("jvm") version "1.9.23"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("me.jakejmattson:DiscordKt:$discordKtVersion")
    implementation("io.github.cdimascio:dotenv-kotlin:$dotenvKotlinVersion")
    implementation("com.github.oshi:oshi-core:$oshiVersion")
    implementation("com.google.code.gson:gson:$gsonVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")

}

tasks {
    kotlin {
        jvmToolchain(21)
    }
    compileKotlin {
        doLast("writeProperties") {}
    }
    register<WriteProperties>("writeProperties") {
        property("name", project.name)
        property("description", project.description.toString())
        property("version", version)
        property("url", "https://github.com/Bluemethyst/KubeBot")
        setOutputFile("src/main/resources/bot.properties")
    }
}