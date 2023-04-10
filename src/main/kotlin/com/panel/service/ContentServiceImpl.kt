package com.panel.service

import com.loging.logService.exceptions.BadRequestException
import com.loging.logService.exceptions.NotFoundException
import com.loging.logService.models.Content
import com.loging.logService.repository.ContentRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

abstract class ContentServiceImpl<E : Content<ID>, ID : Any> (
    private val repository: ContentRepository<E, ID>
) : ContentService<E, ID>{

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    override fun getContentPage(creatorId: ID, page: Int): Page<E> {
        return repository.findByCreatorIdOrderByCreated(creatorId, PageRequest.of(page, 15))
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    override fun getContentById(contentId: ID): E {
        return repository.findById(contentId).orElseThrow { throw NotFoundException() }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun saveContent(entity: E) {
        repository.save(entity)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun markContentAsHide(contentId: ID) {
        repository.updateHideById(true, contentId)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun markContentAsUnHide(contentId: ID) {
        repository.updateHideById(false, contentId)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun markContentAsArchived(contentId: ID) {
        repository.updateArchivedById(true, contentId)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun markContentAsUnArchived(contentId: ID) {
        repository.updateArchivedById(false, contentId)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun deleteContent(entity: E) {
        entity.id?.let {
            val savedEntity = repository.findById(it)
            if (savedEntity.isPresent) {
                repository.deleteById(it)
            } else {
                throw NotFoundException()
            }
        } ?: throw BadRequestException()
    }
}