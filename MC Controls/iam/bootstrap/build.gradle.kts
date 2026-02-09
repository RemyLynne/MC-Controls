plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(project(":iam:application"))
    implementation(project(":iam:adapter-in-cli"))
    implementation(project(":iam:adapter-in-web"))
    implementation(project(":iam:adapter-out-security"))
    implementation(project(":iam:adapter-out-persistence"))
}