plugins {
    kotlin("jvm") version "1.3.61"
    application
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation(project(":core"))
}

application {
    mainClassName = "MainKt"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}