package com.panel.models

import jakarta.persistence.*
import org.hibernate.Hibernate
import java.time.Instant

@Entity
@Table(name = "album")
class AlbumEntity(
    @Column(name = "name", nullable = false, length = 127)
    var name: String,
    @Column(name = "comment", nullable = false)
    var comment: String,
    @Column(name = "end_time", nullable = false)
    var endTime: Instant,
    @ManyToOne(cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "creator_id")
    var creator: UserEntity
) : Content<Long>() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as AlbumEntity

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(name = $name , comment = $comment , endTime = $endTime , hide = $hide , archived = $archived , creator = $creator )"
    }
}

@Entity
data class AlbumView(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,
    @Column(name = "name", nullable = false, length = 127)
    var name: String,
    @Column(name = "comment", nullable = false)
    var comment: String,
    @Column(name = "end_time", nullable = false)
    var endTime: Instant,
    @ManyToOne(cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "creator_id")
    var creator: UserEntity
)
