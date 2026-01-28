package dev.lynne.mc_controls.bootstrap.profile.extractor

class EnvProfilesExtractor(
    private val env: () -> Map<String, String> = { System.getenv() }
) : ExplicitProfileExtractor {
    override fun extract(args: Array<String>): List<String> =
        (env()["SPRING_PROFILES_ACTIVE"] ?: "")
            .split(',')
            .map { it.trim() }
            .filter { it.isNotEmpty() }
}