import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val javaStringVersion: String by project
val javaVersion = JavaVersion.toVersion(javaStringVersion)
val javaVirtualMachineTarget = JvmTarget.fromTarget(javaStringVersion)

plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
    id("maven-publish")
}

java {
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
}

kotlin {
    compilerOptions {
        jvmTarget = javaVirtualMachineTarget
    }
}

publishing {
    publications {
        register<MavenPublication>("release") {
            afterEvaluate {
                from(components["java"])
            }

            groupId = "com.ead.lib"
            artifactId = "monoschinos"
            version = "0.1.0"
        }
    }
}

dependencies {
    implementation(libs.jsoup)
    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.okhttp.interceptor)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.json)
}