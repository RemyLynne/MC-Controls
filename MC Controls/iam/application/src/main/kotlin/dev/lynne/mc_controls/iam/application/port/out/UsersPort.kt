package dev.lynne.mc_controls.iam.application.port.out

import dev.lynne.mc_controls.iam.domain.User

interface UsersPort {
    fun save(user: User): User
    fun deleteByUsername(username: String)
    fun getAll(): List<User>
    fun get(username: String): User?
}