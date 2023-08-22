plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("app.cash.sqldelight") version "2.0.0"
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // sql-delight runtime
                implementation("app.cash.sqldelight:runtime:2.0.0")
                // flows support for sql-delight
                implementation("app.cash.sqldelight:coroutines-extensions:2.0.0")
            }
        }

        val androidMain by getting {
            dependencies {
                // sql-delight driver - Android
                implementation("app.cash.sqldelight:android-driver:2.0.0")
            }
        }

        val iosMain by getting {
            dependencies {
                // sql-delight driver - ios
                implementation("app.cash.sqldelight:native-driver:2.0.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

android {
    namespace = "com.example.notes"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
    }
}