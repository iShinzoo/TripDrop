plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.tripdrop"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.tripdrop"
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
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    implementation(libs.androidx.media3.effect)
    implementation(libs.play.services.analytics.impl)
    implementation(libs.firebase.messaging)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // coil library for image
    implementation("io.coil-kt:coil-compose:2.7.0")

    // Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    ksp("com.google.dagger:hilt-compiler:2.51.1")

    // For instrumentation tests
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.51.1")
    kspAndroidTest("com.google.dagger:hilt-compiler:2.51.1")

    // For local unit tests
    testImplementation("com.google.dagger:hilt-android-testing:2.51.1")
    kspTest("com.google.dagger:hilt-compiler:2.51.1")


    // system ui controller
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.36.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // paging 3 library
    implementation("androidx.paging:paging-runtime:3.3.2")

    // alternatively - without Android dependencies for tests
    testImplementation("androidx.paging:paging-common:3.3.2")

    // optional - RxJava2 support
    implementation("androidx.paging:paging-rxjava2:3.3.2")

    // optional - RxJava3 support
    implementation("androidx.paging:paging-rxjava3:3.3.2")

    // optional - Guava ListenableFuture support
    implementation("androidx.paging:paging-guava:3.3.2")

    // optional - Jetpack Compose integration
    implementation("androidx.paging:paging-compose:3.3.2")

    // Compose Navigation
    implementation("androidx.navigation:navigation-compose:2.8.1")

    //Compose Foundation
    implementation("androidx.compose.foundation:foundation:1.7.2")

    // System Ui Controller
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.36.0")

    //material icon extended library
    implementation("androidx.compose.material:material-icons-extended:1.7.2")

    // Compose Navigation
    implementation("androidx.navigation:navigation-compose:2.8.1")

    implementation("androidx.compose.runtime:runtime-livedata:1.7.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")


    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")

    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    // new Splash Screen API
    implementation("androidx.core:core-splashscreen:1.1.0-alpha02")

    // modal bottom sheet
    implementation("androidx.compose.material:material:1.7.2")
    implementation("androidx.compose.ui:ui:1.7.2")

    //lottie Animations JSON support
    implementation("com.airbnb.android:lottie-compose:6.5.2")

    implementation("com.google.accompanist:accompanist-permissions:0.36.0")

    implementation("com.google.accompanist:accompanist-navigation-animation:0.36.0")

    implementation ("dev.shreyaspatil.EasyUpiPayment:EasyUpiPayment:3.0.3")
}