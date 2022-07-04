package com.flexicondev.messagewall.domain.message

interface MessageRepository {

    fun findAllDescending(): Collection<Message>

    fun findBy(id: String): Message?

    fun save(message: Message): Message

    fun existsBy(id: String): Boolean

    fun deleteBy(id: String)

    fun deleteAll()
}
