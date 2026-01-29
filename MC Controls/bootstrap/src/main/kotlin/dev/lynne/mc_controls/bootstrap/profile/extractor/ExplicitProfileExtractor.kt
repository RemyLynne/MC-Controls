package dev.lynne.mc_controls.bootstrap.profile.extractor

interface ExplicitProfileExtractor {
    fun extract(args: Array<String>): List<String>
}