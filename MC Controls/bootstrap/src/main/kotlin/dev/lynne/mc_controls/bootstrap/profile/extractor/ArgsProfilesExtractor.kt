package dev.lynne.mc_controls.bootstrap.profile.extractor

import org.springframework.boot.DefaultApplicationArguments
import kotlin.collections.orEmpty

class ArgsProfilesExtractor : ExplicitProfileExtractor {
    override fun extract(args: Array<String>): List<String> =
        DefaultApplicationArguments(*args)
            .getOptionValues("spring.profiles.active")
            ?.flatMap { it.split(',') }
            ?.map { it.trim() }
            ?.filter { it.isNotEmpty() }
            .orEmpty()
}