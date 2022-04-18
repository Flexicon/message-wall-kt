package com.flexicondev.messagewall.usecases.message.find

import com.flexicondev.messagewall.domain.Message
import com.flexicondev.messagewall.domain.MessageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FindMessageUseCase(
    @Autowired val messageRepository: MessageRepository
) {

    fun call(id: String): Message =
        messageRepository.findBy(id) ?: throw NoSuchElementException("Message with id $id not found")
}