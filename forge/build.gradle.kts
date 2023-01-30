import com.blamejared.jeitweaker.gradle.Constants
import com.blamejared.modtemplate.Utils

plugins {
    id("com.blamejared.jeitweaker.java-conventions")
    id("net.minecraftforge.gradle") version "5.1.+"
    id("org.parchmentmc.librarian.forgegradle") version "1.+"
    id("org.spongepowered.mixin") version "0.7-SNAPSHOT"
}

evaluationDependsOn(":common")

base.archivesName.set("${Constants.MOD_NAME}-forge-${Constants.MINECRAFT_VERSION}")

minecraft {
    mappings("parchment", "${Constants.PARCHMENT_VERSION}-${Constants.MINECRAFT_VERSION}")

    runs {
        create("client") {
            workingDirectory(project.file("run"))
            ideaModule("${rootProject.name}.${project.name}.main")
            sequenceOf("forge", "common").forEach { arg("-mixin.config=${Constants.MOD_ID}.$it.mixins.json") }
            mods {
                create(Constants.MOD_ID) {
                    source(sourceSets.api.get())
                    source(sourceSets.main.get())
                    source(project(":common").sourceSets.api.get())
                    source(project(":common").sourceSets.main.get())
                }
            }
        }
        create("server") {
            workingDirectory(project.file("run_server"))
            ideaModule("${rootProject.name}.${project.name}.main")
            sequenceOf("forge", "common").forEach { arg("-mixin.config=${Constants.MOD_ID}.$it.mixins.json") }
            arg("nogui")
            mods {
                create(Constants.MOD_ID) {
                    source(sourceSets.api.get())
                    source(sourceSets.main.get())
                    source(project(":common").sourceSets.api.get())
                    source(project(":common").sourceSets.main.get())
                }
            }
        }
    }
}

mixin {
    add(sourceSets.main.get(), "${Constants.MOD_ID}.refmap.json")

    sequenceOf("forge", "common").forEach { config("${Constants.MOD_ID}.$it.mixins.json") }
}

modTemplate {
    modLoader("Forge")
}

dependencies {
    val mcDep = minecraft(group = "net.minecraftforge", name = "forge", version = "${Constants.MINECRAFT_VERSION}-${Constants.FORGE_VERSION}")

    annotationProcessor(group = "org.spongepowered", name = "mixin", version = "0.8.5-SNAPSHOT", classifier = "processor")
    annotationProcessor(group = "com.blamejared.crafttweaker", name = "Crafttweaker_Annotation_Processors-${Constants.MINECRAFT_VERSION}", version = Constants.CRAFTTWEAKER_VERSION)
    annotationProcessor(group = "com.google.code.gson", name = "gson", version = "2.8.6")
    annotationProcessor(group = "org.reflections", name = "reflections", version = "0.9.10")
    annotationProcessor(mcDep)
    annotationProcessor(group = "com.blamejared.crafttweaker", name = "CraftTweaker-forge-${Constants.MINECRAFT_VERSION}", version = Constants.CRAFTTWEAKER_VERSION)

    apiImplementation(project(":common", "apiConfiguration"))
    apiImplementation(fg.deobf("com.blamejared.crafttweaker:CraftTweaker-forge-${Constants.MINECRAFT_VERSION}:${Constants.CRAFTTWEAKER_VERSION}"))
    apiImplementation(fg.deobf("mezz.jei:jei-${Constants.MINECRAFT_VERSION}-forge-api:${Constants.JEI_VERSION}"))

    implementation(project(":common", "apiConfiguration"))
    implementation(project(":common"))
    implementation(fg.deobf("com.blamejared.crafttweaker:CraftTweaker-forge-${Constants.MINECRAFT_VERSION}:${Constants.CRAFTTWEAKER_VERSION}"))
    implementation(fg.deobf("mezz.jei:jei-${Constants.MINECRAFT_VERSION}-forge-api:${Constants.JEI_VERSION}"))

    runtimeOnly(fg.deobf("mezz.jei:jei-${Constants.MINECRAFT_VERSION}-forge:${Constants.JEI_VERSION}"))
}

tasks {
    compileJava {
        project(":common").sourceSets.let { sequenceOf(it.api.get(), it.main.get()) }
                .plusElement(sourceSets.api.get())
                .forEach { source(it.allSource) }
    }
    publishToCurseForge {
        with(upload(Constants.MOD_CURSE_ID, project.buildDir.resolve("libs/${base.archivesName.get()}-$version.jar"))) {
            changelogType = net.darkhax.curseforgegradle.Constants.CHANGELOG_MARKDOWN
            changelog = Utils.getFullChangelog(project)
            releaseType = when (Constants.FORGE_RELEASE) {
                Constants.Release.RELEASE -> net.darkhax.curseforgegradle.Constants.RELEASE_TYPE_RELEASE
                Constants.Release.BETA -> net.darkhax.curseforgegradle.Constants.RELEASE_TYPE_BETA
                Constants.Release.ALPHA -> net.darkhax.curseforgegradle.Constants.RELEASE_TYPE_ALPHA
            }
            addJavaVersion("Java ${Constants.JAVA_VERSION}")
            addGameVersion("Forge")
            addGameVersion(Constants.MINECRAFT_VERSION)
            addRequirement("crafttweaker")
            addRequirement("jei")

            doLast {
                project.ext.set("curse_file_url", "${Constants.MOD_CURSE}/files/${curseFileId}")
            }
        }
    }
    jar {
        project(":common").sourceSets.let { sequenceOf(it.api.get(), it.main.get()) }
                .plusElement(sourceSets.api.get())
                .forEach { from(it.resources) }
        duplicatesStrategy = DuplicatesStrategy.FAIL
    }
}

afterEvaluate {
    tasks.jar.get().finalizedBy(tasks.findByName("reobfJar"))
}