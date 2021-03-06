package com.flexicondev.messagewall.api.http.mappers

import com.flexicondev.messagewall.api.http.responses.MessageResponse
import com.flexicondev.messagewall.domain.message.Message
import java.time.ZoneOffset

class MessageResponseMapper {
    companion object {
        fun toResponse(message: Message) =
            MessageResponse(
                message.id.toString(),
                message.text,
                message.author,
                message.createdAt.toInstant(ZoneOffset.UTC),
            )
    }
}
