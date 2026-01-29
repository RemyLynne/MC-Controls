package dev.lynne.mc_controls.bootstrap.mode

import org.springframework.boot.SpringApplication

data class ResolvedMode(
    val profilesToAdd: Set<String>,
    val configure: (SpringApplication) -> Unit
)
