plugins {
    application
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation(project(":core"))
}

application {
    mainClassName = "com.bsse2018.salavatov.flt.querylanganalyzer.cli.MainKt"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}