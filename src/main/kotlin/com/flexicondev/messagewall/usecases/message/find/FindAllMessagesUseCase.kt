package com.flexicondev.messagewall.usecases.message.find

import com.flexicondev.messagewall.domain.Message
import com.flexicondev.messagewall.domain.MessageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FindAllMessagesUseCase(
    @Autowired val messageRepository: MessageRepository
) {

    fun call(): Collection<Message> = messageRepository.findAllDescending()
}