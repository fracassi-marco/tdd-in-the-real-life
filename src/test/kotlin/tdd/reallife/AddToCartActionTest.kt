package tdd.reallife

import daikon.core.Request
import daikon.core.Response
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class AddToCartActionTest {

    private val request = mockk<Request>()
    private val response = mockk<Response>(relaxed = true)
    private val cartRepository: CartRepository = mockk(relaxed = true)
    private val eventRepository: EventRepository = mockk(relaxed = true)

    @Test
    fun sendBigCartCreatedEvent() {
        every { request.param("price") } returns "2"
        every { request.param("name") } returns "ignore"
        every { cartRepository.load() } returns listOf(anItem(), anItem(), anItem(), anItem())

        AddToCartAction(cartRepository, eventRepository).handle(request, response, mockk())

        verify { eventRepository.send("big_cart_created") }
    }

    @Test
    fun doNotSendBigCartCreatedEvent() {
        every { request.param("price") } returns "2"
        every { request.param("name") } returns "ignore"
        every { cartRepository.load() } returns listOf(anItem(), anItem(), anItem())

        AddToCartAction(cartRepository, eventRepository).handle(request, response, mockk())

        verify(exactly = 0) { eventRepository.send("big_cart_created") }
    }

    private fun anItem() = CartItem("ignore", 1)
}