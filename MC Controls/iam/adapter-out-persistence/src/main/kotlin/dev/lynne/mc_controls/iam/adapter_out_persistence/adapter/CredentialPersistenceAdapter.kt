package dev.lynne.mc_controls.iam.adapter_out_persistence.adapter

import dev.lynne.mc_controls.domain.BiMapper
import dev.lynne.mc_controls.iam.adapter_out_persistence.entity.CredentialJpaEntity
import dev.lynne.mc_controls.iam.adapter_out_persistence.repository.CredentialJpaRepository
import dev.lynne.mc_controls.iam.application.port.out.CredentialsPort
import dev.lynne.mc_controls.iam.domain.Credential
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class CredentialPersistenceAdapter(
    private val repository: CredentialJpaRepository,
    private val mapper: BiMapper<Credential, CredentialJpaEntity>
) : CredentialsPort {
    override fun save(credential: Credential): Credential {
        var entity = mapper.mapForward(credential)
        entity = repository.save(entity)
        return mapper.mapBackward(entity)
    }

    override fun get(userId: Int) =
        repository.findById(userId)
            .map { mapper.mapBackward(it) }
            .getOrNull()
}