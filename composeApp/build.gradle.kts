import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            // in commonMain dependencies

            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.decompose)
            implementation(libs.decompose.jetbrains)
            implementation(libs.kotlinx.serialization.json)

            val ktor_version = "3.1.2"  // Latest Ktor for KMP :contentReference[oaicite:1]{index=1}
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")     // kotlinx-datetime core :contentReference[oaicite:7]{index=7}
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.5.0")
            implementation("io.ktor:ktor-client-core:$ktor_version")               // Core client functionality :contentReference[oaicite:4]{index=4}
            implementation("io.ktor:ktor-client-content-negotiation:$ktor_version") // ContentNegotiation plugin :contentReference[oaicite:5]{index=5}
            implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version") // JSON serialization support :contentReference[oaicite:6]{index=6}
            implementation("io.ktor:ktor-client-logging:$ktor_version")            // Logging plugin :contentReference[oaicite:7]{index=7}
            implementation("io.ktor:ktor-client-encoding:$ktor_version")           // Compression (gzip, deflate) :contentReference[oaicite:8]{index=8}
            implementation("io.ktor:ktor-client-auth:$ktor_version")
            // commonMain (as a fallback or for JVM/Native shared code)
            implementation("io.ktor:ktor-client-cio:$ktor_version")
            // CIO engine :contentReference[oaicite:11]{index=11}
            implementation("io.coil-kt.coil3:coil-compose:3.2.0")
            implementation("io.coil-kt.coil3:coil-network-okhttp:3.2.0")
// androidMain
            implementation("io.ktor:ktor-client-okhttp:$ktor_version")      // OkHttp engine on Android :contentReference[oaicite:12]{index=12}

// iosMain
            //implementation("io.ktor:ktor-client-darwin:$ktor_version")      // Darwin (iOS) engine :contentReference[oaicite:13]{index=13}
// Authentication/Authorization :contentReference[oaicite:9]{index=9}


        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "org.example.kmpproject"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.example.kmpproject"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.runtime.android)
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "org.example.kmpproject.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.example.kmpproject"
            packageVersion = "1.0.0"
        }
    }
}
