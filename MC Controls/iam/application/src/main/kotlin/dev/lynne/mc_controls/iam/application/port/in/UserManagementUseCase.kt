package dev.lynne.mc_controls.iam.application.port.`in`

import dev.lynne.mc_controls.iam.domain.Credential
import dev.lynne.mc_controls.iam.domain.User
import dev.lynne.mc_controls.iam.domain.exception.NoCredentialsException
import dev.lynne.mc_controls.iam.domain.exception.NoSuchUserException
import kotlin.jvm.Throws

interface UserManagementUseCase {
    fun create(username: String, displayname: String): User
    fun delete(username: String)
    fun list(): List<User>
    fun get(username: String): User?
    @Throws(NoSuchUserException::class)
    fun getCredentials(username: String): Credential?
    @Throws(NoSuchUserException::class)
    fun setStatus(username: String, enabled: Boolean)
    @Throws(NoSuchUserException::class)
    fun credentialsReset(username: String, password: CharArray)
    @Throws(NoSuchUserException::class, NoCredentialsException::class)
    fun credentialsExpire(username: String)
}