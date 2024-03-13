// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {

        google()
        mavenCentral()
    }
    dependencies {
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.0")

    }
}
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    id ("androidx.navigation.safeargs.kotlin")version "2.4.1" apply false
}