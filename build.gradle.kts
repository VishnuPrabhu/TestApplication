//plugins {
//    alias(libs.plugins.android.application) apply false
//    alias(libs.plugins.android.library) apply false
//    alias(libs.plugins.kotlin.android) apply false
//    alias(libs.plugins.kotlin.kapt) apply false
//    alias(libs.plugins.kotlin.parcelize) apply false
//    alias(libs.plugins.hilt) apply false
//    //alias(libs.plugins.ktlint) apply false
//    //alias(libs.plugins.moshiX) apply false
//}

// Delete Task
tasks.register("clean", Delete::class) {
    println("===> ${rootProject.buildDir}")
    delete(rootProject.buildDir)
}