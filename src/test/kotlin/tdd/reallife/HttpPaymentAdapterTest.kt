package tdd.reallife

import daikon.HttpServer
import daikon.core.HttpStatus.INTERNAL_SERVER_ERROR_500
import daikon.core.HttpStatus.OK_200
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

class HttpPaymentAdapterTest {

    @Test
    fun callPaymentGateway() {
        var called = false

        HttpServer(4546) {
            post("/") { req, res, _ ->
                if(req.body() == """{"amount":3}""")
                    called = true
                res.status(OK_200)
            }
        }.start().use {

            HttpPaymentAdapter("http://localhost:4546").pay(3)

            assertThat(called).isTrue
        }
    }

    @Test
    fun ifPaymentGatewayRespondsWithNoOkThePaymentFails() {
        HttpServer(4546) {
            post("/") { _, res, _ ->
                res.status(INTERNAL_SERVER_ERROR_500)
            }
        }.start().use {
            assertThatThrownBy { HttpPaymentAdapter("http://localhost:4546").pay(3) }
        }
    }
}