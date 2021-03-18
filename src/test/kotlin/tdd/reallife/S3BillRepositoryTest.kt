package tdd.reallife

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class S3BillRepositoryTest {

    private val s3Endpoint = "http://localhost:4566"

    @BeforeEach
    fun setUp() {
        S3BillRepository(s3Endpoint).init()
    }

    @Test
    fun fileExistsOnS3() {
        S3BillRepository(s3Endpoint).save(4)

        S3Object(s3Endpoint, "bills", "bill_1.txt").assertExists()
    }

    @Test
    fun contentIsTotalPrice() {
        S3BillRepository(s3Endpoint).save(8)

        S3Object(s3Endpoint, "bills", "bill_1.txt").assertContentIsEqualTo("8")
    }
}