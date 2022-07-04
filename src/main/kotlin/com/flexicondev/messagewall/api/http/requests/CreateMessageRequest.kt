package com.flexicondev.messagewall.api.http.requests

import javax.validation.constraints.NotBlank

data class CreateMessageRequest(
    @get:NotBlank(message = "Text is required")
    val text: String = "",
    @get:NotBlank(message = "Author is required")
    val author: String = "",
)
