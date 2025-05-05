import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.composeHotReload)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    // Targets iOS
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    targets.withType<KotlinNativeTarget> {
        binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            implementation(projects.designsystem.theme)
            implementation(projects.designsystem.component)
            implementation(projects.feature.splash)
            implementation(projects.feature.login)
            implementation(projects.feature.balance)
            implementation(projects.common)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            api(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.navigation.compose)
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor2)
            implementation(libs.coil.network.ktor3)
        }
    }
}

android {
    namespace = "com.raya.challenge"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.raya.challenge"
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
    debugImplementation(compose.uiTooling)
}

val buildXcFramework by tasks.registering {
    group = "build"
    description = "Builds XCFramework for iOS"

    dependsOn(
        "linkDebugFrameworkIosArm64",
        "linkDebugFrameworkIosSimulatorArm64"
    )

    doLast {
        val outputDir = buildDir.resolve("XCFrameworks/Debug")
        outputDir.mkdirs()

        exec {
            commandLine(
                "xcodebuild",
                "-create-xcframework",
                "-framework", "${buildDir}/bin/iosArm64/debugFramework/ComposeApp.framework",
                "-framework", "${buildDir}/bin/iosSimulatorArm64/debugFramework/ComposeApp.framework",
                "-output", "$outputDir/ComposeApp.xcframework"
            )
        }
    }
}

