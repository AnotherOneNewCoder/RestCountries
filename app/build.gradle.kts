plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.kapt)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room)
}

android {
    namespace = "com.zhogin.restcountries"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.zhogin.restcountries"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    room {
        schemaDirectory("$projectDir/schemas")
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.dagger.hilt.android)
    implementation(libs.hilt.navigation)
    implementation(libs.javax.inject)
    implementation(libs.androidx.ui.test.junit4)
    kapt(libs.dagger.hilt.compiler)

    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client)
    implementation(libs.ktor.client.auth)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.content.negotiation)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.coil.compose)
    implementation(libs.coil.network)
    ksp(libs.androidx.room.compiler)
    api(libs.androidx.room.ktx)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)

    testImplementation(libs.ktor.client.mock)

    testImplementation(libs.robolectric)
    testImplementation(libs.core.ktx)

    // Для тестирования корутин (runTest, Dispatchers.setMain)
    testImplementation(libs.kotlinx.coroutines.test)
    // Для работы с Flow
    testImplementation(libs.turbine)

    // (Опционально)
    testImplementation(libs.mockk)
    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    androidTestImplementation(libs.androidx.room.testing)
    androidTestImplementation(libs.kotlinx.coroutines.test)
}