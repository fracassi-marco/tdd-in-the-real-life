package tdd.reallife

import daikon.core.Context
import daikon.core.Request
import daikon.core.Response
import daikon.core.RouteAction

class PayByCashAction(private val cartRepository: CartRepository, private val billRepository: BillRepository) :
    RouteAction {

    override fun handle(request: Request, response: Response, context: Context) {
        cartRepository.empty()
        billRepository.save(request.param("price").toInt())

        response.redirect("http://localhost:4545/")
    }
}
