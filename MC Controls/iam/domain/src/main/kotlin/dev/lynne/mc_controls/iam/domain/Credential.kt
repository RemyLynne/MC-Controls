package dev.lynne.mc_controls.iam.domain

import java.time.Instant

data class Credential(
    val userId: Int? = null,
    var passwordHash: String,
    var expired: Boolean = false,
    var lastChangedAt: Instant = Instant.now()
)