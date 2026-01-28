package dev.lynne.mc_controls.bootstrap

import dev.lynne.mc_controls.banner.Banner
import dev.lynne.mc_controls.banner.data.BannerDataBlock
import dev.lynne.mc_controls.spring.GitPropertyKeys
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.SpringApplication
import org.springframework.boot.ansi.AnsiOutput
import org.springframework.mock.env.MockEnvironment
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class StartupBannerTest {
    lateinit var env: MockEnvironment
    lateinit var sb: StartupBanner
    lateinit var banner: Banner
    lateinit var versionBlock: BannerDataBlock
    lateinit var serviceBlock: BannerDataBlock

    @BeforeEach
    fun disableAnsi() {
        AnsiOutput.setEnabled(AnsiOutput.Enabled.NEVER)
    }

    @BeforeEach
    fun setupEnvironment() {
        env = MockEnvironment()
            .withProperty(GitPropertyKeys.APP_NAME, "mc-controls")
            .withProperty(GitPropertyKeys.GIT_BUILD_VERSION, "1.0.0")
            .withProperty(GitPropertyKeys.GIT_VERSION_TAG_NAME, "v1.0.0")
            .withProperty(GitPropertyKeys.GIT_VERSION_TAG_DISTANCE, "0")
            .withProperty(GitPropertyKeys.GIT_COMMIT_ID_ABBREV, "abc123")
            .withProperty(GitPropertyKeys.GIT_BRANCH, "main")

        sb = StartupBanner()
    }

    fun build() {
        banner = sb.create(env, DummySource::class.java)
        versionBlock = sb.createVersionEntry(env, DummySource::class.java)
        serviceBlock = sb.createServiceEntry(env)
    }

    @Test
    fun `printBanner throws when sourceClass is null`() {
        val env = MockEnvironment()
        val out = PrintStream(ByteArrayOutputStream())

        assertThrows<IllegalArgumentException> {
            sb.printBanner(env, null, out)
        }
    }

    @Test
    fun `create builds banner with title description and two data blocks`() {
        val title = "Some Title"
        env.setProperty(GitPropertyKeys.APP_NAME, title)

        build()

        assertEquals(title, banner.title)
        assertContains(banner.description!!, "Powered by Spring Boot")

        assertEquals(2, banner.data.size)
    }

    @Test
    fun `createVersionEntry when tags contain buildVersion has no mismatch warning`() {
        val name = "Some Title"
        val version = "1.2.3"
        env.withProperty(GitPropertyKeys.APP_NAME, name)
            .withProperty(GitPropertyKeys.GIT_BUILD_VERSION, version)
            .withProperty(GitPropertyKeys.GIT_VERSION_TAG_NAME, "v$version")

        build()


        val built = versionBlock.build(50)

        // It should mention tags
        val escapedName = Regex.escape(name)
        val escapedVersion = Regex.escape("v$version")
        val versionLineRegex = Regex("$escapedName\\s+:\\s$escapedVersion")
        assertTrue(built.contains(versionLineRegex), "The text should contain a line with the app version")

        // No mismatch line
        assertFalse(built.contains("Built version differs from git tag"), "Built should not contain version difference text")
    }

    @Test
    fun `createVersionEntry when tags do not contain buildVersion adds mismatch and Build entry`() {
        val name = "Some Title"
        val buildVersion = "1.2.3"
        val gitVersion = "v3.2.1"
        env.withProperty(GitPropertyKeys.APP_NAME, name)
            .withProperty(GitPropertyKeys.GIT_BUILD_VERSION, buildVersion)
            .withProperty(GitPropertyKeys.GIT_VERSION_TAG_NAME, gitVersion)

        build()

        val built = versionBlock.build(50)

        val escapedBuildVersion = Regex.escape(buildVersion)
        val buildLineRegex = Regex("Build\\s+:\\s$escapedBuildVersion")
        assertTrue(built.contains(buildLineRegex), "The text should contain a line with build version")

        val escapedTitle = Regex.escape(name)
        val escapedGitVersion = Regex.escape(gitVersion)
        val versionLineRegex = Regex("$escapedTitle\\s+:\\s$escapedGitVersion")
        assertTrue(built.contains(versionLineRegex), "The text should contain a line with app version, from git tag")

        assertContains(built, "Built version differs from git tag")
    }

    @Test
    fun `createVersionEntry when tags blank uses closest tag info`() {
        val name = "mc-controls"
        val version = "2.3.1"
        val distance = "7"
        val commitIdAbbrev = "abc123"
        val branch = "deploy"
        env.withProperty(GitPropertyKeys.APP_NAME, name)
            .withProperty(GitPropertyKeys.GIT_BUILD_VERSION, version)
            .withProperty(GitPropertyKeys.GIT_VERSION_TAG_NAME, "v$version")
            .withProperty(GitPropertyKeys.GIT_VERSION_TAG_DISTANCE, distance)
            .withProperty(GitPropertyKeys.GIT_COMMIT_ID_ABBREV, commitIdAbbrev)
            .withProperty(GitPropertyKeys.GIT_BRANCH, branch)

        build()

        val built = versionBlock.build(50)


        val escapedTitle = Regex.escape(name)
        val escapedVersion = Regex.escape(version)
        val versionLineRegex = Regex("$escapedTitle\\s+:\\sv$escapedVersion\\+$distance\\s\\($branch\\s@\\s$commitIdAbbrev\\)")  //name : v1.2.3+5 (branch @ id)
        assertTrue(built.contains(versionLineRegex), "The text should contain a line with app version, from git tag, including branch and commit distance")
    }

    @Test
    fun `createVersionEntry adds unbuilt warning when source has no implementationVersion`() {
        val blockExcludingVersion = sb.createVersionEntry(env, DummySource::class.java)
        val builtExcludingVersion = blockExcludingVersion.build(50)

        val blockIncludingVersion = sb.createVersionEntry(env, SpringApplication::class.java)
        val builtIncludingVersion = blockIncludingVersion.build(50)

        assertTrue(builtExcludingVersion.contains("Running non-built version"), "with an absence of version, unbuilt marker should be added")
        assertFalse(builtIncludingVersion.contains("Running non-built version"), "with a version, unbuilt marker should NOT be added")
    }



    private class DummySource
}