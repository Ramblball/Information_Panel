package com.panel.models

import jakarta.persistence.*
import org.hibernate.Hibernate

@Entity
@Table(name = "user")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
    @Column(name = "username", nullable = false, length = 127)
    var username: String,
    @Column(name = "password", nullable = false, length = 127)
    var password: String,
    @ManyToOne(cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "authorities_id")
    var authorities: AuthoritiesEntity
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as UserEntity

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(username = $username )"
    }
}