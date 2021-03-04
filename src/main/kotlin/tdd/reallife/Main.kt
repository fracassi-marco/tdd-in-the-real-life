package tdd.reallife

import daikon.HttpServer

fun main() {
    HttpServer {
        get("/", CartPage())
        post("/add-to-cart", AddToCartAction())
        post("/pay-by-cash", PayByCashAction())
    }.start()
}