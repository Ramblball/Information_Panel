package com.panel.repository;

import com.loging.logService.models.AlbumEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Repository
interface AlbumEntityRepository : ContentRepository<AlbumEntity, Long> {

    @Modifying
    @Transactional
    @Query("update AlbumEntity a set a.name = ?1, a.comment = ?2, a.endTime = ?3 where a.id = ?4")
    fun update(name: String, comment: String, endTime: Instant, id: Long): Int
}