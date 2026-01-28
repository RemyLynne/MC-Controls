package dev.lynne.mc_controls.bootstrap

import dev.lynne.mc_controls.bootstrap.mode.ResolvedMode
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verifyOrder
import org.springframework.boot.SpringApplication
import kotlin.test.Test

class AppConfigurerTest {
    @Test
    fun `configure sets additional profiles and then invokes mode configure`() {
        val app = mockk<SpringApplication>(relaxed = true)

        val configureHook = mockk<(SpringApplication) -> Unit>()

        val resolvedMode = mockk<ResolvedMode>()
        val profilesToAdd = linkedSetOf("dev", "local") // deterministic order

        every { resolvedMode.profilesToAdd } returns profilesToAdd
        every { resolvedMode.configure } returns configureHook

        justRun { configureHook.invoke(any()) }

        val configurer = AppConfigurer()
        configurer.configure(app, resolvedMode)

        verifyOrder {
            app.setAdditionalProfiles(*profilesToAdd.toTypedArray())
            configureHook.invoke(app)
        }

        confirmVerified(app, configureHook)
    }
}