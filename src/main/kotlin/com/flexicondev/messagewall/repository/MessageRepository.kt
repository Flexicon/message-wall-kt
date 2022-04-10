package com.flexicondev.messagewall.repository

import com.flexicondev.messagewall.model.Message
import org.springframework.data.mongodb.repository.MongoRepository

interface MessageRepository : MongoRepository<Message, String>