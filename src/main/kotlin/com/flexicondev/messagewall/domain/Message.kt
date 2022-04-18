package com.flexicondev.messagewall.domain

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "messages")
data class Message(
    @Id val id: ObjectId = ObjectId.get(),
    val text: String,
    val author: String,
    val timestamp: Instant = Instant.now(),
)