package tdd.reallife

import daikon.core.Context
import daikon.core.Request
import daikon.core.Response
import daikon.core.RouteAction
import daikon.freemarker.html
import topinambur.http
import java.sql.DriverManager

class CartPage : RouteAction {

    override fun handle(request: Request, response: Response, context: Context) {
        val products = loadProducts()
        val cartItems = loadCart()

        products.forEach {
            val response = "https://www.random.org/integers/?num=1&min=1&max=100&col=1&base=10&format=plain&rnd=new&id=${it.name}".http.get()
            val price = response.body.replace("\n", "").toInt()
            it.withPrice(price)
        }

        response.html("cart", hashMapOf(
            "products" to products,
            "cartItems" to cartItems
        ))
    }

    private fun loadCart(): List<CartItem> {
        val url = "jdbc:postgresql://localhost:5432/shop?user=postgres&password=tdd"
        val conn = DriverManager.getConnection(url)

        val results = conn.prepareStatement("SELECT * FROM cart").executeQuery()

        val items = mutableListOf<CartItem>()
        while (results.next()) {
            items.add(CartItem(results.getString("name"), results.getInt("price")))
        }

        return items
    }

    private fun loadProducts(): List<Product> {
        val url = "jdbc:postgresql://localhost:5432/shop?user=postgres&password=tdd"
        val conn = DriverManager.getConnection(url)

        val results = conn.prepareStatement("SELECT * FROM products").executeQuery()

        val items = mutableListOf<Product>()
        while (results.next()) {
            items.add(Product(results.getString("name"), results.getString("description"), results.getString("image")))
        }

        return items
    }
}
