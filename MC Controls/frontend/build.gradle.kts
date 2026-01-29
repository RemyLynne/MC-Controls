val rootDir: Directory = project.layout.projectDirectory.dir("../..")
val frontendDir: Directory = rootDir.dir("WebContent")
val frontendBuildDir: Directory = frontendDir.dir("dist/apps/MC_Controls")


plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dep.mgmt)
}

dependencies {
    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.starter.web)
}

val copy = tasks.register<Copy>("copyFrontendToResources") {
    from(frontendBuildDir.dir("browser"), frontendBuildDir.file("3rdpartylicenses.txt"))
    into(layout.buildDirectory.dir("resources/main/static"))
}

tasks.named<ProcessResources>("processResources") {
    dependsOn(copy)
}

tasks.named("bootJar").configure {
    enabled = false
}