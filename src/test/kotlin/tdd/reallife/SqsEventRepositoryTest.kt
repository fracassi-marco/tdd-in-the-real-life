package tdd.reallife

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SqsEventRepositoryTest {

    private val awsEndpoint = "http://localhost:4566"
    private val eventRepository = SqsEventRepository(awsEndpoint)
    private val sqsMessage = SqsMessage(awsEndpoint, "events_queue")

    @BeforeEach
    fun setUp() {
        eventRepository.init()
        sqsMessage.empty()
    }

    @Test
    fun sendMessage() {
        eventRepository.send("foo")

        sqsMessage.assertExists("foo")
    }
}