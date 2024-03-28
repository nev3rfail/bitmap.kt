plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.spotless)
    `maven-publish`
}

kotlin {
    jvmToolchain(8)
    androidTarget {
        compilations.all {
            kotlinOptions {
                freeCompilerArgs += "-Xexpect-actual-classes"
                jvmTarget = JavaVersion.VERSION_1_8.toString()
            }
        }
    }
    jvm {
        compilations.all {
            kotlinOptions {
                freeCompilerArgs += "-Xexpect-actual-classes"
                jvmTarget = JavaVersion.VERSION_1_8.toString()
            }
        }
    }

    // iosX64()
    // iosArm64()
    // iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.io)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "dev.mihon.bitmap"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }

}


afterEvaluate {
    publishing {
        println()
        publications {
            register<MavenPublication>("release") {
                groupId = "com.github.nev3rfail"
                artifactId = "bitmap.kt"
                version = "1.0"
            }
        }
    }
}



spotless {
    kotlin {
        target("**/*.kt", "**/*.kts")
        targetExclude("**/build/**/*.kt")
        ktlint(libs.ktlint.cli.get().version)
            .editorConfigOverride(
                mapOf(
                    "ktlint_standard_discouraged-comment-location" to "disabled",
                ),
            )
        trimTrailingWhitespace()
        endWithNewline()
    }
    format("misc") {
        target("**/.gitignore", "**/*.xml")
        targetExclude("**/build/**/*.xml")
        trimTrailingWhitespace()
        endWithNewline()
    }
}
