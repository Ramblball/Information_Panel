package com.panel.controller.file

import com.panel.controller.ContentController
import com.loging.logService.exceptions.BadRequestException
import com.loging.logService.exceptions.NotFoundException
import com.loging.logService.models.AlbumEntity
import com.loging.logService.models.FileEntity
import com.loging.logService.service.ContentService
import com.loging.logService.service.file.FileServiceImpl
import org.hibernate.exception.ConstraintViolationException
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/file")
class FileController @Autowired constructor(
    private val modelMapper: ModelMapper,
    private val fileService: ContentService<FileEntity, UUID>
) : com.panel.controller.ContentController() {
    @GetMapping("/")
    fun getFile(@RequestParam("id") id: UUID): ResponseEntity<FileIdDTO> {
        val file = fileService.getContentById(id)
        val fileDTO = convertToFileIdDTO(file)
        return ResponseEntity.status(HttpStatus.OK).body(fileDTO)
    }

    @GetMapping("/page")
    fun getFile(@RequestParam("creator") creatorId: UUID, @RequestParam("page") page: Int): ResponseEntity<Page<FileIdDTO>> {
        val files = fileService.getContentPage(creatorId, page)
        val filesDTOs = files.map { convertToFileIdDTO(it) }
        return ResponseEntity.status(HttpStatus.OK).body(filesDTOs)
    }

    @PostMapping("/create")
    fun saveFile(dto: FileCreateDTO): ResponseEntity<Unit> {
        val entity = convertToFileEntity(dto)
        fileService.saveContent(entity)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @PutMapping("/edit")
    fun updateFile(dto: FileCreateDTO): ResponseEntity<Unit> {
        val entity = convertToFileEntity(dto)
        fileService.updateContent(entity)
        return ResponseEntity(HttpStatus.OK)
    }

    @DeleteMapping("/remove")
    fun deleteFile(dto: FileIdDTO): ResponseEntity<Unit> {
        val entity = convertToFileEntity(dto)
        fileService.deleteContent(entity)
        return ResponseEntity(HttpStatus.OK)
    }

    private fun convertToFileIdDTO(entity: FileEntity): FileIdDTO {
        return modelMapper.map(entity, FileIdDTO::class.java)
    }

    private fun convertToFileEntity(dto: FileIdDTO): FileEntity {
        return modelMapper.map(dto, FileEntity::class.java)
    }

    private fun convertToFileEntity(dto: FileCreateDTO): FileEntity {
        return modelMapper.map(dto, FileEntity::class.java)
    }
}