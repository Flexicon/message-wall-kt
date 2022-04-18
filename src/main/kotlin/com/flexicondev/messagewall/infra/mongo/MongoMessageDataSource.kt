package com.flexicondev.messagewall.infra.mongo

import com.flexicondev.messagewall.domain.message.Message
import org.springframework.data.mongodb.repository.MongoRepository

interface MongoMessageDataSource : MongoRepository<Message, String>