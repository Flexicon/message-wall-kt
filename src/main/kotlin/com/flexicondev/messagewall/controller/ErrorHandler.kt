package com.flexicondev.messagewall.controller

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@JsonInclude(Include.NON_NULL)
data class ErrorResponse(
    val errors: Map<String, String?>? = null,
    val message: String? = null,
)

@RestControllerAdvice
class ErrorHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequest(e: MethodArgumentNotValidException): ErrorResponse =
        ErrorResponse(e.allErrors.associate { (it as FieldError).field to it.defaultMessage })

    @ExceptionHandler(NoSuchElementException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFound(e: NoSuchElementException): ErrorResponse =
        ErrorResponse(message = e.message)
}