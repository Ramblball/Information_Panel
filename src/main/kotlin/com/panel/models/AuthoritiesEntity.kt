package com.panel.models

import jakarta.persistence.*
import org.hibernate.Hibernate

@Entity
@Table(name = "authorities")
data class AuthoritiesEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int? = null,
    @Column(name = "name", nullable = false, length = 15)
    var name: String,
    @Column(name = "view", nullable = false)
    var view: Boolean = false,
    @Column(name = "create", nullable = false)
    var create: Boolean = false,
    @Column(name = "edit", nullable = false)
    var edit: Boolean = false,
    @Column(name = "remove", nullable = false)
    var remove: Boolean = false,
    @Column(name = "administrate", nullable = false)
    var administrate: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as AuthoritiesEntity

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , name = $name )"
    }
}