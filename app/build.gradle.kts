plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
//    id ("com.google.gms.google-services")
}

android {
    namespace = "com.dev.appointments"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.dev.appointments"
        minSdk = 26
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
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

//    val core_version = "1.13.1"

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.places)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
//    implementation("androidx.core:core:$core_version")
//    implementation("androidx.core:core-ktx:$core_version")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.google.code.gson:gson:2.10")
    implementation ("androidx.recyclerview:recyclerview:1.3.1")

    //persistancelayer
    implementation ("androidx.room:room-common:2.4.3")
    implementation ("androidx.room:room-ktx:2.4.3")
//    kapt ("androidx.room:room-compiler:2.4.3")

    //Room(MVI)
    implementation("androidx.room:room-rxjava3:2.4.3")
    //LiveData Reactive Stream (MVI)
    implementation("androidx.lifecycle:lifecycle-reactivestreams:2.5.1")
    //LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")

    //Rxkotlin
    implementation ("io.reactivex.rxjava3:rxandroid:3.0.0")
    implementation("io.reactivex.rxjava3:rxkotlin:3.0.0")
    implementation ("com.github.akarnokd:rxjava3-retrofit-adapter:3.0.0")

    implementation ("com.google.android.gms:play-services-maps:18.2.0")
    implementation ("com.google.android.gms:play-services-location:21.0.1")

}