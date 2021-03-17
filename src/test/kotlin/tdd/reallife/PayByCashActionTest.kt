package tdd.reallife

import daikon.core.Request
import daikon.core.Response
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class PayByCashActionTest {

    private val request = mockk<Request>()
    private val response = mockk<Response>(relaxed = true)
    private val cartRepository: CartRepository = mockk(relaxed = true)
    private val billRepository: BillRepository = mockk(relaxed = true)

    @Test
    fun saveBillWhenPaymentSucceeded() {
        every { request.param("price") } returns "5"

        PayByCashAction(cartRepository, billRepository).handle(request, response, mockk())

        verify { billRepository.save(5) }
    }
}