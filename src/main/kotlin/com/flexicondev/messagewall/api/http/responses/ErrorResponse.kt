package com.flexicondev.messagewall.api.http.responses

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include

@JsonInclude(Include.NON_NULL)
data class ErrorResponse(
    val errors: Map<String, String?>? = null,
    val message: String? = null,
)