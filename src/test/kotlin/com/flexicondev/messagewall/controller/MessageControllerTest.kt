package com.flexicondev.messagewall.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.flexicondev.messagewall.model.MessagePayload
import com.flexicondev.messagewall.repository.MessageRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
internal class MessageControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var messageRepository: MessageRepository

    val baseUrl = "/messages"

    @BeforeEach
    fun beforeEach() {
        messageRepository.deleteAll()
    }

    @Test
    fun `GET should return empty list of messages`() {
        mockMvc.get(baseUrl)
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$") { isArray() }
                jsonPath("$") { isEmpty() }
            }
    }

    @Test
    fun `POST should create a new message`() {
        val payload = MessagePayload("Test message", "Johnny Test")

        mockMvc.post(baseUrl) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(payload)
            accept = MediaType.APPLICATION_JSON
        }
            .andExpect {
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.text") { value(payload.text) }
                jsonPath("$.author") { value(payload.author) }
            }
    }
}