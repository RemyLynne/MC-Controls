package dev.lynne.mc_controls.iam.adapter_out_persistence.mapper

import dev.lynne.mc_controls.domain.BiMapper
import dev.lynne.mc_controls.iam.adapter_out_persistence.entity.UserJpaEntity
import dev.lynne.mc_controls.iam.domain.User
import dev.lynne.mc_controls.spring.annotations.Mapper

@Mapper
class UserJpaMapper : BiMapper<User, UserJpaEntity> {
    override fun mapForward(param: User): UserJpaEntity {
        return UserJpaEntity(
            param.id,
            param.username,
            param.displayName,
            param.enabled
        )
    }

    override fun mapBackward(param: UserJpaEntity): User {
        return User(
            param.id,
            param.username,
            param.displayName,
            param.enabled
        )
    }
}