package dev.lynne.mc_controls.bootstrap

import dev.lynne.mc_controls.banner.Banner
import dev.lynne.mc_controls.banner.data.BannerDataBlock
import dev.lynne.mc_controls.banner.data.BannerDataEntry
import dev.lynne.mc_controls.spring.GitPropertyKeys
import dev.lynne.mc_controls.spring.ServerType
import org.springframework.boot.ansi.AnsiColor
import org.springframework.boot.ansi.AnsiOutput
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.core.env.Environment
import java.io.PrintStream
import java.lang.management.ManagementFactory
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import org.springframework.boot.Banner as SpringBanner

class StartupBanner : SpringBanner {
    private val poweredByLine = " :: Powered by Spring Boot :: "
    private val buildVersionMismatch = "❌ Built version differs from git tag"
    private val runningUnbuilt = "⚠ Running non-built version (IDE/classes)"

    override fun printBanner(
        environment: Environment,
        sourceClass: Class<*>?,
        out: PrintStream
    ) {
        if (sourceClass == null)
            throw IllegalArgumentException("Missing sourceClass")
        val banner = create(environment, sourceClass)
        out.println(AnsiOutput.toString(AnsiColor.DEFAULT)) //force color reset
        banner.print(out)
    }

    fun create(
        environment: Environment,
        sourceClass: Class<*>
    ) = Banner(
        title = environment.getProperty(GitPropertyKeys.APP_NAME),
        description = AnsiOutput.toString(AnsiColor.GREEN, poweredByLine),
        data = mutableListOf(
            createVersionEntry(environment, sourceClass),
            createServiceEntry(environment)
        )
    )

    fun createVersionEntry(
        environment: Environment,
        sourceClass: Class<*>
    ): BannerDataBlock {
        val block = BannerDataBlock(
            title = "-- Version --"
        )

        block.entries.add(BannerDataEntry(
            "Spring Boot",
            SpringBootApplication::class.java.`package`.implementationVersion
        ))

        val name = environment.getProperty(GitPropertyKeys.APP_NAME)!!
        val buildVersion = environment.getProperty(GitPropertyKeys.GIT_BUILD_VERSION)!!
        val versionTagName = environment.getProperty(GitPropertyKeys.GIT_VERSION_TAG_NAME)!!
        val versionTagDistance = environment.getProperty(GitPropertyKeys.GIT_VERSION_TAG_DISTANCE)!!.toInt()
        val commitId = environment.getProperty(GitPropertyKeys.GIT_COMMIT_ID_ABBREV)!!
        val branch = environment.getProperty(GitPropertyKeys.GIT_BRANCH)!!

        val versionBuilder = StringBuilder(versionTagName)
        if (versionTagDistance > 0)
            versionBuilder.append("+$versionTagDistance")

        if (versionTagDistance > 0 || !listOf("master", "main").contains(branch))
            versionBuilder.append(" ($branch @ $commitId)")

        block.entries.add(BannerDataEntry(name, versionBuilder.toString()))
        if (!versionTagName.contains(buildVersion)) {
            block.entries.add(BannerDataEntry("Build", buildVersion))
            block.entries.add(BannerDataEntry(AnsiOutput.toString(AnsiColor.RED, buildVersionMismatch)))
        }

        if (sourceClass.`package`.implementationVersion == null)
            block.entries.add(BannerDataEntry(AnsiOutput.toString(AnsiColor.YELLOW, runningUnbuilt)))

        return block
    }

    fun createServiceEntry(environment: Environment): BannerDataBlock {
        val block = BannerDataBlock(
            title = "-- Service --"
        )

        // Start Time
        block.entries.add(BannerDataEntry(
            "Start Time",
            Instant.ofEpochMilli(ManagementFactory.getRuntimeMXBean().startTime)
                .atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        ))

        val profiles = environment.activeProfiles

        // Mode
        val mode = if (ServerType.SERVER() in profiles)
            "Server"
        else if (ServerType.CLI() in profiles)
            "CLI"
        else
            AnsiOutput.toString(AnsiColor.RED, "UNKNOWN")
        block.entries.add(BannerDataEntry("Mode", mode))

        //Profiles
        block.entries.add(BannerDataEntry("Profiles", profiles.joinToString()))

        return block
    }
}