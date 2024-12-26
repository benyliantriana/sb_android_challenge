plugins {
    id("jp.speakbuddy.lib-convention")
    alias(libs.plugins.protobuf)
}

android {
    namespace = "jp.speakbuddy.lib_datastore"
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.22.0"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                val java by registering {
                    option("lite")
                }
                val kotlin by registering {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    implementation(libs.datastore.preferences)
    implementation(libs.protobuf.kotlin.lite)
}
