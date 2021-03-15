package tdd.reallife

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client.builder
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import java.net.URI

class BillFromStore {

    fun exist(filename: String): Boolean {
        val endpointOverride = URI("http://localhost:4566")
        val credentialsProvider =
            StaticCredentialsProvider.create(AwsBasicCredentials.create("TEST_ACCESS_KEY", "TEST_SECRET_KEY"))
        val s3 = builder().credentialsProvider(credentialsProvider)
            .endpointOverride(endpointOverride)
            .region(Region.AWS_GLOBAL)
            .build()
        val response = s3.getObject(GetObjectRequest.builder().bucket("bills").key(filename).build())
        return response != null
    }
}