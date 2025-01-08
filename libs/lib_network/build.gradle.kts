plugins {
    id("jp.speakbuddy.lib-convention")
}

android {
    namespace = "jp.speakbuddy.network"
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.gson.converter)
}
