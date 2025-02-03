plugins {
    // Apply the shared build logic from a convention plugin.
    // The shared code is located in `buildSrc/src/main/kotlin/kotlin-jdk17.gradle.kts`.
    buildsrc.convention.`kotlin-jdk17`
}

dependencies {
    testImplementation(kotlin("test"))
}
