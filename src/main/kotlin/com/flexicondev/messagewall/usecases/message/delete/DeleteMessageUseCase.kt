package com.flexicondev.messagewall.usecases.message.delete

import com.flexicondev.messagewall.domain.message.MessageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DeleteMessageUseCase(
    @Autowired val messageRepository: MessageRepository
) {

    fun call(id: String) {
        if (!messageRepository.existsBy(id)) throw NoSuchElementException("Message with id $id not found")

        messageRepository.deleteBy(id)
    }
}
