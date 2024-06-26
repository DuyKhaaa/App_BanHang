@file:Suppress("UNUSED_EXPRESSION")

plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.do_an"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.do_an"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures{
        viewBinding = true
        dataBinding= true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.7.6")
    implementation("androidx.navigation:navigation-ui:2.7.6")
    implementation("androidx.databinding:databinding-runtime:8.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    //glider
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
    //Recycler Viewer
    implementation ("androidx.recyclerview:recyclerview:1.2.1")
    implementation ("com.google.code.gson:gson:2.8.8")
    implementation ("com.google.android.material:material:1.4.0")

    //implementation ("com.android.support:appcompat-v7:34.0.0")
    //bage
    implementation ("com.nex3z:notification-badge:1.0.4")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
}