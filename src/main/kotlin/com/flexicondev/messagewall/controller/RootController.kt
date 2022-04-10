package com.flexicondev.messagewall.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class RootController {

    @GetMapping
    fun getRoot(): Map<String, String> = mapOf("msg" to "message-wall api")
}