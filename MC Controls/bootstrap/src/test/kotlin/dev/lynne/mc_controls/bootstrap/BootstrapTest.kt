package dev.lynne.mc_controls.bootstrap

import dev.lynne.mc_controls.bootstrap.mode.ModeResolver
import dev.lynne.mc_controls.bootstrap.mode.ResolvedMode
import dev.lynne.mc_controls.bootstrap.profile.ProfilePolicy
import dev.lynne.mc_controls.bootstrap.profile.ProfileSources
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.Banner
import org.springframework.boot.SpringApplication
import kotlin.test.Test

class BootstrapTest {
    val profileSources = mockk<ProfileSources>()
    val modeResolver = mockk<ModeResolver>()
    val profilePolicy = mockk<ProfilePolicy>()
    val appFactory = mockk<AppFactory>()
    val appConfigurer = mockk<AppConfigurer>()

    val mode = mockk<ResolvedMode>()
    val explicit = setOf("local")
    val profilesToAdd = setOf("dev")

    @Test
    fun `run resolves mode, parses profiles, checks policy, configures, and runs`() {
        val app = mockk<SpringApplication>(relaxed = true)

        val args = arrayOf("--mode=dev", "--spring.profiles.active=local")

        every { modeResolver.resolve(args) } returns mode
        every { profileSources.explicitProfiles(args) } returns explicit
        every { mode.profilesToAdd } returns profilesToAdd
        every { appFactory.create(McControlsApplication::class.java) } returns app

        justRun { profilePolicy.check(explicit, profilesToAdd) }
        justRun { appConfigurer.configure(app, mode) }

        val bootstrap = Bootstrap(modeResolver, profileSources, profilePolicy, appFactory, appConfigurer)
        bootstrap.run(McControlsApplication::class.java, args)

        verifyOrder {
            modeResolver.resolve(args)
            profileSources.explicitProfiles(args)
            profilePolicy.check(explicit, profilesToAdd)
            appFactory.create(McControlsApplication::class.java)
            app.setBannerMode(Banner.Mode.LOG)
            app.setBanner(any<StartupBanner>())
            appConfigurer.configure(app, mode)
            app.run(*args)
        }

        confirmVerified(profileSources, modeResolver, profilePolicy, appFactory, appConfigurer, app)
    }

    @Test
    fun `run does not start app if policy check fails`() {
        val args = arrayOf("--bad=true")

        every { modeResolver.resolve(args) } returns mode
        every { mode.profilesToAdd } returns profilesToAdd
        every { profileSources.explicitProfiles(args) } returns explicit

        every { profilePolicy.check(any(), any()) } throws IllegalArgumentException("nope")

        val bootstrap = Bootstrap(modeResolver, profileSources, profilePolicy, appFactory, appConfigurer)

        assertThrows<IllegalArgumentException> {
            bootstrap.run(McControlsApplication::class.java, args)
        }

        verify(exactly = 0) { appFactory.create(any()) }
        verify(exactly = 0) { appConfigurer.configure(any(), any()) }
    }
}