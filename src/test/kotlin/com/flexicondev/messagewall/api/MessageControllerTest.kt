package com.flexicondev.messagewall.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.flexicondev.messagewall.api.http.mappers.MessageResponseMapper
import com.flexicondev.messagewall.api.http.requests.CreateMessageRequest
import com.flexicondev.messagewall.domain.message.Message
import com.flexicondev.messagewall.domain.message.MessageRepository
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
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
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
            val payload = CreateMessageRequest("Test message", "Johnny Test")

            // Create message
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

            // Check that message was created
            mockMvc.get(baseUrl)
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.length()") { value(1) }
                    jsonPath("$[0].text") { value(payload.text) }
                    jsonPath("$[0].author") { value(payload.author) }
                }
        }

        @Nested
        @DisplayName("Invalid payload")
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        inner class PostInvalid {

            @Test
            fun `should fail with empty payload`() {
                val payload = "{}"

                // Fail to create message
                mockMvc.post(baseUrl) {
                    contentType = MediaType.APPLICATION_JSON
                    content = payload
                    accept = MediaType.APPLICATION_JSON
                }
                    .andExpect {
                        status { isBadRequest() }
                        jsonPath("$.errors.text") { value("Text is required") }
                        jsonPath("$.errors.author") { value("Author is required") }
                    }

                // Check that no message was created
                mockMvc.get(baseUrl)
                    .andExpect {
                        status { isOk() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        jsonPath("$") { isEmpty() }
                    }
            }

            @Test
            fun `should fail with empty fields`() {
                val payload = mapOf("author" to "", "text" to "")

                // Fail to create message
                mockMvc.post(baseUrl) {
                    contentType = MediaType.APPLICATION_JSON
                    content = objectMapper.writeValueAsString(payload)
                    accept = MediaType.APPLICATION_JSON
                }
                    .andExpect {
                        status { isBadRequest() }
                        jsonPath("$.errors.text") { value("Text is required") }
                        jsonPath("$.errors.author") { value("Author is required") }
                    }

                // Check that no message was created
                mockMvc.get(baseUrl)
                    .andExpect {
                        status { isOk() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        jsonPath("$") { isEmpty() }
                    }
            }

            @Test
            fun `should fail with only author`() {
                val payload = mapOf("author" to "Ben Kenobi")

                // Fail to create message
                mockMvc.post(baseUrl) {
                    contentType = MediaType.APPLICATION_JSON
                    content = objectMapper.writeValueAsString(payload)
                    accept = MediaType.APPLICATION_JSON
                }
                    .andExpect {
                        status { isBadRequest() }
                        jsonPath("$.errors.text") { value("Text is required") }
                    }

                // Check that no message was created
                mockMvc.get(baseUrl)
                    .andExpect {
                        status { isOk() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        jsonPath("$") { isEmpty() }
                    }
            }

            @Test
            fun `should fail with only text`() {
                val payload = mapOf("text" to "Only a Sith deals in absolutes.")

                // Fail to create message
                mockMvc.post(baseUrl) {
                    contentType = MediaType.APPLICATION_JSON
                    content = objectMapper.writeValueAsString(payload)
                    accept = MediaType.APPLICATION_JSON
                }
                    .andExpect {
                        status { isBadRequest() }
                        jsonPath("$.errors.author") { value("Author is required") }
                    }

                // Check that no message was created
                mockMvc.get(baseUrl)
                    .andExpect {
                        status { isOk() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        jsonPath("$") { isEmpty() }
                    }
            }
        }
    }

    @Nested
    @DisplayName("GET /messages/{id}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetMessage {
        private lateinit var message: Message

        @BeforeEach
        fun beforeEach() {
            message = messageRepository.save(Message(text = "Expelliarmus", author = "Harry Potter"))
        }

        @Test
        fun `should return existing message`() {
            val messageResponse = MessageResponseMapper.toResponse(message)

            mockMvc.get("$baseUrl/${message.id}")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    content { string(objectMapper.writeValueAsString(messageResponse)) }
                }
        }
        @Test
        fun `should fail for a non-existent message`() {
            val id = "123456dummy"

            mockMvc.get("$baseUrl/$id")
                .andExpect {
                    status { isNotFound() }
                    jsonPath("$.message") { value("Message with id $id not found") }
                }
        }
    }

    @Nested
    @DisplayName("DELETE /messages/{id}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteMessage {
        private lateinit var message: Message

        @BeforeEach
        fun beforeEach() {
            message = messageRepository.save(Message(text = "Expelliarmus", author = "Harry Potter"))
        }

        @Test
        fun `should delete an existing message`() {
            // Delete message
            mockMvc.delete("$baseUrl/${message.id}")
                .andExpect {
                    status { isNoContent() }
                }

            // Check that message was deleted
            mockMvc.get(baseUrl)
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$") { isEmpty() }
                }
        }
        @Test
        fun `should fail for a non-existent message`() {
            val id = "123456dummy"

            // Attempt to delete non-existent message
            mockMvc.delete("$baseUrl/$id")
                .andExpect {
                    status { isNotFound() }
                    jsonPath("$.message") { value("Message with id $id not found") }
                }

            // Check that message was not deleted
            mockMvc.get(baseUrl)
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$") { isNotEmpty() }
                }
        }
    }
}
