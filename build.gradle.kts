buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath("gradle.plugin.com.github.johnrengelman:shadow:7.1.2")
    }
}

plugins {
    kotlin("jvm") version "1.7.10"
}


java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "com.github.johnrengelman.shadow")

    tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        archiveFileName.set("${project.name}.jar")
    }

    repositories {
        maven("https://papermc.io/repo/repository/maven-public/")
    }

    dependencies {
        compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")

        compileOnly(kotlin("stdlib"))
        compileOnly(kotlin("reflect"))

        compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
        compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-core:1.4.1")
        compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")

        compileOnly("io.github.monun:kommand-api:2.14.0")
        compileOnly("com.github.shynixn.mccoroutine:mccoroutine-bukkit-api:2.6.0")
        compileOnly("com.github.shynixn.mccoroutine:mccoroutine-bukkit-core:2.6.0")
    }
}

tasks {
    register<DefaultTask>("setupModules") {
        doLast {
            val defaultPrefix = "carrot"
            val projectPrefix = rootProject.name

            if (defaultPrefix != projectPrefix) {
                fun rename(suffix: String) {
                    val from = "$defaultPrefix-$suffix"
                    val to = "$projectPrefix-$suffix"
                    file(from).takeIf { it.exists() }?.renameTo(file(to))
                }

                rename("plugins")
                rename("library")
            }
        }
    }
}