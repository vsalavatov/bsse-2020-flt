group = "com.bsse2018.salavatov"
version = "1.0.0"

plugins {
    kotlin("jvm") version "1.3.61" apply false

    // plugin for beautiful gradlew test output
    id("com.adarshr.test-logger") version "2.0.0" apply false
}

subprojects {
    repositories {
        mavenCentral()
    }
    apply(plugin = "kotlin")
    apply(plugin = "com.adarshr.test-logger")
}