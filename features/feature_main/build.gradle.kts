plugins {
    id("jp.speakbuddy.feature-convention")
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "jp.speakbuddy.feature_main"
}

dependencies {
    implementation(libs.compose.navigation)
    implementation(project(":features:feature_fact"))
}
