package tdd.reallife

data class Product(val name: String, val description: String, val image: String) {

    var price: Int = 0

    fun withPrice(value: Int) : Product {
        price = value
        return this
    }
}

