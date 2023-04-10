package com.panel.controller.album

import com.panel.controller.ContentController
import com.loging.logService.exceptions.BadRequestException
import com.loging.logService.exceptions.NotFoundException
import com.loging.logService.models.AlbumEntity
import com.loging.logService.service.ContentService
import com.loging.logService.service.album.AlbumServiceImpl
import org.hibernate.exception.ConstraintViolationException
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/album")
class AlbumController @Autowired constructor(
    private val modelMapper: ModelMapper,
    private val albumService: ContentService<AlbumEntity, Long>
) : com.panel.controller.ContentController() {
    @GetMapping("/")
    fun getAlbum(@RequestParam("id") id: Long): ResponseEntity<com.panel.controller.album.AlbumIdDTO> {
        val album = albumService.getContentById(id)
        val albumDTO = convertToAlbumIdDTO(album)
        return ResponseEntity.status(HttpStatus.OK).body(albumDTO)
    }

    @GetMapping("/page")
    fun getAlbum(@RequestParam("creator") creatorId: Long, @RequestParam("page") page: Int): ResponseEntity<Page<com.panel.controller.album.AlbumIdDTO>> {
        val albums = albumService.getContentPage(creatorId, page)
        val albumsDTOs = albums.map { convertToAlbumIdDTO(it) }
        return ResponseEntity.status(HttpStatus.OK).body(albumsDTOs)
    }

    @PostMapping("/create")
    fun saveAlbum(dto: com.panel.controller.album.AlbumCreateDTO): ResponseEntity<Unit> {
        val entity = convertToAlbumEntity(dto)
        albumService.saveContent(entity)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @PutMapping("/edit")
    fun updateAlbum(dto: com.panel.controller.album.AlbumCreateDTO): ResponseEntity<Unit> {
        val entity = convertToAlbumEntity(dto)
        albumService.updateContent(entity)
        return ResponseEntity(HttpStatus.OK)
    }

    @DeleteMapping("/remove")
    fun deleteAlbum(dto: com.panel.controller.album.AlbumIdDTO): ResponseEntity<Unit> {
        val entity = convertToAlbumEntity(dto)
        albumService.deleteContent(entity)
        return ResponseEntity(HttpStatus.OK)
    }

    private fun convertToAlbumIdDTO(entity: AlbumEntity): com.panel.controller.album.AlbumIdDTO {
        return modelMapper.map(entity, com.panel.controller.album.AlbumIdDTO::class.java)
    }

    private fun convertToAlbumEntity(dto: com.panel.controller.album.AlbumIdDTO): AlbumEntity {
        return modelMapper.map(dto, AlbumEntity::class.java)
    }

    private fun convertToAlbumEntity(dto: com.panel.controller.album.AlbumCreateDTO): AlbumEntity {
        return modelMapper.map(dto, AlbumEntity::class.java)
    }
}