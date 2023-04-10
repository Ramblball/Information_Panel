package com.panel.models

import jakarta.persistence.*
import java.time.Instant

@MappedSuperclass
abstract class Content<T>(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: T? = null
) {
    @Column(name = "hide", nullable = false)
    var hide: Boolean? = null
    @Column(name = "archived", nullable = false)
    var archived: Boolean? = null
    @Column(name = "created", nullable = false)
    var created: Instant? = null
}