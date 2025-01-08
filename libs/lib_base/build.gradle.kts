plugins {
    id("jp.speakbuddy.lib-convention")
}

android {
    namespace = "jp.speakbuddy.lib_base"

    packaging {
        resources.excludes.add("META-INF/*")
    }
}

dependencies {
}
