package tdd.reallife

import daikon.core.Context
import daikon.core.Request
import daikon.core.Response
import daikon.core.RouteAction
import java.sql.DriverManager

class PayByCashAction : RouteAction {

    override fun handle(request: Request, response: Response, context: Context) {
        emptyCart()

        response.redirect("http://localhost:4545/")
    }

    private fun emptyCart() {
        val url = "jdbc:postgresql://localhost:5432/shop?user=postgres&password=tdd"
        val conn = DriverManager.getConnection(url)

        conn.prepareStatement("TRUNCATE TABLE cart").execute()
    }
}