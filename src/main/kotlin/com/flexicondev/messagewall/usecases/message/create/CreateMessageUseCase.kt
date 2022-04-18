package com.flexicondev.messagewall.usecases.message.create

import com.flexicondev.messagewall.domain.Message
import com.flexicondev.messagewall.domain.MessageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CreateMessageUseCase(
    @Autowired val messageRepository: MessageRepository
) {

    fun call(command: CreateMessageCommand): Message =
        messageRepository.save(Message(text = command.text, author = command.author))
}