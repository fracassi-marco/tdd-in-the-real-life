package tdd.reallife

interface CartRepository {
    fun empty()
    fun save(cartItem: CartItem)
    fun load(): List<CartItem>
}