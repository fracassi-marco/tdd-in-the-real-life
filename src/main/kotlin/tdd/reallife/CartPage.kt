package tdd.reallife

import daikon.core.Context
import daikon.core.Request
import daikon.core.Response
import daikon.core.RouteAction
import daikon.freemarker.html
import topinambur.http

class CartPage(private val cartRepository: CartRepository, private val productRepository: ProductRepository) : RouteAction {

    override fun handle(request: Request, response: Response, context: Context) {
        val products = productRepository.all()
        val cartItems = cartRepository.load()

        products.forEach {
            val priceResponse = "http://www.randomnumberapi.com/api/v1.0/random?min=1&max=10&id=${it.name}".http.get()
            val price = priceResponse.body.replace("[", "").replace("]", "").toInt()
            it.withPrice(price)
        }

        response.html(
            "cart", hashMapOf(
                "products" to products,
                "cartItems" to cartItems,
                "total" to cartItems.sumBy { it.price }
            )
        )
    }
}
