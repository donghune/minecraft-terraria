rootProject.name = "terraria"

val prefix = rootProject.name

include("$prefix-library", "$prefix-plugins")

listOf("$prefix-plugins").forEach { sub ->
    include(sub)
    file(sub).listFiles()?.filter { it.isDirectory && it.name.startsWith("${rootProject.name}-") }?.forEach { file ->
        include(":${sub}:${file.name}")
    }
}