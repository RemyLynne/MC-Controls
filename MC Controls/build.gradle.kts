import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

plugins {
    alias(libs.plugins.gradle.git.props) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.spring) apply false
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.spring.dep.mgmt) apply false
}

allprojects {
    group = "dev.lynne.mc_controls"
    version = property("rootProject.version") as String

    repositories {
        mavenCentral()
        maven {
            url = uri("https://libraries.minecraft.net")
        }
    }
}

subprojects {
    pluginManager.withPlugin("org.jetbrains.kotlin.jvm") {
        extensions.configure<KotlinJvmProjectExtension> {
            jvmToolchain(21)
            compilerOptions {
                freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
            }
        }
        dependencies {
            "testRuntimeOnly"(libs.junit.platform.launcher)
            "testImplementation"(libs.kotlin.test.junit5)
            "testImplementation"(libs.mockk)
            "implementation"(libs.kotlin.reflect)
        }
    }
    tasks.withType<Test> {
        useJUnitPlatform()
    }
    tasks.matching { it.name == "bootRun" }.configureEach {
        (this as org.springframework.boot.gradle.tasks.run.BootRun).workingDir = rootProject.projectDir.resolve("..")
    }
    tasks.withType<Jar>().configureEach {
        archiveBaseName.set("${project.group}-${project.name}")
    }
}
