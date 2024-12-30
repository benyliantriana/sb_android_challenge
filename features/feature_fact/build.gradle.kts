plugins {
    id("jp.speakbuddy.feature-convention")
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.example.feature_fact"
}

dependencies {
    implementation(project(":libs:lib_base"))
    implementation(project(":libs:lib_network"))
    implementation(project(":libs:lib_datastore"))
}
