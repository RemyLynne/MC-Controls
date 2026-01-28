package dev.lynne.mc_controls.bootstrap.profile

import dev.lynne.mc_controls.bootstrap.exception.profile.MutexProfileException
import dev.lynne.mc_controls.bootstrap.exception.profile.ServerControlledProfileException
import dev.lynne.mc_controls.domain.validation.MutexRule
import dev.lynne.mc_controls.spring.ServerType

class ProfilePolicy(
    private val serverControlledProfiles: Set<String> = setOf(*ServerType.entries.map { it() }.toTypedArray()),
    private val mutexProfiles: List<Set<String>> = listOf(
        setOf(*ServerType.entries.map { it() }.toTypedArray())
    ),
) {
    fun check(explicitProfiles: Set<String>, profilesToAdd: Set<String>) {
        for (profile in explicitProfiles)
            if (serverControlledProfiles.contains(profile))
                throw ServerControlledProfileException(profile)

        val mutexViolations = MutexRule.check(explicitProfiles + profilesToAdd, mutexProfiles)
        if (mutexViolations.isNotEmpty())
            throw MutexProfileException(mutexViolations)
    }
}