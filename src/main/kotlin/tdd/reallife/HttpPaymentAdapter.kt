package tdd.reallife

import daikon.core.HttpStatus.OK_200
import topinambur.http
import java.lang.RuntimeException

class HttpPaymentAdapter(private val endpoint: String) : PaymentAdapter {

    override fun pay(euro: Int) {
        val response = "$endpoint/".http.post(body = """{"amount":$euro}""")
        if(response.statusCode != OK_200)
            throw RuntimeException("Payment failed")
    }
}