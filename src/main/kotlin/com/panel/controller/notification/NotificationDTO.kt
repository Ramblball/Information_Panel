package com.panel.controller.notification

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import java.time.Instant

open class NotificationCreateDTO {
    @NotBlank
    @JsonProperty("text", required = true)
    val text: String? = null
    @JsonProperty("endTime", required = true)
    val endTime: Instant? = null
    @JsonProperty("creator_id", required = true)
    val creator: Long? = null
}

class NotificationIdDTO : NotificationCreateDTO() {
    @JsonProperty("id", required = true)
    val id: Long? = null
}

