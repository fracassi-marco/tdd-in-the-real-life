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
        val cartItems = loadCart()

        products.forEach {
            val response = "http://www.randomnumberapi.com/api/v1.0/random?min=1&max=10&id=${it.name}".http.get()
            val price = response.body.replace("[", "").replace("]", "").toInt()
            it.withPrice(price)
        }

        response.html(
            "cart", hashMapOf(
                "products" to products,
                "cartItems" to cartItems
            )
        )
    }

    private fun loadCart(): List<CartItem> {
        val results = DbConnection().create().use {
            it.prepareStatement("SELECT * FROM cart").executeQuery()
        }

        val items = mutableListOf<CartItem>()
        while (results.next()) {
            items.add(CartItem(results.getString("name"), results.getInt("price")))
        }

        return items
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
