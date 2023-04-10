package com.panel.service.file

import com.loging.logService.exceptions.BadRequestException
import com.loging.logService.exceptions.NotFoundException
import com.loging.logService.models.AlbumEntity
import com.loging.logService.models.FileEntity
import com.loging.logService.repository.FileEntityRepository
import com.loging.logService.service.ContentServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class FileServiceImpl @Autowired constructor(
    private val fileEntityRepository: FileEntityRepository
) : ContentServiceImpl<FileEntity, UUID>(fileEntityRepository) {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun updateContent(entity: FileEntity) {
        entity.id?.let {
            if (fileEntityRepository.update(entity.comment, entity.order, it) == 0) {
                throw NotFoundException()
            }
        } ?: throw BadRequestException()
    }
}