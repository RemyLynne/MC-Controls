package dev.lynne.mc_controls.bootstrap.profile

import dev.lynne.mc_controls.bootstrap.profile.extractor.ArgsProfilesExtractor
import dev.lynne.mc_controls.bootstrap.profile.extractor.EnvProfilesExtractor
import dev.lynne.mc_controls.bootstrap.profile.extractor.ExplicitProfileExtractor

class ProfileSources(
    private val extractors: List<ExplicitProfileExtractor> = listOf(
        ArgsProfilesExtractor(),
        EnvProfilesExtractor()
    )
) {
    fun explicitProfiles(args: Array<String>): Set<String> {
        return extractors.flatMap { it.extract(args) }.toSet()
    }
}