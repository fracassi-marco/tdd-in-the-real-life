package tdd.reallife

import daikon.core.Context
import daikon.core.Request
import daikon.core.Response
import daikon.core.RouteAction


class AddToCartAction(private val cartRepository: CartRepository) : RouteAction {

    override fun handle(request: Request, response: Response, context: Context) {
        val price = request.param("price").toInt()
        val name = request.param("name")
        cartRepository.save(CartItem(name, price))

        response.redirect("http://localhost:4545/")
    }
}

