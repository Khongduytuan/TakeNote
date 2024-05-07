plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
//    id ("androidx.navigation.safeargs.kotlin")
    id ("kotlin-parcelize")
}

android {
    namespace = "com.eagletech.takenote"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.eagletech.takenote"
        minSdk = 21
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        viewBinding = true
    }

}

dependencies {

//    implementation("androidx.core:core-ktx:1.9.0")
//    implementation("androidx.appcompat:appcompat:1.6.1")
//    implementation("com.google.android.material:material:1.11.0")
//    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.5")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
//
//
//
//    // Architectural Components
//    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
//    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
//    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
//
//    // Room
//    implementation ("androidx.room:room-runtime:2.2.6")
//    kapt ("androidx.room:room-compiler:2.2.6")
//
//    // Kotlin Extensions and Coroutines support for Room
//    implementation ("androidx.room:room-ktx:2.2.6")
//
//    // Navigation Components
//    implementation ("androidx.navigation:navigation-fragment-ktx:2.3.4")
//    implementation ("androidx.navigation:navigation-ui-ktx:2.3.4")
//    implementation ("androidx.navigation:navigation-dynamic-features-fragment:2.3.4")
//
//
//    // Espresso
//    androidTestImplementation ("androidx.test.espresso:espresso-core:3.3.0")
//    androidTestImplementation ("androidx.test.espresso:espresso-contrib:3.3.0")
//
//    // Mockk.io
//    androidTestImplementation ("io.mockk:mockk-android:1.9.3")
//
//    // androidx.test
//    androidTestImplementation ("androidx.test:runner:1.1.2")
//    androidTestImplementation ("androidx.test:core:1.1.2")
//    androidTestImplementation ("androidx.test.ext:junit-ktx:1.1.2")
//
//    // androidx.fragment
//    debugImplementation ("androidx.fragment:fragment-testing:1.3.2")
//    implementation ("androidx.fragment:fragment-ktx:1.3.2")

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")



    // Architectural Components
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")

    // Room
    implementation ("androidx.room:room-runtime:2.6.1")
    kapt ("androidx.room:room-compiler:2.6.1")

    // Kotlin Extensions and Coroutines support for Room
    implementation ("androidx.room:room-ktx:2.6.1")

    // Navigation Components
    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation ("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation ("androidx.navigation:navigation-dynamic-features-fragment:2.7.7")


    // Espresso
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation ("androidx.test.espresso:espresso-contrib:3.5.1")

    // Mockk.io
//    androidTestImplementation ("io.mockk:mockk-android:1.9.3")
    // Payment Amazon
    implementation("com.amazon.device:amazon-appstore-sdk:3.0.4")
}