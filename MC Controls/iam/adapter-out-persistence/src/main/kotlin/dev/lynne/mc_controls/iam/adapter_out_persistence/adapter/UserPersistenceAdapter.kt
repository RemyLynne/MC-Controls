package dev.lynne.mc_controls.iam.adapter_out_persistence.adapter

import dev.lynne.mc_controls.domain.BiMapper
import dev.lynne.mc_controls.iam.adapter_out_persistence.entity.UserJpaEntity
import dev.lynne.mc_controls.iam.adapter_out_persistence.repository.UserJpaRepository
import dev.lynne.mc_controls.iam.application.port.out.UsersPort
import dev.lynne.mc_controls.iam.domain.User
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class UserPersistenceAdapter(
    private val repository: UserJpaRepository,
    private val mapper: BiMapper<User, UserJpaEntity>
) : UsersPort {
    override fun save(user: User): User {
        var entity = mapper.mapForward(user)
        entity = repository.save(entity)
        return mapper.mapBackward(entity)
    }

    override fun deleteByUsername(username: String) {
        val entity = repository.findByUsername(username)
        if (entity.isPresent) {
            repository.delete(entity.get())
        }
    }

    override fun getAll() =
        repository.findAll()
            .map { mapper.mapBackward(it) }

    override fun get(username: String) =
        repository.findByUsername(username)
            .map { mapper.mapBackward(it) }
            .getOrNull()
}