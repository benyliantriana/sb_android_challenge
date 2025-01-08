plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.detekt)
    kotlin("kapt")
}

android {
    namespace = "jp.speakbuddy.edisonandroidexercise"
    compileSdk = 35

    defaultConfig {
        applicationId = "jp.speakbuddy.edisonandroidexercise"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
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
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.7.5"
    }
    packaging {
        resources.excludes.add("META-INF/*")
    }

    detekt {
        config.setFrom("../config/detekt/detekt.yml")
        buildUponDefaultConfig = true
    }
}

dependencies {
    implementation(libs.activity.compose)
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
    implementation(libs.material3)
    implementation(libs.compose.navigation)

    implementation(project(":libs:lib_base"))
    implementation(project(":libs:lib_ui"))
    implementation(project(":features:feature_fact"))
}

kapt {
    correctErrorTypes = true
}