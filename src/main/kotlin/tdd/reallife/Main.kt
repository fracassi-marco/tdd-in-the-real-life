package tdd.reallife

import daikon.HttpServer

fun main() {
    HttpServer {
        get("/", CartPage())
    }.start()
}