package com.flexicondev.messagewall.service

import com.flexicondev.messagewall.model.Message
import com.flexicondev.messagewall.model.MessagePayload
import com.flexicondev.messagewall.repository.MessageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class MessageService @Autowired constructor(
    val messageRepository: MessageRepository
) {

    fun getMessages(): Collection<Message> =
        messageRepository.findAll(Sort.by(Sort.Direction.DESC, "timestamp"))

    fun createMessage(payload: MessagePayload): Message =
        messageRepository.save(Message(text = payload.text, author = payload.author))
}