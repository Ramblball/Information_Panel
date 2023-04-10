package com.panel.controller.album

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import java.time.Instant

open class AlbumCreateDTO {
    @NotBlank
    @JsonProperty("name", required = true)
    val name: String? = null
    @JsonProperty("comment", required = true)
    val comment: String? = null
    @JsonProperty("endTime", required = true)
    val endTime: Instant? = null
    @JsonProperty("creator_id", required = true)
    val creator: Long? = null
}

class AlbumIdDTO : AlbumCreateDTO() {
    @JsonProperty("id", required = true)
    val id: Long? = null
}