package com.panel.repository;

import com.loging.logService.models.NotificationEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Repository
interface NotificationEntityRepository : ContentRepository<NotificationEntity, Long> {

    @Modifying
    @Transactional
    @Query("update NotificationEntity n set n.text = ?1, n.endTime = ?2 where n.id = ?3")
    fun update(text: String, endTime: Instant, id: Long): Int
}