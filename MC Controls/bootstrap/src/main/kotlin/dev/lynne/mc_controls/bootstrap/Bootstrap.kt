package dev.lynne.mc_controls.bootstrap

import dev.lynne.mc_controls.bootstrap.mode.ModeResolver
import dev.lynne.mc_controls.bootstrap.profile.ProfilePolicy
import dev.lynne.mc_controls.bootstrap.profile.ProfileSources
import org.springframework.boot.Banner
import org.springframework.context.ConfigurableApplicationContext
import java.nio.file.Files
import java.nio.file.Path

class Bootstrap(
    private val modeResolver: ModeResolver = ModeResolver(),
    private val profileSources: ProfileSources = ProfileSources(),
    private val profilePolicy: ProfilePolicy = ProfilePolicy(),
    private val appFactory: AppFactory = AppFactory(),
    private val appConfigurer: AppConfigurer = AppConfigurer()
) {
    fun run(primarySource: Class<*>, args: Array<String>): ConfigurableApplicationContext {
        val resolvedMode = modeResolver.resolve(args)
        val explicitProfiles = profileSources.explicitProfiles(args)

        profilePolicy.check(explicitProfiles, resolvedMode.profilesToAdd)

        var dbPath = System.getenv("APP_SQLITE_DB_PATH")
        if (dbPath == null) dbPath = "./data/app.db"
        Files.createDirectories(Path.of(dbPath).parent)

        val app = appFactory.create(primarySource)

        app.setBannerMode(Banner.Mode.LOG)
        app.setBanner(StartupBanner())

        appConfigurer.configure(app, resolvedMode)
        return app.run(*args)
    }
}