plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(project(":iam:application"))
}