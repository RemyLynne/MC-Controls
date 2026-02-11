package dev.lynne.mc_controls.iam.adapter_out_persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "iam_user")
data class UserJpaEntity(
    @Id
    @GeneratedValue(GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, insertable = false)
    val id: Int? = null,

    @Column(name = "USERNAME", nullable = false, updatable = false, unique = true)
    val username: String,

    @Column(name = "DISPLAYNAME", nullable = false, updatable = false, unique = true)
    var displayName: String,

    @Column(name = "ENABLED", nullable = false)
    var enabled: Boolean = true,

    @Column(name = "CREATED_AT", nullable = false, updatable = false, insertable = false)
    val createdAt: Instant? = null,

    @Column(name = "UPDATED_AT", nullable = false, updatable = false, insertable = false)
    var updatedAt: Instant? = null
)