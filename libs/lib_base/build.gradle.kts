plugins {
    id("jp.speakbuddy.lib-convention")
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "jp.speakbuddy.lib_base"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle)
    implementation(libs.activity.compose)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)
    implementation(libs.material3)
}
