package com.flexicondev.messagewall.infra.mongo

import com.flexicondev.messagewall.domain.Message
import org.springframework.data.mongodb.repository.MongoRepository

interface MongoMessageDataSource : MongoRepository<Message, String>