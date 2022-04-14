package com.flexicondev.messagewall.model

import javax.validation.constraints.NotBlank

data class CreateMessagePayload(
    @get:NotBlank(message = "Text is required")
    val text: String = "",
    @get:NotBlank(message = "Author is required")
    val author: String = "",
)