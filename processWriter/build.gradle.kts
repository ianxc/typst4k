plugins {
    // Apply the shared build logic from a convention plugin.
    // The shared code is located in `buildSrc/src/main/kotlin/kotlin-jdk17.gradle.kts`.
    buildsrc.convention.`kotlin-jdk17`
}

dependencies {
    // Project "app" depends on project "utils". (Project paths are separated with ":", so ":utils"
    // refers to the top-level "utils" project.)
    implementation(projects.api)
}
