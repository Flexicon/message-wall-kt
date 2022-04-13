package com.flexicondev.messagewall.controller

import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

data class ErrorResponse(val errors: Map<String, String?>)

@RestControllerAdvice
class ErrorHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequest(e: MethodArgumentNotValidException): ErrorResponse =
        ErrorResponse(e.allErrors.associate { (it as FieldError).field to it.defaultMessage })
}