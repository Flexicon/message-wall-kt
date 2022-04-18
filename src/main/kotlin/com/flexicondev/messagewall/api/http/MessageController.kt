package com.flexicondev.messagewall.api.http

import com.flexicondev.messagewall.api.http.mappers.MessageResponseMapper
import com.flexicondev.messagewall.api.http.requests.CreateMessageRequest
import com.flexicondev.messagewall.api.http.responses.MessageResponse
import com.flexicondev.messagewall.usecases.message.create.CreateMessageCommand
import com.flexicondev.messagewall.usecases.message.create.CreateMessageUseCase
import com.flexicondev.messagewall.usecases.message.delete.DeleteMessageUseCase
import com.flexicondev.messagewall.usecases.message.find.FindAllMessagesUseCase
import com.flexicondev.messagewall.usecases.message.find.FindMessageUseCase
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
    val findAllUseCase: FindAllMessagesUseCase,
    val findUseCase: FindMessageUseCase,
    val createUseCase: CreateMessageUseCase,
    val deleteUseCase: DeleteMessageUseCase,
) {

    @GetMapping
    fun getMessages(): Collection<MessageResponse> =
        findAllUseCase.call().map(MessageResponseMapper::toResponse)

    @GetMapping("/{id}")
    fun getMessage(@PathVariable id: String): MessageResponse =
        MessageResponseMapper.toResponse(findUseCase.call(id))

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createMessage(@Valid @RequestBody request: CreateMessageRequest): MessageResponse =
        MessageResponseMapper.toResponse(
            createUseCase.call(CreateMessageCommand.fromRequest(request))
        )

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteMessage(@PathVariable id: String) = deleteUseCase.call(id)
}