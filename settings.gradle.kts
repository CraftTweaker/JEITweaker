pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/") {
            name = "Fabric"
        }
        maven("https://repo.spongepowered.org/repository/maven-public/") {
            name = "Sponge Snapshots"
        }
        maven("https://maven.minecraftforge.net") {
            name = "Forge"
        }
        maven("https://maven.parchmentmc.org") {
            name = "ParchmentMC"
        }
        maven("https://maven.blamejared.com") {
            name = "BlameJared"
        }
    }
}

rootProject.name = "JEITweaker"
include("common", "fabric", "forge")

if (file("CraftTweaker-Annotation-Processors").exists()) {
    includeBuild("CraftTweaker-Annotation-Processors") {
        dependencySubstitution {
            substitute(module("com.blamejared.crafttweaker:Crafttweaker_Annotation_Processors")).using(project(":"))
        }
    }
}