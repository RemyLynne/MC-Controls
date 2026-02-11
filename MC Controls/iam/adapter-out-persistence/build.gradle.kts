plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.jpa)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dep.mgmt)
}

dependencies {
    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.starter.data.jpa)

    implementation(project(":domain"))
    implementation(project(":spring"))

    implementation(project(":iam:application"))
}