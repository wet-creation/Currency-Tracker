plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.dokka")
    id("com.google.gms.google-services")

}

android {
    namespace = "com.mycompany.currencytracker"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mycompany.currencytracker"
        minSdk = 28
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
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources.excludes.addAll(
            listOf(
                "/META-INF/{AL2.0,LGPL2.1}",
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md",
            )
        )
    }

}


dependencies {
    implementation("androidx.compose.ui:ui-android:1.6.7")
    implementation("com.android.car.ui:car-ui-lib:2.6.0")
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
    implementation("com.google.firebase:firebase-common-ktx:21.0.0")
    val coroutinesVer = "1.7.3"
    val coroutineLifecycleScopeVer = "2.7.0"
    val daggerHiltVer = "2.49"
    val retrofitVer = "2.9.0"
    val okhttpVer = "5.0.0-alpha.2"
    val hiltCompilerVer = "1.2.0"
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2024.05.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("io.coil-kt:coil-compose:2.3.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:3.12.4")
    testImplementation("org.mockito:mockito-android:3.12.4")
    testImplementation("app.cash.turbine:turbine:0.6.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVer")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.05.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.38.1")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("eu.bambooapps:compose-material3-pullrefresh:1.1.1")

    implementation("androidx.core:core-splashscreen:1.0.1")

    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.27.0")

    // Coroutines
    //noinspection GradleDependency
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVer")
    //noinspection GradleDependency
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVer")

// Coroutine Lifecycle Scopes
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$coroutineLifecycleScopeVer")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$coroutineLifecycleScopeVer")

// Dagger - Hilt
    implementation("com.google.dagger:hilt-android:$daggerHiltVer")
    kapt("com.google.dagger:hilt-android-compiler:$daggerHiltVer")
    kapt("androidx.hilt:hilt-compiler:$hiltCompilerVer")
    implementation("androidx.hilt:hilt-navigation-compose:$hiltCompilerVer")


// Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofitVer")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVer")
    implementation("com.squareup.okhttp3:okhttp:$okhttpVer")
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttpVer")
    implementation ("com.squareup.okhttp3:logging-interceptor:$okhttpVer")

    testImplementation("io.mockk:mockk:1.13.10")
    androidTestImplementation("io.mockk:mockk-android:1.13.10")
    androidTestImplementation("io.mockk:mockk-agent:1.13.10")
// Documentation
    implementation("org.jetbrains.dokka:android-documentation-plugin:1.9.20")
// Firebase SDK
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-analytics")

//YCharts
    implementation("co.yml:ycharts:2.1.0")
    implementation("de.charlex.compose:revealswipe:1.0.0")
}
