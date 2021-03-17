package tdd.reallife

import org.assertj.core.api.Assertions.assertThat
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

        BillFromStore(s3Endpoint).exist("bill_1.txt")
    }

    @Test
    fun contentIsTotalPrice() {
        S3BillRepository(s3Endpoint).save(8)

        val content = BillFromStore(s3Endpoint).read("bill_1.txt")
        assertThat(content).isEqualTo("8")
    }
}