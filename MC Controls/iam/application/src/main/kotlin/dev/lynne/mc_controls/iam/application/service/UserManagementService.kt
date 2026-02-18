package dev.lynne.mc_controls.iam.application.service

import dev.lynne.mc_controls.iam.application.port.`in`.UserManagementUseCase
import dev.lynne.mc_controls.iam.application.port.out.CredentialsPort
import dev.lynne.mc_controls.iam.application.port.out.PasswordEncoder
import dev.lynne.mc_controls.iam.application.port.out.UsersPort
import dev.lynne.mc_controls.iam.domain.Credential
import dev.lynne.mc_controls.iam.domain.User
import dev.lynne.mc_controls.iam.domain.exception.NoCredentialsException
import dev.lynne.mc_controls.iam.domain.exception.NoSuchUserException

class UserManagementService(
    private val userStore: UsersPort,
    private val credentialStore: CredentialsPort,
    private val encoder: PasswordEncoder
) : UserManagementUseCase {
    override fun create(username: String, displayname: String) =
        userStore.save(User(
            username = username,
            displayName = displayname
        ))

    override fun delete(username: String) =
        userStore.deleteByUsername(username)

    override fun list() =
        userStore.getAll()

    override fun get(username: String) =
        userStore.get(username)

    @Throws(NoSuchUserException::class)
    override fun getCredentials(username: String): Credential? {
        val user = userStore.get(username) ?: throw NoSuchUserException(username)
        return credentialStore.get(user.id)
    }

    @Throws(NoSuchUserException::class)
    override fun setStatus(username: String, enabled: Boolean) {
        val user = userStore.get(username) ?: throw NoSuchUserException(username)
        user.enabled = enabled
        userStore.save(user)
    }

    @Throws(NoSuchUserException::class)
    override fun credentialsReset(username: String, password: CharArray) {
        val user = userStore.get(username) ?: throw NoSuchUserException(username)
        credentialStore.save(Credential(
            user.id,
            encoder.encode(password)
        ))
    }

    @Throws(NoSuchUserException::class, NoCredentialsException::class)
    override fun credentialsExpire(username: String) {
        val user = userStore.get(username) ?: throw NoSuchUserException(username)
        val credentials = credentialStore.get(user.id) ?: throw NoCredentialsException(username)
        credentials.expired = true
        credentialStore.save(credentials)
    }
}