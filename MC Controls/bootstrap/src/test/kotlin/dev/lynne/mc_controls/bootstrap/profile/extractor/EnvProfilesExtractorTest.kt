package dev.lynne.mc_controls.bootstrap.profile.extractor

import kotlin.test.Test
import kotlin.test.assertEquals

class EnvProfilesExtractorTest {
    @Test
    fun `extract returns empty when env var missing`() {
        val ex = EnvProfilesExtractor(env = { emptyMap() })
        assertEquals(emptyList(), ex.extract(arrayOf("--anything")))
    }

    @Test
    fun `extract splits trims and drops empties`() {
        val ex = EnvProfilesExtractor(env = {
            mapOf("SPRING_PROFILES_ACTIVE" to " local, dev , ,prod,, ")
        })

        assertEquals(listOf("local", "dev", "prod"), ex.extract(emptyArray()))
    }
}