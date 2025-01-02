package jp.speakbuddy

import jp.speakbuddy.common.libs

plugins {
    `android-library`
    `kotlin-android`
    `kotlin-kapt`
    `kotlin-parcelize`
    `kotlinx-serialization`
    kotlin("kapt")
}

android {
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.7.5"
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle)
    implementation(libs.activity.compose)
    implementation(libs.coil)
    implementation(libs.coil.network)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)
    implementation(libs.datastore.preferences)
    implementation(libs.gson.converter)
    implementation(libs.hilt)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.hilt.compiler)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.material3)
    implementation(libs.protobuf.kotlin.lite)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}
