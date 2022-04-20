plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.parcelize)
    //alias(libs.plugins.hilt)
    alias(libs.plugins.androidx.navigation.safeargs)
}

android {
    val compileSdkVersion: Int = libs.versions.compileSdkVersion.get().toInt()
    val minSdkVersion: Int = libs.versions.minSdkVersion.get().toInt()
    val targetSdkVersion: Int = libs.versions.targetSdkVersion.get().toInt()
    val appVersionCode: Int = libs.versions.versionCode.get().toInt()
    val appVersionName: String = libs.versions.versionName.get()

    compileSdk = compileSdkVersion

    defaultConfig {
        applicationId = "com.vishnu.testapplication"
        minSdk = minSdkVersion
        targetSdk = targetSdkVersion
        versionCode = appVersionCode
        versionName = appVersionName

        testInstrumentationRunner = "com.quaffie.ibanking.CFETestRunner"
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
    flavorDimensions += listOf("releaseType")
    productFlavors {
        create("mock") {
            buildConfigField("String", "MOCK_ENABLE", "\"true\"")
            dimension = "releaseType"
            applicationIdSuffix = ".mock"
        }
        create("prod") {
            buildConfigField("String", "MOCK_ENABLE", "\"false\"")
            dimension = "releaseType"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }

    addTestOptions()
    ignoreMockRelease()
}

dependencies {

    // libs directory
    implementation(project.libsDir())

    // Splash Screen
    implementation(libs.androidx.core.splashscreen)

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.cardview)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.material)

    implementation(libs.bundles.androidX.constraintlayout)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

    implementation(libs.androidx.recyclerView)
    implementation(libs.androidx.recyclerViewSelection)
    implementation(libs.androidx.annotations)

    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.livedata)
    kapt(libs.androidx.lifecycle.compiler)

    implementation(libs.bundles.compose)

    implementation(libs.androidx.room)
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)

    implementation(libs.bundles.google.accompanist)

    implementation(libs.androidx.hilt)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.bundles.kapt.hilt.compiler)

    // retrofit
    implementation("com.google.code.gson:gson:2.9.0")
    implementation(libs.bundles.squareup.retrofit)
    implementation(libs.squareup.timber)

    // Koin
    val koin_version = "3.1.3"
    implementation("io.insert-koin:koin-core:$koin_version")
    implementation("io.insert-koin:koin-android:$koin_version")
    implementation("io.insert-koin:koin-androidx-navigation:$koin_version")
    testImplementation("io.insert-koin:koin-test-junit4:$koin_version")

    // SwipeToRefresh
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    // ShimmerView
    implementation("com.facebook.shimmer:shimmer:0.5.0")

    // Mockito
    testImplementation(libs.androidx.test.espressoIdlingResource)
    testImplementation("org.mockito:mockito-core:4.4.0")
    testImplementation("org.mockito:mockito-inline:4.4.0")
    testImplementation("com.nhaarman:mockito-kotlin-kt1.1:1.6.0")

//    testImplementation deps . testArtifacts . values ()
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}

fun addTestOptions() {
    // Always show the result of every unit test, even if it passes.
    android.testOptions.unitTests {
        isIncludeAndroidResources = true

        all {
            it.testLogging {
                setEvents(setOf("passed", "skipped", "failed", "standardOut", "standardError"))
            }
        }
    }
}

// Filter variants - https://developer.android.com/studio/build/gradle-tips#filter-variants
fun ignoreMockRelease() {
    // Remove mockRelease as it's not needed.
    androidComponents {
        beforeVariants { variantBuilder ->
            // hide the staging build type and china country flavor for now.
            if (variantBuilder.buildType.equals("release")
                && variantBuilder.productFlavors[0].first == "mock"
            ) {
                variantBuilder.enable = false
            }
            if (variantBuilder.buildType.equals("staging")
                && variantBuilder.productFlavors[0].first == "prod"
            ) {
                variantBuilder.enable = false
            }
        }
    }
}