package tdd.reallife

import daikon.HttpServer

fun main() {
    App().start()
}

class App : AutoCloseable {

    private val server = HttpServer {
        get("/", CartPage())
        post("/add-to-cart", AddToCartAction())
        post("/pay-by-cash", PayByCashAction())
    }

    fun start() {
        server.start()
    }

    override fun close() {
        server.close()
    }
}