package dev.lynne.mc_controls.iam.application.port.out

fun interface PasswordEncoder {
    fun encode(password: CharArray): String
}