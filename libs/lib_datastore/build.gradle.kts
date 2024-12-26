plugins {
    id("jp.speakbuddy.lib-convention")
}

android {
    namespace = "jp.speakbuddy.lib_datastore"
}

dependencies {
    implementation(libs.datastore)
    implementation(libs.datastore.preferences)
}
