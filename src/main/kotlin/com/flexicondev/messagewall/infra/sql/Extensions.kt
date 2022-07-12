package com.flexicondev.messagewall.infra.sql

import com.flexicondev.messagewall.domain.message.Message
import org.jooq.Result
import org.jooq.generated.tables.records.MessagesRecord

fun Result<MessagesRecord>.toModels(): Collection<Message> = map { it.toModel() }

fun MessagesRecord.toModel(): Message = Message(id, text, author, createdAt)
