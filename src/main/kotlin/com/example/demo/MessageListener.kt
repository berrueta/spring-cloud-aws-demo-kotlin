package com.example.demo

import io.awspring.cloud.sqs.annotation.SqsListener
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class MessageListener(
        val messageConsumer: MessageConsumer
) {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @SqsListener("demo-queue")
    fun receiveMessage(message: Message) {
        log.info("Received {}", message)
        messageConsumer.consumeMessage(message)
    }
}