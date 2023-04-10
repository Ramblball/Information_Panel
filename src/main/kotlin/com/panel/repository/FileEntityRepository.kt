package com.panel.repository;

import com.loging.logService.models.FileEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
interface FileEntityRepository : ContentRepository<FileEntity, UUID> {

    @Transactional
    @Query("select n from FileEntity n where n.album.id = ?1 order by n.created")
    fun findByAlbumIdOrderById(id: Long, pageable: Pageable): Page<FileEntity>

    @Modifying
    @Transactional
    @Query("update FileEntity a set a.comment = ?1, a.order = ?2 where a.id = ?3")
    fun update(comment: String, order: Int, id: UUID): Int
}