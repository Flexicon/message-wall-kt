package com.flexicondev.messagewall.api

import com.flexicondev.messagewall.MessageWallSpringTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@MessageWallSpringTest
@AutoConfigureMockMvc
internal class RootControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `should return welcome json`() {
        mockMvc.get("/")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.message") { value("message-wall api") }
            }
    }
}
