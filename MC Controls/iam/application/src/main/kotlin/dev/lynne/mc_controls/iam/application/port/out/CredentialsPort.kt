package dev.lynne.mc_controls.iam.application.port.out

import dev.lynne.mc_controls.iam.domain.Credential

interface CredentialsPort {
    fun save(credential: Credential): Credential
    fun get(userId: Int): Credential?
}