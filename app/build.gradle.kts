import Configs.Android.applicationId
import Configs.Android.compileSdkVersion
import Configs.Android.minSdkVersion
import Configs.Android.targetSdkVersion
import Configs.Android.testInstrumentationRunner
import Configs.Android.versionCode
import Configs.Android.versionName
import com.android.build.gradle.ProguardFiles.getDefaultProguardFile
import org.gradle.api.JavaVersion.VERSION_1_8
import org.gradle.internal.impldep.bsh.commands.dir

import org.jetbrains.kotlin.config.KotlinCompilerVersion
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.setValue

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(Configs.Android.compileSdkVersion)
    defaultConfig {
        applicationId = Configs.Android.applicationId
        minSdkVersion(Configs.Android.minSdkVersion)
        targetSdkVersion(Configs.Android.targetSdkVersion)
        versionCode = Configs.Android.versionCode
        versionName = Configs.Android.versionName
        testInstrumentationRunner = Configs.Android.testInstrumentationRunner
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(kotlin("stdlib-jdk7", KotlinCompilerVersion.VERSION))
    implementation(Configs.Libs.appcompat)
    implementation(Configs.Libs.constraintLayout)
    implementation(Configs.Libs.courotinesCore)
    implementation(Configs.Libs.courotinesAndroid)
    implementation(Configs.Libs.andoroidKTX)
    implementation(Configs.Libs.lifeCycleExtension)
    kapt(Configs.Libs.lifeCycleCompiler)

    testImplementation(Configs.TestLibs.junit)
    androidTestImplementation(Configs.TestLibs.testRunner)
    androidTestImplementation(Configs.TestLibs.espresso)
}
