package com.panel.service.album

import com.loging.logService.exceptions.BadRequestException
import com.loging.logService.exceptions.NotFoundException
import com.loging.logService.models.AlbumEntity
import com.loging.logService.repository.AlbumEntityRepository
import com.loging.logService.service.ContentServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class AlbumServiceImpl @Autowired constructor(
    private val albumEntityRepository: AlbumEntityRepository
) : ContentServiceImpl<AlbumEntity, Long>(albumEntityRepository) {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun updateContent(entity: AlbumEntity) {
        entity.id?.let {
            if (albumEntityRepository.update(entity.name, entity.comment, entity.endTime, it) == 0) {
                throw NotFoundException()
            }
        } ?: throw BadRequestException()
    }
}