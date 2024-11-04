//import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
//import org.jetbrains.kotlin.konan.properties.Properties
//
//plugins {
//    alias(libs.plugins.android.application)
//    alias(libs.plugins.kotlin.android)
//    alias(libs.plugins.kotlin.compose)
//}
//
//val localProperties = Properties()
//localProperties.load(project.rootProject.file("local.properties").inputStream())
//val BASE_URL = localProperties["BASE_URL"]
//
//android {
//    namespace = "com.example.attendancecheckandroidtest"
//    compileSdk = 34
//
//    defaultConfig {
//
//        applicationId = "com.example.attendancecheckandroidtest"
//        minSdk = 33 // 최소 SDK 버전을 31로 변경
//        targetSdk = 34
//        versionCode = 1
//        versionName = "1.0"
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        buildConfigField("String", "BASE_URL", BASE_URL)
////        manifestPlaceholders["root"] = root
//
//    }
//
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
//
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_11
//        targetCompatibility = JavaVersion.VERSION_11
//    }
//
//    kotlinOptions {
//        jvmTarget = "11"
//    }
//
//    buildFeatures {
//        compose = true
//        buildConfig = true
//    }
//}
//
//
//dependencies {
//    implementation("com.google.accompanist:accompanist-swiperefresh:0.28.0")
//    implementation("com.google.code.gson:gson:2.8.9")
//    implementation("com.squareup.okhttp3:okhttp:4.9.3")
//    implementation("org.json:json:20210307")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
//    implementation("androidx.compose.material:material-icons-extended:1.4.2") // Compose 업데이트
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.activity.compose)
//    implementation(platform(libs.androidx.compose.bom))
//    implementation(libs.androidx.ui)
//    implementation(libs.androidx.ui.graphics)
//    implementation(libs.androidx.ui.tooling.preview)
//    implementation(libs.androidx.material3)
//    implementation("androidx.compose.material:material:1.4.2") // Compose 업데이트
//    implementation(libs.androidx.navigation.compose)
//    implementation(libs.androidx.compose.material.core)
//    implementation(libs.androidx.espresso.core)
//
//    // CameraX dependencies
//    implementation("androidx.camera:camera-core:1.3.4")
//    implementation("androidx.camera:camera-camera2:1.3.4")
//    // If you want to additionally use the CameraX Lifecycle library
//    implementation("androidx.camera:camera-lifecycle:1.3.4")
//    // If you want to additionally use the CameraX View class
//    implementation("androidx.camera:camera-view:1.3.4")
//
//    // ML Kit dependencies
//    implementation("com.google.mlkit:barcode-scanning:17.3.0") // ML Kit 바코드 스캐닝
//    implementation(libs.play.services.mlkit.barcode.scanning)
//    implementation(libs.firebase.firestore.ktx)
//
//    // Guava dependency for ListenableFuture
//    implementation("com.google.guava:guava:31.0.1-android")
//
//    // DarkMode
//    implementation ("com.google.android.material:material:1.7.0")
//
//    //viewModel or LiveData
//    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1") // 최신 버전 확인
//    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1") // 최신 버전 확인
//
//    // 코루틴 의존성 추가
//    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0") // 최신 버전 확인
//
//    // Testing dependencies
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//    androidTestImplementation(platform(libs.androidx.compose.bom))
//    androidTestImplementation(libs.androidx.ui.test.junit4)
//
//    // Debug dependencies
//    debugImplementation(libs.androidx.ui.tooling)
//    debugImplementation(libs.androidx.ui.test.manifest)
//
//
//}
//
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}


android {
    namespace = "com.example.attendancecheckandroidtest"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.attendancecheckandroidtest"
        minSdk = 33
        targetSdk = 33
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
}



        dependencies {
    implementation("com.google.accompanist:accompanist-swiperefresh:0.28.0")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("org.json:json:20210307")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
    implementation("androidx.compose.material:material-icons-extended:1.4.2") // Compose 업데이트
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation("androidx.compose.material:material:1.4.2") // Compose 업데이트
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.material.core)
    implementation(libs.androidx.espresso.core)

    // CameraX dependencies
    implementation("androidx.camera:camera-core:1.3.4")
    implementation("androidx.camera:camera-camera2:1.3.4")
    implementation("androidx.camera:camera-lifecycle:1.3.4")
    implementation("androidx.camera:camera-view:1.3.4")

    // ML Kit dependencies
    implementation("com.google.mlkit:barcode-scanning:17.3.0") // ML Kit 바코드 스캐닝
    implementation(libs.play.services.mlkit.barcode.scanning)
    implementation(libs.firebase.firestore.ktx)

    // Guava dependency for ListenableFuture
    implementation("com.google.guava:guava:31.0.1-android")

    // DarkMode
    implementation ("com.google.android.material:material:1.7.0")

    // ViewModel or LiveData
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1") // 최신 버전 확인
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1") // 최신 버전 확인

    // 코루틴 의존성 추가
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0") // 최신 버전 확인

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Debug dependencies
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
