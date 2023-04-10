package com.panel.models

import jakarta.persistence.*
import org.hibernate.Hibernate
import java.time.Instant
import java.util.*

@Entity
@Table(name = "file")
class FileEntity(
    @Column(name = "original_name", nullable = false, length = 255)
    var originalName: String,
    @Column(name = "file_format", nullable = false, length = 7)
    var fileFormat: String,
    @Column(name = "file_type", nullable = false)
    var fileType: Boolean,
    @Column(name = "comment", nullable = false)
    var comment: String,
    @Column(name = "order", nullable = false)
    var order: Int,
    @Column(name = "removed", nullable = false)
    var removed: Boolean = false,
    @ManyToOne
    @JoinColumn(name = "album_id")
    var album: AlbumEntity? = null,
    @ManyToOne(cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "creator_id", nullable = false)
    var creator: UserEntity
) : Content<UUID>() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as FileEntity

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(fileName = $id , originalName = $originalName , fileFormat = $fileFormat , fileType = $fileType , comment = $comment )"
    }
}
