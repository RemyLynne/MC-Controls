package dev.lynne.mc_controls.iam.domain

data class User(
    //0: unset
    val id: Int = 0,
    val username: String,
    var displayName: String,
    var enabled: Boolean = true
)