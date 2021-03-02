package tdd.reallife

import daikon.core.Context
import daikon.core.Request
import daikon.core.Response
import daikon.core.RouteAction
import daikon.freemarker.html
import topinambur.http

class CartPage : RouteAction {

    override fun handle(request: Request, response: Response, context: Context) {
        val products = loadProducts()

        products.forEach {
            val response = "https://www.random.org/integers/?num=1&min=1&max=100&col=1&base=10&format=plain&rnd=new&id=${it.id}".http.get()
            val price = response.body.replace("\n", "").toInt()
            it.withPrice(price)
        }

        response.html("cart", hashMapOf(
            "products" to products
        ))
    }

    private fun loadProducts(): List<Product> {
        return listOf(Product(1, "Garlic"), Product(2, "Daikon"), Product(3, "Onion"))
    }
}
