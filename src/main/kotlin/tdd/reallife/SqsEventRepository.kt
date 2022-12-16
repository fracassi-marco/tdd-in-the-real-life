package tdd.reallife

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest
import software.amazon.awssdk.services.sqs.model.SendMessageRequest
import java.net.URI

class SqsEventRepository(private val endpoint: String) : EventRepository {

    private val queue = "events_queue"

    fun init(): EventRepository {
        sqsClient().createQueue(CreateQueueRequest.builder().queueName(queue).build())
        return this
    }

    override fun send(message: String) {
        val queueUrl = sqsClient().getQueueUrl(GetQueueUrlRequest.builder().queueName(queue).build()).queueUrl()
        sqsClient().sendMessage(SendMessageRequest.builder().queueUrl(queueUrl).messageBody(message).build())
    }

    private fun sqsClient(): SqsClient {
        val credentialsProvider = StaticCredentialsProvider.create(AwsBasicCredentials.create("ignore", "ignore"))
        return SqsClient.builder().credentialsProvider(credentialsProvider)
            .endpointOverride(URI(endpoint))
            .region(Region.AP_EAST_1)
            .build()
    }
}