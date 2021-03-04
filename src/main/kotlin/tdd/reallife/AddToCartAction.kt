package tdd.reallife

import daikon.core.Context
import daikon.core.Request
import daikon.core.Response
import daikon.core.RouteAction
import java.sql.DriverManager


class AddToCartAction : RouteAction{

    override fun handle(request: Request, response: Response, context: Context) {
        val price = request.param("price").toInt()
        val name = request.param("name")
        saveToCart(CartItem(name, price))

        response.redirect("http://localhost:4545/")
    }

    private fun saveToCart(cartItem: CartItem) {
        val url = "jdbc:postgresql://localhost:5432/shop?user=postgres&password=tdd"
        val conn = DriverManager.getConnection(url)

        conn.prepareStatement("INSERT INTO cart VALUES (?, ?)").apply {
            setString(1, cartItem.name)
            setInt(2, cartItem.price)
        }.execute()
    }
}

