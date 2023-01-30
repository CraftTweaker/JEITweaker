import com.blamejared.jeitweaker.gradle.Constants
import com.blamejared.modtemplate.Utils

plugins {
    id("com.blamejared.jeitweaker.java-conventions")
    id("fabric-loom") version "0.12-SNAPSHOT"
}

evaluationDependsOn(":common")

base.archivesName.set("${Constants.MOD_NAME}-fabric-${Constants.MINECRAFT_VERSION}")

loom {
    mixin {
        defaultRefmapName.convention("${Constants.MOD_ID}.refmap.json")
    }
    runs {
        named("client") {
            client()
            ideConfigGenerated(true)
            runDir("run")
            programArg("--username=Dev")
        }
        named("server") {
            server()
            ideConfigGenerated(true)
            runDir("run_server")
            programArg("nogui")
        }
    }
}

modTemplate {
    modLoader("Fabric")
}

dependencies {
    val mcDep = minecraft(group = "com.mojang", name = "minecraft", version = Constants.MINECRAFT_VERSION)
    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-${Constants.MINECRAFT_VERSION}:${Constants.PARCHMENT_VERSION}@zip")
    })

    annotationProcessor(group = "com.blamejared.crafttweaker", name = "Crafttweaker_Annotation_Processors-${Constants.MINECRAFT_VERSION}", version = Constants.CRAFTTWEAKER_VERSION)
    annotationProcessor(group = "com.google.code.gson", name = "gson", version = "2.8.6")
    annotationProcessor(group = "org.reflections", name = "reflections", version = "0.9.10")
    annotationProcessor(mcDep)
    annotationProcessor(group = "com.blamejared.crafttweaker", name = "CraftTweaker-fabric-${Constants.MINECRAFT_VERSION}", version = Constants.CRAFTTWEAKER_VERSION)

    apiImplementation(project(":common", "apiConfiguration"))
    // CT and JEI are implicitly added because Loom does not support per-source-set deobfuscated dependencies

    implementation(project(":common", "apiConfiguration"))
    implementation(project(":common"))
    modImplementation(group = "com.blamejared.crafttweaker", name = "CraftTweaker-fabric-${Constants.MINECRAFT_VERSION}", version = Constants.CRAFTTWEAKER_VERSION) {
        exclude(module = "Crafttweaker_Annotations")
    }
    modImplementation(group = "net.fabricmc", name = "fabric-loader", version = Constants.FABRIC_VERSION)
    modImplementation(group = "net.fabricmc.fabric-api", name = "fabric-api", version = Constants.FABRIC_API_VERSION)
    modImplementation(group = "mezz.jei", name = "jei-${Constants.MINECRAFT_VERSION}-fabric-api", version = Constants.JEI_VERSION)
    modRuntimeOnly(group = "mezz.jei", name = "jei-${Constants.MINECRAFT_VERSION}-fabric", version = Constants.JEI_VERSION)
}

tasks {
    compileJava {
        project(":common").sourceSets.let { sequenceOf(it.api.get(), it.main.get()) }
                .plusElement(sourceSets.api.get())
                .forEach { source(it.allSource) }
    }
    processResources {
        outputs.upToDateWhen { false }

        project(":common").sourceSets.let { sequenceOf(it.api.get(), it.main.get()) }
                .plusElement(sourceSets.api.get())
                .forEach { from(it.resources) }

        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand("version" to project.version)
        }
    }
    publishToCurseForge {
        with(upload(Constants.MOD_CURSE_ID, project.buildDir.resolve("libs/${base.archivesName.get()}-$version.jar"))) {
            changelogType = net.darkhax.curseforgegradle.Constants.CHANGELOG_MARKDOWN
            changelog = Utils.getFullChangelog(project)
            releaseType = when (Constants.FABRIC_RELEASE) {
                Constants.Release.RELEASE -> net.darkhax.curseforgegradle.Constants.RELEASE_TYPE_RELEASE
                Constants.Release.BETA -> net.darkhax.curseforgegradle.Constants.RELEASE_TYPE_BETA
                Constants.Release.ALPHA -> net.darkhax.curseforgegradle.Constants.RELEASE_TYPE_ALPHA
            }
            addJavaVersion("Java ${Constants.JAVA_VERSION}")
            addGameVersion("Fabric")
            addGameVersion(Constants.MINECRAFT_VERSION)
            addRequirement("fabric-api")
            addRequirement("crafttweaker")
            addRequirement("jei")

            doLast {
                project.ext.set("curse_file_url", "${Constants.MOD_CURSE}/files/${curseFileId}")
            }
        }
        dependsOn(project.tasks.remapJar)
    }
    jar {
        duplicatesStrategy = DuplicatesStrategy.FAIL
    }
}