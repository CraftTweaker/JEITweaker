package com.blamejared.jeitweaker.gradle

import com.blamejared.modtemplate.ModTemplatePlugin
import com.blamejared.modtemplate.Utils
import com.blamejared.modtemplate.extensions.ModTemplateExtension
import net.darkhax.curseforgegradle.CurseForgeGradlePlugin
import net.darkhax.curseforgegradle.TaskPublishCurseForge
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePluginExtension
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.api.publish.tasks.GenerateModuleMetadata
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.javadoc.Javadoc
import org.gradle.jvm.tasks.Jar
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.plugins.ide.idea.IdeaPlugin
import org.gradle.plugins.ide.idea.model.IdeaModel

import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat

final class JavaConventionsPlugin implements Plugin<Project> {
    @Override
    void apply(final Project target) {
        applyJavaPlugin target
        applyIdeaPlugin target
        applyMavenPlugin target
        applyModTemplate target
        applyCurseTemplate target
        setUpDefaults target
    }

    private static void applyJavaPlugin(final Project project) {
        project.plugins.apply JavaLibraryPlugin

        final java = project.extensions.findByType JavaPluginExtension
        java.toolchain.languageVersion.set(JavaLanguageVersion.of(Constants.JAVA_VERSION))
        java.withSourcesJar()
        java.withJavadocJar()

        final apiSourceSet = java.sourceSets.create('api') {}
        final apiConfiguration = project.configurations.create('apiConfiguration') {}

        project.afterEvaluate {
            final conf = project.configurations

            if (project.hasProperty('loom')) {
                project.afterEvaluate {
                    conf.apiImplementation.extendsFrom(conf.minecraftNamed, conf.modImplementationMapped)
                }
            } else {
                conf.apiImplementation.extendsFrom(conf.minecraft)
            }
        }

        project.dependencies {
            implementation apiSourceSet.output
            it.apiConfiguration apiSourceSet.output
        }

        project.tasks.withType(JavaCompile).configureEach {
            options.compilerArgs << "-Acrafttweaker.processor.document.output_directory=${project.rootProject.file('docsOut')}"
                    << "-Acrafttweaker.processor.document.multi_source=true"
            options.encoding = StandardCharsets.UTF_8.toString()
            options.release.set(Integer.parseInt(Constants.JAVA_VERSION))
            outputs.upToDateWhen { false }
        }
        project.tasks.withType(Javadoc).configureEach {
            options.encoding = StandardCharsets.UTF_8.toString()
            options.tags = [
                    'apiNote:a:Api Note:',
                    'implSpec:a:Implementation Specifications:',
                    'implNote:a:Implementation Note:',
                    'docParam'
            ]
            options.addStringOption('Xdoclint:none', '-quiet')
        }
        project.tasks.withType(Jar).configureEach {
            manifest {
                attributes([
                        'Specification-Title' : Constants.MOD_NAME,
                        'Specification-Vendor' : Constants.MOD_AUTHOR,
                        'Specification-Version' : archiveVersion.get(),
                        'Implementation-Title' : project.name,
                        'Implementation-Version' : archiveVersion.get(),
                        'Implementation-Vendor' : Constants.MOD_AUTHOR,
                        'Implementation-Timestamp' : new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(new Date()),
                        'Timestamp' : System.currentTimeMillis(),
                        'Built-On-Java' : "${System.getProperty("java.vm.version")} (${System.getProperty("java.vm.vendor")})",
                        'Built-On-Minecraft' : Constants.MINECRAFT_VERSION
                ])
            }
        }
    }

    private static void applyIdeaPlugin(final Project project) {
        project.plugins.apply IdeaPlugin

        final idea = project.extensions.findByType IdeaModel
        idea.module.excludeDirs.addAll(['run', 'run_server', 'run_client'].collect(project.&file))
    }

    private static void applyMavenPlugin(final Project project) {
        project.plugins.apply MavenPublishPlugin

        final publishing = project.extensions.findByType PublishingExtension
        final base = project.extensions.findByType BasePluginExtension

        project.afterEvaluate {
            publishing.publications.register('mavenJava', MavenPublication) {
                groupId = project.group
                artifactId = base.archivesName.get()
                version = project.version
                from(project.components.java)

                if (project.name.equals('forge')) {
                    pom.withXml {
                        asNode().dependencies.dependency.each { dep -> dep.parent().remove(dep) }
                    }
                }
            }
            publishing.repositories {
                maven {
                    url = "file:///${System.getenv('local_maven')}"
                }
            }
        }
    }

    private static void applyModTemplate(final Project project) {
        project.plugins.apply ModTemplatePlugin

        final modTemplate = project.extensions.findByType ModTemplateExtension
        modTemplate.mcVersion Constants.MINECRAFT_VERSION
        modTemplate.curseHomepage Constants.MOD_CURSE
        modTemplate.displayName Constants.MOD_NAME
        modTemplate.changelog.with {
            enabled false
            firstCommit Constants.GIT_FIRST_COMMIT
            repo Constants.GIT_REPO
            changelogFile 'changelog.md'
        }
        modTemplate.versionTracker.with {
            enabled true
            endpoint System.getenv('versionTrackerAPI')
            author Constants.MOD_AUTHOR
            projectName "${Constants.MOD_NAME}-${project.name}"
            homepage Constants.MOD_CURSE
            uid System.getenv('versionTrackerKey')
        }
    }

    private static void applyCurseTemplate(final Project project) {
        project.plugins.apply CurseForgeGradlePlugin

        final targetTask = project.tasks.create 'publishToCurseForge', TaskPublishCurseForge
        targetTask.group = 'publishing'
        targetTask.apiToken = System.getenv('curseforgeApiToken') ?: 0
    }

    private static void setUpDefaults(final Project project) {
        project.group = Constants.MOD_GROUP
        project.version = Utils.updatingSemVersion(Constants.MOD_VERSION)

        project.tasks.withType(GenerateModuleMetadata)*.setEnabled(false)

        project.repositories.with {
            add mavenCentral()
            add maven {
                name = 'BlameJared'
                url = 'https://maven.blamejared.com'
                content {
                    includeGroup('mezz.jei')
                    includeGroup('com.blamejared.crafttweaker')
                    includeGroup('org.openzen.zencode')
                    includeGroup('com.faux.ingredientextension')
                    includeGroup('com.faux.fauxcustomentitydata')
                }
            }
            add maven {
                name = 'ParchmentMC'
                url = 'https://maven.parchmentmc.org'
            }
            add maven {
                name = 'SpongePowered'
                url = 'https://repo.spongepowered.org/repository/maven-public/'
            }
        }
    }
}
