package com.flexicondev.messagewall.controller

import com.flexicondev.messagewall.model.Message
import com.flexicondev.messagewall.model.CreateMessagePayload
import com.flexicondev.messagewall.service.MessageService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/messages")
class MessageController(
    val messageService: MessageService
) {

    @GetMapping
    fun getMessages(): Collection<Message> = messageService.getMessages()

    @GetMapping("/{id}")
    fun getMessage(@PathVariable id: String): Message = messageService.getMessage(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createMessage(@Valid @RequestBody payload: CreateMessagePayload): Message =
        messageService.createMessage(text = payload.text!!, author = payload.author!!)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteMessage(@PathVariable id: String) = messageService.deleteMessage(id)
}