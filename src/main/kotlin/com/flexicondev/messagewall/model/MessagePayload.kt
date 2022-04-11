package com.flexicondev.messagewall.model

data class MessagePayload(val text: String, val author: String) {
    init {
        require(text.isNotBlank()) { "Text is required" }
        require(author.isNotBlank()) { "Author is required" }
    }
}