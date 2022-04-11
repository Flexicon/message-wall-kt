package com.flexicondev.messagewall.controller

import com.flexicondev.messagewall.model.Message
import com.flexicondev.messagewall.model.MessagePayload
import com.flexicondev.messagewall.service.MessageService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/messages")
class MessageController(
    val messageService: MessageService
) {
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @GetMapping
    fun getMessages(): Collection<Message> = messageService.getMessages()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createMessage(@RequestBody payload: MessagePayload): Message =
        messageService.createMessage(payload)
}