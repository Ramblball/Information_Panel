package com.panel.repository

import com.loging.logService.models.Content
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.transaction.annotation.Transactional

@NoRepositoryBean
interface ContentRepository<E : Content<ID>, ID> : CrudRepository<E, ID> {

    @Transactional
    @Query("select a from #{#entityName} a where a.creator.id = ?1 order by a.created")
    fun findByCreatorIdOrderByCreated(id: ID, pageable: Pageable): Page<E>

    @Modifying
    @Transactional
    @Query("update #{#entityName} a set a.hide = ?1 where a.id = ?2")
    fun updateHideById(hide: Boolean, id: ID): Int

    @Modifying
    @Transactional
    @Query("update #{#entityName} a set a.archived = ?1 where a.id = ?2")
    fun updateArchivedById(archived: Boolean, id: ID): Int
}