package com.panel.models

import jakarta.persistence.*
import org.hibernate.Hibernate
import java.time.Instant

@Entity
@Table(name = "notification")
class NotificationEntity(
    @Column(name = "text", nullable = false)
    var text: String,
    @Column(name = "end_time", nullable = false)
    var endTime: Instant,
    @ManyToOne(cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "creator_id")
    var creator: UserEntity? = null
) : Content<Long>() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as NotificationEntity

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(text = $text , endTime = $endTime )"
    }
}
