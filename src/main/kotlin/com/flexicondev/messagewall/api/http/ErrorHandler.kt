package com.flexicondev.messagewall.api.http

import com.flexicondev.messagewall.api.http.responses.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ErrorHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequest(e: MethodArgumentNotValidException): ErrorResponse =
        ErrorResponse(
            errors = e.allErrors.associate { (it as FieldError).field to it.defaultMessage },
            message = "Invalid payload",
        )

    @ExceptionHandler(NoSuchElementException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFound(e: NoSuchElementException): ErrorResponse =
        ErrorResponse(message = e.message)
}
