package com.flexicondev.messagewall.api.http.responses

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant

data class MessageResponse(
    val id: String,
    val text: String,
    val author: String,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mmZ", timezone = "UTC")
    val timestamp: Instant,
)
