package dev.lynne.mc_controls.iam.adapter_out_persistence.repository

import dev.lynne.mc_controls.iam.adapter_out_persistence.entity.CredentialJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CredentialJpaRepository : JpaRepository<CredentialJpaEntity, Long>