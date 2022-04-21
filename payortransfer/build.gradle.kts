plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    val compileSdkVersion: Int = libs.versions.compileSdkVersion.get().toInt()
    val minSdkVersion: Int = libs.versions.minSdkVersion.get().toInt()
    val targetSdkVersion: Int = libs.versions.targetSdkVersion.get().toInt()
    val appVersionCode: Int = libs.versions.versionCode.get().toInt()
    val appVersionName: String = libs.versions.versionName.get()

    compileSdk = compileSdkVersion

    defaultConfig {
        minSdk = minSdkVersion
        targetSdk = targetSdkVersion
        //versionCode = appVersionCode
        //versionName = appVersionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        //consumerProguardFiles = "consumer-rules.pro"
    }

    buildTypes {
        getByName("debug") {
            // debug config
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }

    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}