package com.flexicondev.messagewall.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/messages")
class MessageController {

    @GetMapping
    fun getMessages(): String = "messages go here"
}
