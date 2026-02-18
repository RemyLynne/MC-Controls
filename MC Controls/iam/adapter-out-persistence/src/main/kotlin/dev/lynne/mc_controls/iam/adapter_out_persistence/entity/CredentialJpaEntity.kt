package dev.lynne.mc_controls.iam.adapter_out_persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "iam_credential")
data class CredentialJpaEntity(
    @Id
    @Column(name = "USER_ID", nullable = false, updatable = false)
    val userId: Int = 0,

    @Column(name = "PASSWORD_HASH", nullable = false)
    var passwordHash: String,

    @Column(name = "EXPIRED", nullable = false)
    var expired: Boolean = false,

    @Column(name = "PASSWORD_LAST_CHANGED_AT", nullable = false)
    var lastChangedAt: Instant = Instant.now()
)