package com.panel.service.notification

import com.loging.logService.exceptions.BadRequestException
import com.loging.logService.exceptions.NotFoundException
import com.loging.logService.models.FileEntity
import com.loging.logService.models.NotificationEntity
import com.loging.logService.repository.NotificationEntityRepository
import com.loging.logService.service.ContentServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class NotificationServiceImpl @Autowired constructor (
    private val notificationEntityRepository: NotificationEntityRepository
) : ContentServiceImpl<NotificationEntity, Long>(notificationEntityRepository) {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun updateContent(entity: NotificationEntity) {
        entity.id?.let {
            if (notificationEntityRepository.update(entity.text, entity.endTime, it) == 0) {
                throw NotFoundException()
            }
        } ?: throw BadRequestException()
    }
}
