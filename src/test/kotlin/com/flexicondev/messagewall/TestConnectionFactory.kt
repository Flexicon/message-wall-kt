package com.flexicondev.messagewall

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory

@Configuration
class TestConnectionFactory {

    @Bean
    fun mongoDatabaseFactory(container: TestMongoDBContainer): MongoDatabaseFactory =
        SimpleMongoClientDatabaseFactory(container.replicaSetUrl)
}
