package com.example.demo

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class MessageConsumer {
    private val log = LoggerFactory.getLogger(this.javaClass)

    fun consumeMessage(message: Message) {
        log.info("Received {}", message)
    }
}
