package com.panel.controller.notification

import com.panel.controller.ContentController
import com.loging.logService.exceptions.NotFoundException
import com.loging.logService.models.AlbumEntity
import com.loging.logService.models.NotificationEntity
import com.loging.logService.service.ContentService
import com.loging.logService.service.notification.NotificationServiceImpl
import org.hibernate.exception.ConstraintViolationException
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/notification")
class NotificationController @Autowired constructor (
    private val modelMapper: ModelMapper,
    private val notificationService: ContentService<NotificationEntity, Long>
) : com.panel.controller.ContentController() {
    @GetMapping("/")
    fun getNotification(@RequestParam("id") id: Long): ResponseEntity<NotificationIdDTO> {
        val notification = notificationService.getContentById(id)
        return ResponseEntity.status(HttpStatus.OK).body(convertToNotificationIdDTO(notification))
    }

    @GetMapping("/page")
    fun getNotification(@RequestParam("creator") creatorId: Long, @RequestParam("page") page: Int): ResponseEntity<Page<NotificationIdDTO>> {
        val notifications = notificationService.getContentPage(creatorId, page)
        val notificationDTOs = notifications.map { convertToNotificationIdDTO(it) }
        return ResponseEntity.status(HttpStatus.OK).body(notificationDTOs)
    }

    @PostMapping("/create")
    fun saveNotification(dto: NotificationCreateDTO): ResponseEntity<Unit> {
        val entity = convertToNotificationEntity(dto)
        notificationService.saveContent(entity)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @PutMapping("/edit")
    fun updateNotification(dto: NotificationCreateDTO): ResponseEntity<Unit> {
        val entity = convertToNotificationEntity(dto)
        notificationService.updateContent(entity)
        return ResponseEntity(HttpStatus.OK)
    }

    @DeleteMapping("/remove")
    fun deleteNotification(dto: NotificationIdDTO): ResponseEntity<Unit> {
        val entity = convertToNotificationEntity(dto)
        notificationService.deleteContent(entity)
        return ResponseEntity(HttpStatus.OK)
    }

    private fun convertToNotificationIdDTO(entity: NotificationEntity): NotificationIdDTO {
        return modelMapper.map(entity, NotificationIdDTO::class.java)
    }

    private fun convertToNotificationEntity(dto: NotificationIdDTO): NotificationEntity {
        return modelMapper.map(dto, NotificationEntity::class.java)
    }

    private fun convertToNotificationEntity(dto: NotificationCreateDTO): NotificationEntity {
        return modelMapper.map(dto, NotificationEntity::class.java)
    }
}