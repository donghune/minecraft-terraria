val pluginProject = project

subprojects {
    val projectName = name
    val sep = File.separator
    val buildFolder = "${buildDir.absolutePath}${sep}libs$sep${project.name}.jar"

    val build = tasks.withType<Jar> {
        dependsOn(tasks.processResources)
        archiveFileName.set("${project.name}.jar")
        doLast {
            copy {
                from(buildFolder)
                into("${pluginProject.rootDir.absolutePath}${sep}.server${sep}plugins${sep}")
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
        implementation(project(":sample-library"))
    }
}

tasks.withType<Jar> {
    dependsOn(*childProjects.values.map { it.tasks.jar }.toTypedArray())
}
