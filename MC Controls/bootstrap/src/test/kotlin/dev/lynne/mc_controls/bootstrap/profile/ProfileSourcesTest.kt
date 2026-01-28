package dev.lynne.mc_controls.bootstrap.profile

import dev.lynne.mc_controls.bootstrap.profile.extractor.ExplicitProfileExtractor
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.assertEquals

class ProfileSourcesTest {
    val ex1 = mockk<ExplicitProfileExtractor>()
    val ex2 = mockk<ExplicitProfileExtractor>()
    val sources = ProfileSources(listOf(ex1, ex2))

    @Test
    fun `explicitProfiles unions extractor outputs`() {
        val args = arrayOf("--spring.profiles.active=local")

        every { ex1.extract(args) } returns listOf("local", "dev")
        every { ex2.extract(args) } returns listOf("dev", "prod")

        val result = sources.explicitProfiles(args)

        assertEquals(setOf("local", "dev", "prod"), result)

        verify(exactly = 1) { ex1.extract(args) }
        verify(exactly = 1) { ex2.extract(args) }
        confirmVerified(ex1, ex2)
    }

    @Test
    fun `explicitProfiles returns empty set when no extractors return anything`() {
        val args = emptyArray<String>()

        every { ex1.extract(args) } returns emptyList()
        every { ex2.extract(args) } returns emptyList()

        val result = sources.explicitProfiles(args)

        assertEquals(emptySet(), result)
    }
}