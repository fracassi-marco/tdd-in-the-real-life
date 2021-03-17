package tdd.reallife

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.S3Client.builder
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URI

class BillFromStore(private val endpoint: String) {

    fun exist(filename: String): Boolean {
        return s3Client().getObject(GetObjectRequest.builder().bucket("bills").key(filename).build()) != null
    }

    private fun s3Client(): S3Client {
        val credentialsProvider = StaticCredentialsProvider.create(AwsBasicCredentials.create("ignore", "ignore"))
        return builder().credentialsProvider(credentialsProvider)
            .endpointOverride(URI(endpoint))
            .region(Region.AWS_GLOBAL)
            .build()
    }

    fun read(filename: String): String {
        val request = GetObjectRequest.builder().bucket("bills").key(filename).build()
        val response = s3Client().getObject(request)
        BufferedReader(InputStreamReader(response)).use {
            return it.readText()
        }
    }
}