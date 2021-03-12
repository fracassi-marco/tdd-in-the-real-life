package tdd.reallife

import daikon.core.Context
import daikon.core.HttpStatus.MOVED_PERMANENTLY_301
import daikon.core.Request
import daikon.core.Response
import daikon.core.RouteAction

class PayExternalAction(private val paymentAdapter: PaymentAdapter, private val cartRepository: CartRepository) :
    RouteAction {

    override fun handle(request: Request, response: Response, context: Context) {
        val euro = request.param("price").toInt()
        try {
            paymentAdapter.pay(euro)
            cartRepository.empty()
        } catch (t: Throwable) {
        }

        response.redirect("http://localhost:4545/", MOVED_PERMANENTLY_301)
    }
}
