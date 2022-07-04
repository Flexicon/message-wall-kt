package com.flexicondev.messagewall

import org.springframework.stereotype.Component
import org.testcontainers.containers.MongoDBContainer
import javax.annotation.PreDestroy

@Component
class TestMongoDBContainer : MongoDBContainer("mongo:5.0") {

    init {
        this.start()
    }

    @PreDestroy
    fun destroy() {
        stop()
    }
}
