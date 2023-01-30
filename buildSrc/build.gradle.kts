plugins {
    `groovy-gradle-plugin`
}

configurations.all {
    resolutionStrategy {
        force("com.google.guava:guava:30.1.1-jre")
    }
}

repositories {
    gradlePluginPortal()
    maven("https://maven.blamejared.com") {
        name = "BlameJared"
    }
}

dependencies {
    gradleApi()
    implementation(group = "com.blamejared", name = "ModTemplate", version = "3.0.0.38")
    implementation(group = "net.darkhax.curseforgegradle", name = "CurseForgeGradle", version = "1.0.10")
    runtimeOnly(group = "com.diluv.schoomp", name = "Schoomp", version = "1.2.6")
}

gradlePlugin {
    plugins {
        create("java-conventions") {
            id = "com.blamejared.jeitweaker.java-conventions"
            implementationClass = "com.blamejared.jeitweaker.gradle.JavaConventionsPlugin"
        }
    }
}
