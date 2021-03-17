package tdd.reallife

import daikon.core.Context
import daikon.core.HttpStatus.MOVED_PERMANENTLY_301
import daikon.core.Request
import daikon.core.Response
import daikon.core.RouteAction

class PayExternalAction(
    private val paymentAdapter: PaymentAdapter,
    private val cartRepository: CartRepository,
    private val billRepository: BillRepository
) :
    RouteAction {

    override fun handle(request: Request, response: Response, context: Context) {
        val price = request.param("price").toInt()
        try {
            paymentAdapter.pay(price)
            cartRepository.empty()
            billRepository.save(price)
        } catch (t: Throwable) {
        }

        response.redirect("http://localhost:4545/", MOVED_PERMANENTLY_301)
    }
}
