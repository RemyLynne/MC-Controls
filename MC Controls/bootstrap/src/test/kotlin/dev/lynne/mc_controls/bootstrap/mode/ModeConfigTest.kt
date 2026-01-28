package dev.lynne.mc_controls.bootstrap.mode

import dev.lynne.mc_controls.spring.ServerType
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import org.springframework.boot.SpringApplication
import org.springframework.boot.WebApplicationType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ModeConfigTest {
    @Test
    fun `forMode SERVER adds server profile and configures servlet web type`() {
        val config = ModeConfig()
        val resolved = config.forMode(Mode.SERVER)

        assertEquals(setOf(ServerType.SERVER()), resolved.profilesToAdd)

        val app = mockk<SpringApplication>(relaxed = true)
        resolved.configure.invoke(app)

        verify { app.setWebApplicationType(WebApplicationType.SERVLET) }
        confirmVerified(app)
    }

    @Test
    fun `forMode CLI adds cli profile and configures none web type`() {
        val config = ModeConfig()
        val resolved = config.forMode(Mode.CLI)

        assertEquals(setOf(ServerType.CLI()), resolved.profilesToAdd)

        val app = mockk<SpringApplication>(relaxed = true)
        resolved.configure.invoke(app)

        verify { app.setWebApplicationType(WebApplicationType.NONE) }
        confirmVerified(app)
    }

    @Test
    fun `forMode supports all Mode enum values`() {
        val config = ModeConfig()

        for (mode in Mode.entries) {
            val resolved = config.forMode(mode)
            assertNotNull(resolved, "ModeConfig did not provide ResolvedMode for $mode")
        }
    }
}