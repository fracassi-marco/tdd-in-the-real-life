package tdd.reallife

import daikon.core.HttpStatus.MOVED_PERMANENTLY_301
import daikon.core.Request
import daikon.core.Response
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.lang.RuntimeException

class PayExternalActionTest {

    private val request = mockk<Request>()
    private val response = mockk<Response>(relaxed = true)
    private val paymentAdapter = mockk<PaymentAdapter>(relaxed = true)
    private val cartRepository: CartRepository = mockk(relaxed = true)
    private val billRepository: BillRepository = mockk(relaxed = true)

    @Test
    fun redirectToCartPage() {
        every { request.param("price") } returns "5"

        PayExternalAction(paymentAdapter, cartRepository, billRepository).handle(request, response, mockk())

        verify { paymentAdapter.pay(5) }
        verify { response.redirect("http://localhost:4545/", MOVED_PERMANENTLY_301) }
    }

    @Test
    fun emptyCartWhenPaymentSucceeded() {
        every { request.param("price") } returns "5"

        PayExternalAction(paymentAdapter, cartRepository, billRepository).handle(request, response, mockk())

        verify { cartRepository.empty() }
    }

    @Test
    fun saveBillWhenPaymentSucceeded() {
        every { request.param("price") } returns "5"

        PayExternalAction(paymentAdapter, cartRepository, billRepository).handle(request, response, mockk())

        verify { billRepository.save(5) }
    }

    @Test
    fun doNotEmptyCartWhenPaymentFails() {
        every { request.param("price") } returns "7"
        every { paymentAdapter.pay(7) } throws RuntimeException()

        PayExternalAction(paymentAdapter, cartRepository, billRepository).handle(request, response, mockk())

        verify(exactly = 0) { cartRepository.empty() }
    }
}