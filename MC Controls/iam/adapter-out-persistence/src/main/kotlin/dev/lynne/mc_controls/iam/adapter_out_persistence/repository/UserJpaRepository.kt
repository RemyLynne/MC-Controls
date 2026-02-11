package dev.lynne.mc_controls.iam.adapter_out_persistence.repository

import dev.lynne.mc_controls.iam.adapter_out_persistence.entity.UserJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserJpaRepository : JpaRepository<UserJpaEntity, Long>