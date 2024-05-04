// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    val daggerHiltVer = "2.44"
    id("com.android.application") version "8.3.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.dagger.hilt.android") version daggerHiltVer apply false
    id("org.jetbrains.dokka") version "1.9.20"
    id("com.google.gms.google-services") version "4.4.0" apply false

}

buildscript {

    dependencies {
        classpath("com.google.gms:google-services:4.4.1")
    }
}