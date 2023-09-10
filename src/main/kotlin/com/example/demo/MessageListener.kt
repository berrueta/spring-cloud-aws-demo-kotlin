package com.example.demo

import io.awspring.cloud.sqs.annotation.SqsListener
import org.springframework.stereotype.Service

@Service
class MessageListener(
        val messageConsumer: MessageConsumer
) {
    @SqsListener("demo-queue")
    fun receiveMessage(message: Message) {
        messageConsumer.consumeMessage(message)
    }
}