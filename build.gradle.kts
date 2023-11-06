import com.android.build.gradle.internal.dsl.decorator.SupportedPropertyType.Collection.List.type

buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.20-RC")
        classpath("com.android.tools.build:gradle:7.1.2")
        classpath ("com.google.gms:google-services:4.3.10")

    }
}

plugins {
    id ("com.android.application") version "8.1.1" apply false
    id("com.android.library") version "8.1.1" apply false
    id ("com.google.gms.google-services") version "4.4.0" apply false
}
