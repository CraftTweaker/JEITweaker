#!/usr/bin/env groovy

def docsOutDir = 'docsOut'
def docsRepositoryUrl = 'git@github.com:CraftTweaker/CraftTweaker-Documentation.git'
def gitSshCredentialsId = 'crt_git_ssh_key'
def botUsername = 'crafttweakerbot'
def botEmail = 'crafttweakerbot@gmail.com'

def documentationDir = 'CrafttweakerDocumentation'
def exportDirInRepo = 'docs_exported/1.19/jeitweaker'

def docCommitMessage = { -> "CI doc export for JeiTweaker build ${env.BUILD_NUMBER}\n\nMatches git commit ${env.GIT_COMMIT} on branch ${env.BRANCH_NAME}" }

def branchName = "1.19.2";

pipeline {
    agent any
    tools {
        jdk "jdk-17.0.1"
    }

    environment {
        curseforgeApiToken = credentials('curseforge_token')
        discordCFWebhook = credentials('discord_cf_webhook')
        versionTrackerKey = credentials('version_tracker_key')
        versionTrackerAPI = credentials('version_tracker_api')
    }

    stages {
        stage('Clean') {
            steps {
                echo 'Cleaning Project'
                sh 'chmod +x gradlew'
                sh './gradlew clean'
            }
        }
        stage('Build') {
            steps {
                echo 'Building'
                sh './gradlew build'
            }
        }
        stage('Git Changelog') {
            steps {
                sh './gradlew genGitChangelog'
            }
        }

        stage('Publish') {
            when {
                branch branchName
            }
            stages {
                stage('Updating version') {
                    steps {
                        echo 'Updating version'
                        sh './gradlew updateVersionTracker'
                    }
                }
                stage('Deploy to Maven') {
                    steps {
                        echo 'Deploying to Maven'
                        sh './gradlew publish'
                    }
                }
                stage('Deploying to CurseForge') {
                    steps {
                        echo 'Deploying to CurseForge'
                        sh './gradlew publishToCurseForge postDiscord'
                    }
                }
                stage('Exporting Documentation') {
                    when {
                        branch branchName
                    }
                    stages {
                        stage('Cloning Repository') {
                            steps {
                                echo 'Cloning documentation repository at "main" branch'
                                dir(documentationDir) {
                                    git credentialsId: gitSshCredentialsId, url: docsRepositoryUrl, branch: 'main', changelog: false
                                }
                            }
                        }
                        stage('Clearing export') {
                            steps {
                                echo 'Clearing existing documentation export'
                                dir(documentationDir) {
                                    sh "rm --recursive --force ./$exportDirInRepo"
                                }
                            }
                        }
                        stage('Moving documentation') {
                            steps {
                                echo 'Moving generated documentation to clone'
                                sh "mkdir --parents ./$documentationDir/$exportDirInRepo"
                                sh "mv ./$docsOutDir/* ./$documentationDir/$exportDirInRepo/"
                            }
                        }
                        stage('Publishing documentation') {
                            steps {
                                echo 'Committing documentation'
                                dir(documentationDir) {
                                    sshagent([gitSshCredentialsId]) {
                                        sh "git config user.name $botUsername"
                                        sh "git config user.email $botEmail"
                                        sh 'git add -A'
                                        sh "git diff-index --quiet HEAD || git commit -m '${docCommitMessage()}'"
                                        sh 'git push origin main'
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    post {
        always {
            archive 'build/libs/**.jar'
            archive 'changelog.md'
        }
    }
    options {
        disableConcurrentBuilds()
    }
}
