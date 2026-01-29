import gradlegitproperties.org.ajoberstar.grgit.Grgit

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dep.mgmt)
    alias(libs.plugins.gradle.git.props)
}

dependencies {
    developmentOnly(libs.spring.boot.devtools)
    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.starter.actuator)
    testImplementation(libs.spring.boot.starter.test)
    implementation(project(":banner"))
    implementation(project(":domain:validation"))
    implementation(project(":frontend"))
    implementation(project(":spring"))
}

gitProperties {
    keys = mutableListOf(
        "git.build.version",
        "git.commit.id.abbrev",
        "git.branch"
    )
    dotGitDirectory = file("${project.rootDir}/../.git")

    val tagMatchGlobs = listOf(
        "release/*"
    )

    fun describe(repo: Grgit): String =
        repo.describe(
            mapOf(
                "tags" to true,
                "longDescr" to true,
                "always" to true,
                "match" to tagMatchGlobs
            )
        )

    val describedPattern = Regex("""^release\/[^\/]+\/(.*)-(\d+)-g([0-9a-fA-F]+)$""") // release/<channel>/<name>-<distance>-<hash>
    fun parseDescribe(d: String): Pair<String?, Int?> {
        val m = describedPattern.matchEntire(d) ?: return Pair(null, null)
        return Pair(
            m.groupValues[1],
            m.groupValues[2].toInt()
        )
    }

    customProperty("git.version_tag.name", KotlinClosure1<Grgit, String>({
        val d = describe(this)
        parseDescribe(d).first ?: ""
    }))

    customProperty("git.version_tag.commit.count", KotlinClosure1<Grgit, String>({
        val d = describe(this)
        (parseDescribe(d).second ?: 0).toString()
    }))
}