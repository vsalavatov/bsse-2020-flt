val rdf4j = "3.1.0"

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("org.eclipse.rdf4j:rdf4j-client:$rdf4j")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}