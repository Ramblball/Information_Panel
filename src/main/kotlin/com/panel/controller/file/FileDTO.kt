package com.panel.controller.file

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import java.time.Instant
import java.util.*

open class FileCreateDTO {
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

class FileIdDTO : FileCreateDTO() {
    @JsonProperty("id", required = true)
    val id: UUID? = null
}