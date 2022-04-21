@file:Suppress("UnstableApiUsage")

pluginManagement {
    resolutionStrategy {
        eachPlugin {
            println("pl ${this.requested} == ${this.target}")
        }
    }

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

// https://docs.gradle.org/7.4/userguide/declaring_dependencies.html#sec:type-safe-project-accessors
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "TestApplication"
include(":app")
include(":login")
include(":payortransfer")
