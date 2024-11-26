import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val monosChinosVersion: String by project
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
            version = monosChinosVersion
        }
    }
}

dependencies {

    // coroutines
    implementation(libs.kotlinx.coroutines.core)

    // json handler
    implementation(libs.json)

    // jsoup scrapper
    implementation(libs.jsoup)

    // http client
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.okhttp.interceptor)


    // tests
    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)
}