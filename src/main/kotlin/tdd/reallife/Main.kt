package tdd.reallife

import daikon.HttpServer
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.CreateBucketRequest
import java.net.URI

fun main() {
    App().start()
}

class App : AutoCloseable {

    private val server = HttpServer {
        get("/", CartPage())
        post("/add-to-cart", AddToCartAction())
        post("/pay-by-cash", PayByCashAction())
        post("/pay-external", PayExternalAction(HttpPaymentAdapter("http://localhost:4546"), DbCartRepository()))
    }

    fun start() {
        server.start()
        createBucket()
    }

    override fun close() {
        server.close()
    }

    fun createBucket() {
        val endpointOverride = URI("http://localhost:4566")
        val credentialsProvider =
            StaticCredentialsProvider.create(AwsBasicCredentials.create("TEST_ACCESS_KEY", "TEST_SECRET_KEY"))
        val s3 = S3Client.builder().credentialsProvider(credentialsProvider)
            .endpointOverride(endpointOverride)
            .region(Region.AWS_GLOBAL)
            .build()
        s3.createBucket(CreateBucketRequest.builder().bucket("bills").build())
    }
}