val pluginProject = project

subprojects {
    val projectName = name.removePrefix("terraria-")
    val sep = File.separator
    val buildFolder = "${buildDir.absolutePath}${sep}libs$sep${project.name}.jar"

    val build = tasks.withType<Jar> {
        dependsOn(tasks.processResources)
        archiveFileName.set("${project.name}.jar")
        doLast {
            copy {
                from(buildFolder)
                into("${pluginProject.rootDir.absolutePath}${sep}.server${sep}plugins-dev${sep}")
            }
        }
    }

    rootProject.tasks {
        register<DefaultTask>(projectName) {
            dependsOn(build)
        }
    }

    if (version == "unspecified") {
        version = rootProject.version
    }

    tasks {
        processResources {
            filesMatching("**/*.yml") {
                expand(rootProject.properties)
            }
        }
    }

    dependencies {
        compileOnly(project(":terraria-library"))
    }
}

tasks.withType<Jar> {
    dependsOn(*childProjects.values.map { it.tasks.jar }.toTypedArray())
}
