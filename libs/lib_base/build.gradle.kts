plugins {
    id("jp.speakbuddy.lib-convention")
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "jp.speakbuddy.lib_base"

    packaging {
        resources.excludes.add("META-INF/*")
    }
}

dependencies {
    implementation(libs.robolectric)
    implementation(libs.androidx.ui.test.junit)
}
