import com.blamejared.jeitweaker.gradle.Constants
import com.blamejared.modtemplate.Utils
import com.diluv.schoomp.message.embed.Embed
import com.diluv.schoomp.message.Message
import com.diluv.schoomp.Webhook

import java.io.IOException
import java.util.StringJoiner

plugins {
    base
    id("com.blamejared.modtemplate")
}

tasks.create("postDiscord") {

    doLast {
        try {
            // Create a new webhook instance for Discord
            val webhook = Webhook(
                    System.getenv("discordCFWebhook"),
                    "${Constants.MOD_NAME} CurseForge Gradle Upload"
            )

            // Craft a message to send to Discord using the webhook.
            val message = Message()
            message.username = Constants.MOD_NAME
            message.avatarUrl = Constants.MOD_AVATAR
            message.content = "${Constants.MOD_NAME} $version for Minecraft ${Constants.MINECRAFT_VERSION} has been published!"

            val embed = Embed()
            val downloadSources = StringJoiner("\n")

            if (project(":fabric").ext.has("curse_file_url")) {

                downloadSources.add("<:fabric:932163720568782878> [Fabric](${project(":fabric").ext.get("curse_file_url")})")
            }

            if (project(":forge").ext.has("curse_file_url")) {

                downloadSources.add("<:forge:932163698003443804> [Forge](${project(":forge").ext.get("curse_file_url")})")
            }

            downloadSources.add(
                    "<:maven:932165250738970634> `\"${project(":core").group}:${project(":core").base.archivesName.get()}:${
                        project(":core").version
                    }\"`"
            )

            downloadSources.add(
                    "<:maven:932165250738970634> `\"${project(":fabric").group}:${project(":fabric").base.archivesName.get()}:${
                        project(":fabric").version
                    }\"`"
            )
            downloadSources.add(
                    "<:maven:932165250738970634> `\"${project(":forge").group}:${project(":forge").base.archivesName.get()}:${
                        project(":forge").version
                    }\"`"
            )

            downloadSources.add(
                    "<:maven:932165250738970634> `\"${project(":vanilla").group}:${project(":vanilla").base.archivesName.get()}:${
                        project(":vanilla").version
                    }\"`"
            )

            // Add Curseforge DL link if available.
            val downloadString = downloadSources.toString()

            if (downloadString.isNotEmpty()) {

                embed.addField("Download", downloadString, false)
            }

            // Just use the Forge changelog for now, the files are the same anyway.
            embed.addField("Changelog", Utils.getCIChangelog(project, Constants.GIT_REPO).take(1000), false)

            embed.color = 0xF16436
            message.addEmbed(embed)

            webhook.sendMessage(message)
        } catch (e: IOException) {

            project.logger.error("Failed to push CF Discord webhook.")
        }
    }

}

val apDir = "CraftTweaker-Annotation-Processors";

tasks.create("checkoutAP") {
    doFirst {
        if (!rootProject.file(apDir).exists() || (rootProject.file(apDir).listFiles() ?: arrayOf()).isEmpty()) {
            exec {
                commandLine("git", "clone", "https://github.com/CraftTweaker/CraftTweaker-Annotation-Processors.git")
            }
        } else {
            throw GradleException("$apDir folder already exists and is not empty!")
        }
    }
}

tasks.create<Delete>("clearAP") {
    doFirst {
        delete(rootProject.file(apDir))
    }
}