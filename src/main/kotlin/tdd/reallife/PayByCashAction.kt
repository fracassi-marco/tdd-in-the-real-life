package tdd.reallife

import daikon.core.Context
import daikon.core.Request
import daikon.core.Response
import daikon.core.RouteAction

class PayByCashAction : RouteAction {

    override fun handle(request: Request, response: Response, context: Context) {
        emptyCart()

        response.redirect("http://localhost:4545/")
    }

    private fun emptyCart() {
        DbConnection().create().use { it.prepareStatement("TRUNCATE TABLE cart").execute() }
    }
}