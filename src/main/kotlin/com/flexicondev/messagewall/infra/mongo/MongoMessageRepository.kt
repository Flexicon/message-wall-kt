package com.flexicondev.messagewall.infra.mongo

import com.flexicondev.messagewall.domain.message.MessageRepository
import com.flexicondev.messagewall.domain.message.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Repository

@Repository
@Qualifier("mongo")
class MongoMessageRepository(
    @Autowired val dataSource: MongoMessageDataSource
) : MessageRepository {

    override fun findAllDescending(): Collection<Message> =
        dataSource.findAll(Sort.by(Sort.Direction.DESC, "timestamp"))

    override fun findBy(id: String): Message? = dataSource.findById(id).orElse(null)

    override fun save(message: Message): Message = dataSource.save(message)

    override fun existsBy(id: String): Boolean = dataSource.existsById(id)

    override fun deleteBy(id: String) = dataSource.deleteById(id)

    override fun deleteAll() = dataSource.deleteAll()
}