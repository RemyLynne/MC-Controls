package dev.lynne.mc_controls.domain.validation

data class MutexRuleViolation(
    val incompatible: Set<String>,
    val mutex: Set<String>,
)
