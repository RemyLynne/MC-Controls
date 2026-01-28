package dev.lynne.mc_controls.bootstrap.profile.extractor

import kotlin.test.Test
import kotlin.test.assertEquals

class ArgsProfilesExtractorTest {
    private val ex = ArgsProfilesExtractor()

    @Test
    fun `extract returns empty when option missing`() {
        assertEquals(emptyList(), ex.extract(arrayOf("--foo=bar")))
    }

    @Test
    fun `extract reads single profile`() {
        assertEquals(listOf("local"), ex.extract(arrayOf("--spring.profiles.active=local")))
    }

    @Test
    fun `extract splits trims and drops empties`() {
        val args = arrayOf("--spring.profiles.active= local, dev , ,prod,, ")
        assertEquals(listOf("local", "dev", "prod"), ex.extract(args))
    }

    @Test
    fun `extract flattens repeated option occurrences`() {
        val args = arrayOf(
            "--spring.profiles.active=local,dev",
            "--spring.profiles.active=prod"
        )
        assertEquals(listOf("local", "dev", "prod"), ex.extract(args))
    }
}