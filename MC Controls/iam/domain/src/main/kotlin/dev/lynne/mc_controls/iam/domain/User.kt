package dev.lynne.mc_controls.iam.domain

data class User(
    val id: Int? = null,
    val username: String,
    var displayName: String,
    var enabled: Boolean
)