package tdd.reallife

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.BucketAlreadyExistsException
import software.amazon.awssdk.services.s3.model.BucketAlreadyOwnedByYouException
import software.amazon.awssdk.services.s3.model.CreateBucketRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.net.URI

class S3BillRepository(private val endpoint: String) : BillRepository {

    override fun save(price: Int) {
        val request = PutObjectRequest.builder().bucket("bills").key("bill_1.txt").build()
        s3Client().putObject(request, RequestBody.fromString(price.toString()))
    }

    fun init(): BillRepository {
        try {
            s3Client().createBucket(CreateBucketRequest.builder().bucket("bills").build())
        } catch (e: BucketAlreadyExistsException) {
        } catch (e: BucketAlreadyOwnedByYouException) {
        }

        return this
    }

    private fun s3Client(): S3Client {
        val credentialsProvider = StaticCredentialsProvider.create(AwsBasicCredentials.create("ignore", "ignore"))
        return S3Client.builder()
            .credentialsProvider(credentialsProvider)
            .endpointOverride(URI(endpoint))
            .region(Region.AWS_GLOBAL)
            .build()
    }
}

