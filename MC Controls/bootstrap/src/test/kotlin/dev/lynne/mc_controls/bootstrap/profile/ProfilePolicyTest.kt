package dev.lynne.mc_controls.bootstrap.profile

import dev.lynne.mc_controls.bootstrap.exception.profile.MutexProfileException
import dev.lynne.mc_controls.bootstrap.exception.profile.ServerControlledProfileException
import dev.lynne.mc_controls.domain.validation.MutexRuleViolation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class ProfilePolicyTest {
    @Test
    fun `check throws when explicit contains server-controlled profile`() {
        val profile = "prod"
        val policy = ProfilePolicy(
            serverControlledProfiles = setOf(profile),
            mutexProfiles = emptyList()
        )

        val ex = assertThrows<ServerControlledProfileException> {
            policy.check(explicitProfiles = setOf(profile), profilesToAdd = emptySet())
        }

        assertEquals(profile, ex.profile)
    }

    @Test
    fun `check does not throw when server-controlled is only in profilesToAdd`() {
        val policy = ProfilePolicy(
            serverControlledProfiles = setOf("prod"),
            mutexProfiles = emptyList()
        )

        policy.check(explicitProfiles = setOf("local"), profilesToAdd = setOf("prod"))
    }

    @Test
    fun `check throws when mutex rules violated across explicit and added`() {
        val policy = ProfilePolicy(
            serverControlledProfiles = emptySet(),
            mutexProfiles = listOf(setOf("dev", "prod"))
        )

        val ex = assertThrows<MutexProfileException> {
            policy.check(explicitProfiles = setOf("dev"), profilesToAdd = setOf("prod"))
        }


        assertEquals(
            listOf(MutexRuleViolation(
                setOf("dev", "prod"),
                setOf("dev", "prod")
            )),
            ex.violations
        )
    }

    @Test
    fun `check passes when no server-controlled and no mutex violations`() {
        val policy = ProfilePolicy(
            serverControlledProfiles = setOf("prod"),
            mutexProfiles = listOf(setOf("dev", "prod"))
        )

        policy.check(explicitProfiles = setOf("local"), profilesToAdd = setOf("dev"))
    }
}