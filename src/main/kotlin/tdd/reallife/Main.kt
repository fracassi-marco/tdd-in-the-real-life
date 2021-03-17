package tdd.reallife

import daikon.HttpServer

fun main() {
    App().start()
}

class App : AutoCloseable {

    private val server = HttpServer {
        val cartRepository = DbCartRepository()
        val billRepository = S3BillRepository("http://localhost:4566").init()
        val paymentAdapter = HttpPaymentAdapter("http://localhost:4546")

        get("/", CartPage(cartRepository))
        post("/add-to-cart", AddToCartAction(cartRepository))
        post("/pay-by-cash", PayByCashAction(cartRepository, billRepository))
        post("/pay-external", PayExternalAction(paymentAdapter, cartRepository, billRepository))
    }

    fun start() {
        server.start()
    }

    override fun close() {
        server.close()
    }
}