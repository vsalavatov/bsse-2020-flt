plugins {
    application
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation(project(":core"))
}

application {
    mainClassName = "com.bsse2018.salavatov.flt.cykquery.cli.MainKt"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}