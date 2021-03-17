package tdd.reallife

import daikon.core.Context
import daikon.core.Request
import daikon.core.Response
import daikon.core.RouteAction
import daikon.freemarker.html
import topinambur.http

class CartPage(private val cartRepository: CartRepository) : RouteAction {

    override fun handle(request: Request, response: Response, context: Context) {
        val products = loadProducts()
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

    private fun loadProducts(): List<Product> {
        val results = DbConnection().create().use {
            it.prepareStatement("SELECT * FROM products").executeQuery()
        }

        val items = mutableListOf<Product>()
        while (results.next()) {
            items.add(Product(results.getString("name"), results.getString("description"), results.getString("image")))
        }

        return items
    }
}
