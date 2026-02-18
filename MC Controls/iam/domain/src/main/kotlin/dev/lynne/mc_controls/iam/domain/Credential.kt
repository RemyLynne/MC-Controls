package dev.lynne.mc_controls.iam.domain

import java.time.Instant

data class Credential(
    //0: unset
    val userId: Int = 0,
    var passwordHash: String,
    var expired: Boolean = false,
    var lastChangedAt: Instant = Instant.now()
)