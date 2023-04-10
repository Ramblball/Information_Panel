package com.panel.controller

import com.loging.logService.exceptions.BadRequestException
import com.loging.logService.exceptions.NotFoundException
import org.hibernate.exception.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler

open class ContentController {
    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<String?> {
        return if (ex.cause is ConstraintViolationException || ex is BadRequestException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body<String?>("Invalid data")
        } else if (ex is NotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body<String?>("Data not found")
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body<String?>("Unexpected exception")
        }
    }
}