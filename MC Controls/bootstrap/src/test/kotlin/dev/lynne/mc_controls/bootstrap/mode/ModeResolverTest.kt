package dev.lynne.mc_controls.bootstrap.mode

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlin.test.Test
import kotlin.test.assertSame

class ModeResolverTest {
    @Test
    fun `resolve detects mode and returns configured ResolvedMode`() {
        val detector = mockk<ModeDetector>()
        val config = mockk<ModeConfig>()

        val args = arrayOf("--whatever")
        val mode = mockk<Mode>()
        val resolved = mockk<ResolvedMode>()

        every { detector.detect(args) } returns mode
        every { config.forMode(mode) } returns resolved

        val resolver = ModeResolver(detector, config)
        val result = resolver.resolve(args)

        assertSame(resolved, result)

        verifyOrder {
            detector.detect(args)
            config.forMode(mode)
        }
        confirmVerified(detector, config)
    }
}