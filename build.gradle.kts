import com.android.build.gradle.BaseExtension
import com.android.build.gradle.AppExtension
import com.android.build.gradle.LibraryExtension
import dagger.hilt.android.plugin.HiltExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.androidx.navigation.safeargs) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.moshiX) apply false
}

// Delete Task
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

subprojects {
    plugins.applyAndroidConfig(project)

    plugins.withId(rootProject.libs.plugins.hilt.get().pluginId) {
        configure<HiltExtension> {
            enableAggregatingTask = true
        }
    }
}

/**
 * Apply configuration settings that are shared across all modules.
 */
fun PluginContainer.applyAndroidConfig(project: Project) {
    whenPluginAdded {
        when (this) {
            is com.android.build.gradle.AppPlugin -> {
                project.extensions.run {
                    getByType<com.android.build.gradle.AppExtension>()
                        .apply {
                            baseConfig(project)
                            appConfig(project)
                        }
                }
            }
            is com.android.build.gradle.LibraryPlugin -> {
                project.extensions
                    .getByType<com.android.build.gradle.LibraryExtension>()
                    .apply {
                        baseConfig(project)
                        libConfig(project)
                    }
            }
        }
    }
}

fun BaseExtension.baseConfig(project: Project) {
    val compileSdkVersion: Int = libs.versions.compileSdkVersion.get().toInt()
    val minSdkVersion: Int = libs.versions.minSdkVersion.get().toInt()
    val targetSdkVersion: Int = libs.versions.targetSdkVersion.get().toInt()
    val appVersionCode: Int = libs.versions.versionCode.get().toInt()
    val appVersionName: String = libs.versions.versionName.get()

    compileSdkVersion(compileSdkVersion)

    defaultConfig.apply {
        minSdk = minSdkVersion
        targetSdk = targetSdkVersion
        versionCode = appVersionCode
        versionName = appVersionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isTestCoverageEnabled = false
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
        }
        create("staging") {
            // For CI/CD - Jenkins
            isMinifyEnabled = false
            isTestCoverageEnabled = true
        }
    }

    /**
     * The productFlavors block is where you can configure multiple COUNTRY product flavors.
     */
    flavorDimensions("releaseType")
    productFlavors {
        create("mock") {
            buildConfigField("String", "MOCK_ENABLE", "\"true\"")
            dimension = "releaseType"
        }
        create("prod") {
            buildConfigField("String", "MOCK_ENABLE", "\"false\"")
            dimension = "releaseType"
        }
    }

    compileOptions.apply {
        // setDefaultJavaVersion(JavaVersion.VERSION_11)
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.apply {
            //jvmTarget = "11"
            jvmTarget = "1.8"
        }
    }

    buildFeatures.apply {
        compose = true
        viewBinding = true
    }

    dataBinding {
        isEnabled = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }

    packagingOptions {
        resources {
            excludes += ("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

    // Always show the result of every unit test, even if it passes.
    testOptions.unitTests {
        isIncludeAndroidResources = true
        isReturnDefaultValues = true
        all {
            it.testLogging {
                setEvents(setOf("passed", "skipped", "failed", "standardOut", "standardError"))
            }
        }
    }

    // Filter variants - https://developer.android.com/studio/build/gradle-tips#filter-variants
    variantFilter {
        // hide the staging build type and china country flavor for now.
        if (buildType.name == "release" && flavors.map { it.name }.contains("mock")) {
            ignore = true
        }
        if (buildType.name == "staging" && flavors.map { it.name }.contains("prod")) {
            ignore = true
        }
    }
}

fun AppExtension.appConfig(project: Project) {
    defaultConfig {
        applicationId = "com.vishnu.testapplication"
    }

    productFlavors {
        getByName("mock") {
            applicationIdSuffix = ".mock"
        }
    }
}

fun LibraryExtension.libConfig(project: Project) {
    namespace = "com.vishnu.testapplication.${project.name}"
}