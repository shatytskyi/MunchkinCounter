plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.shatytskyi.munchcounter"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.shatytskyi.gamecounter"
        minSdk = 31
        targetSdk = 36
        versionCode = 9
        versionName = "v1.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
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
    
    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    // Compose BOM
    implementation(platform(libs.compose.bom))

    // Core Compose dependencies
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material.icons.core)
    implementation(libs.compose.material.icons.extended)
    implementation(libs.compose.material.ripple)
    implementation(libs.material)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)

    // Core Android dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Modern reactive programming
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // JSON serialization
    implementation(libs.gson)
    implementation(libs.kotlinx.serialization.json)

    // Room database
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.androidx.material3)
    ksp(libs.room.compiler)

    // SharedPreferences extensions (deprecated - will remove after Room migration)
    implementation(libs.androidx.preference.ktx)

    // Hilt for dependency injection
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    
    // Compose UI Tooling for Previews
    debugImplementation(libs.compose.ui.tooling)
}
