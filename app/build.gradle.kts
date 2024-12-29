plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.services)
    kotlin("kapt")
    kotlin("plugin.serialization") version "1.9.0"
}

android {
    namespace = "com.example.notesapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.notesapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    kapt {
        correctErrorTypes = true
        useBuildCache = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    // google fonts library
    implementation(libs.androidx.google.font)
    // splash screen api
    implementation(libs.androidx.splashscreen)

    // room library
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.core.ktx)
    kapt(libs.androidx.room.compiler)

    // koin library
    implementation(libs.io.koin.core)
    implementation(libs.io.koin.android)
    implementation(libs.io.koin.android.compose)
    implementation(libs.io.koin.android.workmanager){
        exclude(group = "com.google.guava")
    }

   implementation(libs.guava)

    // compose navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    // coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // firebase dependencies
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.google.firebase.auth)
    implementation(libs.google.firbasestore){
        exclude(group = "com.google.guava")
    }


    // compose lifecycle runtime
    implementation(libs.androidx.lifecycle.compose)

    // auth services with credential manager
    implementation(libs.google.play.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.auth)
    implementation(libs.google.identity.googleid)


    testImplementation(libs.junit)

    //truth test
    testImplementation(libs.truth)
    androidTestImplementation(libs.truth)

    // room test
    androidTestImplementation(libs.androidx.room.testing)

    // coroutine test
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.kotlinx.coroutines.test)

    // arch core test
    testImplementation(libs.androidx.core.testing)
    androidTestImplementation(libs.androidx.core.testing)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // mockito
    androidTestImplementation(libs.mockito.android)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}