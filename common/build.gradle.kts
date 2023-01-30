import com.blamejared.jeitweaker.gradle.Constants

plugins {
    id("com.blamejared.jeitweaker.java-conventions")
    id("org.spongepowered.gradle.vanilla") version "0.2.1-SNAPSHOT"
}

base.archivesName.set("${Constants.MOD_NAME}-core-${Constants.MINECRAFT_VERSION}")

minecraft {
    version(Constants.MINECRAFT_VERSION)
}

dependencies {
    apiCompileOnly(group = "com.blamejared.crafttweaker", name = "CraftTweaker-common-${Constants.MINECRAFT_VERSION}", version = Constants.CRAFTTWEAKER_VERSION)
    apiCompileOnly(group = "mezz.jei", name = "jei-${Constants.MINECRAFT_VERSION}-common-api", version = Constants.JEI_VERSION)

    compileOnly(group = "org.spongepowered", name = "mixin", version = "0.8.4")
    compileOnly(group = "com.blamejared.crafttweaker", name = "CraftTweaker-common-${Constants.MINECRAFT_VERSION}", version = Constants.CRAFTTWEAKER_VERSION)
    compileOnly(group = "mezz.jei", name = "jei-${Constants.MINECRAFT_VERSION}-common-api", version = Constants.JEI_VERSION)
}
