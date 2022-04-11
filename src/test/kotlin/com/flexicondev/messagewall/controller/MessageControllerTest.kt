package com.flexicondev.messagewall.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.flexicondev.messagewall.model.MessagePayload
import com.flexicondev.messagewall.repository.MessageRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
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

    @Nested
    @DisplayName("GET /messages")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetMessages {
        @Test
        fun `should return empty list of messages`() {
            mockMvc.get(baseUrl)
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$") { isArray() }
                    jsonPath("$") { isEmpty() }
                }
        }
    }

    @Nested
    @DisplayName("POST /messages")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class CreateMessage {
        @Test
        fun `should create a new message`() {
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

        @Nested
        @DisplayName("Invalid payload")
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        inner class PostInvalid {

            @Test
            fun `should fail an empty payload`() {
                val payload = "{}"

                mockMvc.post(baseUrl) {
                    contentType = MediaType.APPLICATION_JSON
                    content = payload
                    accept = MediaType.APPLICATION_JSON
                }
                    .andExpect {
                        status { isBadRequest() }
                    }
            }

            @Test
            fun `should fail without text field`() {
                val payload = mapOf("author" to "John", "text" to "")

                mockMvc.post(baseUrl) {
                    contentType = MediaType.APPLICATION_JSON
                    content = objectMapper.writeValueAsString(payload)
                    accept = MediaType.APPLICATION_JSON
                }
                    .andExpect {
                        status { isBadRequest() }
                        content { string("Text is required") }
                    }
            }
        }
    }
}