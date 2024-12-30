plugins {
    id("jp.speakbuddy.feature-convention")
    alias(libs.plugins.compose.compiler) // need to move this on convention, but how?
}

android {
    namespace = "com.example.feature_fact"
}

dependencies {
    implementation(project(":libs:lib_network"))
    implementation(project(":libs:lib_datastore"))
}