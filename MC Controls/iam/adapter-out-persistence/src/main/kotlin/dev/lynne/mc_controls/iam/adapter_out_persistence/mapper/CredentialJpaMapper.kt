package dev.lynne.mc_controls.iam.adapter_out_persistence.mapper

import dev.lynne.mc_controls.domain.BiMapper
import dev.lynne.mc_controls.iam.adapter_out_persistence.entity.CredentialJpaEntity
import dev.lynne.mc_controls.iam.domain.Credential
import dev.lynne.mc_controls.iam.domain.User
import dev.lynne.mc_controls.spring.annotations.Mapper

@Mapper
class CredentialJpaMapper : BiMapper<Credential, CredentialJpaEntity> {
    override fun mapForward(param: Credential): CredentialJpaEntity {
        return CredentialJpaEntity(
            param.userId,
            param.passwordHash,
            param.expired,
            param.lastChangedAt
        )
    }

    override fun mapBackward(param: CredentialJpaEntity): Credential {
        return Credential(
            param.userId,
            param.passwordHash,
            param.expired,
            param.lastChangedAt
        )
    }
}