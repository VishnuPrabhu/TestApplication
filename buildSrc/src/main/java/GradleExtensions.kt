import org.gradle.api.Project

fun Project.libsDir() = fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar")))