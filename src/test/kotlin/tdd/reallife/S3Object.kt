package tdd.reallife

import org.assertj.core.api.Assertions.assertThat
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.S3Client.builder
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URI

class S3Object(private val endpoint: String, private val bucket: String, private val filename: String) {

    fun assertExists() {
        assertThat(read()).isNotNull
    }

    fun assertContentIsEqualTo(expected: String) {
        assertThat(read()).isEqualTo(expected)
    }

    private fun read(): String {
        val request = GetObjectRequest.builder().bucket(bucket).key(filename).build()
        val response = s3Client().getObject(request)
        BufferedReader(InputStreamReader(response)).use {
            return it.readText()
        }
    }

    private fun s3Client(): S3Client {
        val credentialsProvider = StaticCredentialsProvider.create(AwsBasicCredentials.create("ignore", "ignore"))
        return builder().credentialsProvider(credentialsProvider)
            .endpointOverride(URI(endpoint))
            .region(Region.AWS_GLOBAL)
            .build()
    }
}