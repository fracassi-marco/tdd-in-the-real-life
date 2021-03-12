package tdd.reallife

import daikon.core.Context
import daikon.core.Request
import daikon.core.Response
import daikon.core.RouteAction

class PayByCashAction : RouteAction {

    override fun handle(request: Request, response: Response, context: Context) {
        DbCartRepository().empty()

        response.redirect("http://localhost:4545/")
    }
}
