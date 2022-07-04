package com.flexicondev.messagewall.usecases.message.create

import com.flexicondev.messagewall.api.http.requests.CreateMessageRequest

data class CreateMessageCommand(val text: String, val author: String) {
    companion object {
        fun fromRequest(request: CreateMessageRequest) =
            CreateMessageCommand(request.text, request.author)
    }
}
