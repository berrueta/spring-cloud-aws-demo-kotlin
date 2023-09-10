package com.example.demo

import io.awspring.cloud.sqs.operations.SqsTemplate
import io.awspring.cloud.test.sqs.SqsTest
import org.awaitility.Awaitility.await
import org.junit.jupiter.api.Test
import org.mockito.Mockito.atLeastOnce
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.BindMode
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import java.time.Duration


@SqsTest(MessageListener::class)
@Testcontainers
class MessageListenerTest{
    companion object {

        @Container
        var localStack = LocalStackContainer(DockerImageName.parse("localstack/localstack:2.2"))
                .withClasspathResourceMapping("/localstack", "/etc/localstack", BindMode.READ_ONLY)
                .withServices(LocalStackContainer.Service.SQS)
                .waitingFor(Wait.forLogMessage(".*Initialized\\.\n", 1))

        @JvmStatic
        @DynamicPropertySource
        fun registerProperties(registry: DynamicPropertyRegistry) {
            with(registry) {
                add("spring.cloud.aws.sqs.endpoint") { localStack.getEndpointOverride(LocalStackContainer.Service.SQS).toString() }
                add("spring.cloud.aws.credentials.access-key") { "x" }
                add("spring.cloud.aws.credentials.secret-key") { "x" }
                add("spring.cloud.aws.region.static") { localStack.region }
            }
        }
    }

    @Autowired
    lateinit var sqsTemplate: SqsTemplate

    @MockBean
    lateinit var messageConsumer: MessageConsumer

    @Test
    fun `should receive the message`() {
        val message = Message("this is a test")

        sqsTemplate.send("demo-queue", message)

        await().atMost(Duration.ofSeconds(5)).untilAsserted {
            verify(messageConsumer, atLeastOnce()).consumeMessage(message)
        }
    }
}