package com.flexicondev.messagewall.infra.sql

import com.flexicondev.messagewall.domain.message.Message
import com.flexicondev.messagewall.domain.message.MessageRepository
import org.jooq.DSLContext
import org.jooq.generated.Tables.MESSAGES
import org.jooq.impl.DSL
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository

@Repository
@Qualifier("sql")
class MessageEntityRepository(val dsl: DSLContext) : MessageRepository {
    override fun findAllDescending(): Collection<Message> =
        select()
            .orderBy(MESSAGES.CREATED_AT.desc())
            .fetchInto(MESSAGES)
            .toModels()

    override fun findBy(id: Int): Message? =
        select()
            .where(MESSAGES.ID.equal(id))
            .fetchOneInto(MESSAGES)
            ?.toModel()

    override fun save(message: Message): Message = if (message.id == null) {
        insertMessage(message)
    } else {
        updateMessage(message)
    }

    override fun existsBy(id: Int): Boolean = dsl.fetchExists(
        dsl.select(DSL.asterisk()).from(MESSAGES).where(MESSAGES.ID.equal(id))
    )

    override fun deleteBy(id: Int) {
        dsl.delete(MESSAGES).where(MESSAGES.ID.equal(id)).execute()
    }

    override fun deleteAll() {
        dsl.delete(MESSAGES).execute()
    }

    private fun columns() = listOf(
        MESSAGES.ID,
        MESSAGES.TEXT,
        MESSAGES.AUTHOR,
        MESSAGES.CREATED_AT,
    )

    private fun select() = dsl.select(columns()).from(MESSAGES)

    private fun insertMessage(message: Message): Message =
        dsl.insertInto(MESSAGES)
            .columns(MESSAGES.TEXT, MESSAGES.AUTHOR, MESSAGES.CREATED_AT)
            .values(message.text, message.author, message.createdAt)
            .returningResult(columns())
            .fetchOneInto(MESSAGES)!!
            .toModel()

    private fun updateMessage(message: Message): Message {
        dsl.update(MESSAGES)
            .set(MESSAGES.TEXT, message.text)
            .set(MESSAGES.AUTHOR, message.author)
            .set(MESSAGES.CREATED_AT, message.createdAt)

        return message
    }
}
