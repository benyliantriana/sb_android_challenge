plugins {
    id("jp.speakbuddy.lib-convention")
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "jp.speakbuddy.lib_ui"

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle)
    implementation(libs.activity.compose)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)
    implementation(libs.material3)
    implementation(project(":libs:lib_base"))

    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation(libs.androidx.ui.test.junit)
    testImplementation(libs.junit4)
}