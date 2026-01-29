package dev.lynne.mc_controls.bootstrap.mode

import dev.lynne.mc_controls.bootstrap.exception.command.MissingCommandException
import dev.lynne.mc_controls.bootstrap.exception.command.NoSuchCommandException
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class ModeDetectorTest {
    private val detector = ModeDetector(
        commandToMode = mapOf(
            "serve" to Mode.SERVER,
            "cli" to Mode.CLI,
        )
    )

    @Test
    fun `detect returns mode for known command`() {
        val mode = detector.detect(arrayOf("serve"))
        assertEquals(Mode.SERVER, mode)
    }

    @Test
    fun `detect ignores options and picks first non-option token`() {
        val mode = detector.detect(arrayOf("--debug", "-Dfoo=bar", "cli", "--other"))
        assertEquals(Mode.CLI, mode)
    }

    @Test
    fun `detect trims tokens and ignores blanks`() {
        val mode = detector.detect(arrayOf("   ", "", "  serve  "))
        assertEquals(Mode.SERVER, mode)
    }

    @Test
    fun `detect throws MissingCommandException when no command provided`() {
        val ex = assertThrows<MissingCommandException> {
            detector.detect(arrayOf("--debug", "   ", "-x"))
        }

        assertEquals(setOf("serve", "cli"), ex.availableCommands.toSet())
    }

    @Test
    fun `detect throws NoSuchCommandException for unknown command`() {
        val ex = assertThrows<NoSuchCommandException> {
            detector.detect(arrayOf("wat"))
        }

        assertEquals("wat", ex.attemptedCommand)
        assertEquals(setOf("serve", "cli"), ex.availableCommands.toSet())
    }

    @Test
    fun `detect supports double-dash end-of-options marker`() {
        assertEquals(Mode.SERVER, detector.detect(arrayOf("--", "serve")))
    }
}