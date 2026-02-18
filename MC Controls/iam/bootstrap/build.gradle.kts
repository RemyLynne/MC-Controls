plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dep.mgmt)
}

dependencies {
    implementation(libs.spring.boot.starter)

    implementation(project(":iam:application"))
    implementation(project(":iam:adapter-in-cli"))
    implementation(project(":iam:adapter-in-web"))
    implementation(project(":iam:adapter-out-security"))
    implementation(project(":iam:adapter-out-persistence"))
}