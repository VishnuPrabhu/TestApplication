plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.androidx.navigation.safeargs)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.hilt)
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