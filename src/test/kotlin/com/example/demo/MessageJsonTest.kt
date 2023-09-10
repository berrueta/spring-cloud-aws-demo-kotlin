package com.example.demo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester

@JsonTest
class MessageJsonTest {
    @Autowired
    lateinit var jacksonTester : JacksonTester<Message>

    @Test
    fun `Should deserialize message`() {
        val message = jacksonTester.parse("""
            { "payload": "sensitive data" }
        """)
        assertThat(message).isEqualTo(Message("sensitive data"))
    }
}