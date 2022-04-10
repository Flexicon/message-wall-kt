package com.flexicondev.messagewall.controller

import com.flexicondev.messagewall.model.Message
import com.flexicondev.messagewall.repository.MessageRepository
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/messages")
class MessageController(
    val messageRepository: MessageRepository
) {

    @GetMapping
    fun getMessages(): Collection<Message> =
        messageRepository.findAll(Sort.by(Sort.Direction.DESC, "timestamp"))
}