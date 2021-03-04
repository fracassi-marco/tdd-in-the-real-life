package tdd.reallife

import daikon.core.Context
import daikon.core.Request
import daikon.core.Response
import daikon.core.RouteAction
import java.sql.DriverManager


class AddToCartAction : RouteAction{

    override fun handle(request: Request, response: Response, context: Context) {
        val productId = request.param("id").toInt()
        val price = request.param("price").toInt()
        val name = request.param("name")
        saveToCart(Product(productId, name).withPrice(price))

        response.redirect("http://localhost:4545/")
    }

    private fun saveToCart(product: Product) {
        val url = "jdbc:postgresql://localhost:5432/shop?user=postgres&password=tdd"
        val conn = DriverManager.getConnection(url)

        conn.prepareStatement("INSERT INTO cart VALUES (?, ?, ?)").apply {
            setInt(1, product.id)
            setString(2, product.name)
            setInt(3, product.price)
        }.execute()
    }
}

