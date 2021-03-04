package tdd.reallife

data class Product(val id: Int, val name: String) {

    var price: Int = 0

    fun withPrice(value: Int) : Product {
        price = value
        return this
    }
}
