plugins {
    kotlin("jvm") version "1.3.61"
}

val rdf4j = "3.1.0"

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("org.eclipse.rdf4j:rdf4j-client:$rdf4j")

}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}