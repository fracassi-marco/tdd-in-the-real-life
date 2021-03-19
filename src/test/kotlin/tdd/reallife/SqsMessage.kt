package tdd.reallife

import org.assertj.core.api.Assertions.assertThat
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region.AWS_GLOBAL
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.*
import java.net.URI

class SqsMessage(private val endpoint: String, private val queue: String) {

    fun empty() {
        sqsClient().purgeQueue(PurgeQueueRequest.builder().queueUrl(queueUrl()).build())
    }

    fun assertExists(message: String) {
        assertThat(read().single { it.body() == message }).isNotNull
    }

    private fun read(): MutableList<Message> {
        val receiveMessageRequest = ReceiveMessageRequest.builder().queueUrl(queueUrl()).build()
        return sqsClient().receiveMessage(receiveMessageRequest).messages()
    }

    private fun queueUrl() = sqsClient().getQueueUrl(GetQueueUrlRequest.builder().queueName(queue).build()).queueUrl()

    private fun sqsClient(): SqsClient {
        val credentialsProvider = StaticCredentialsProvider.create(AwsBasicCredentials.create("ignore", "ignore"))
        return SqsClient.builder().credentialsProvider(credentialsProvider)
            .endpointOverride(URI(endpoint))
            .region(AWS_GLOBAL)
            .build()
    }
}