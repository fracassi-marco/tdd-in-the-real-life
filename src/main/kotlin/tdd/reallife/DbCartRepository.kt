package tdd.reallife

class DbCartRepository : CartRepository {

    override fun empty() {
        DbConnection().create().use { it.prepareStatement("TRUNCATE TABLE cart").execute() }
    }

    override fun save(cartItem: CartItem) {
        DbConnection().create().use {
            it.prepareStatement("INSERT INTO cart VALUES (?, ?)").apply {
                setString(1, cartItem.name)
                setInt(2, cartItem.price)
            }.execute()
        }
    }

    override fun load(): List<CartItem> {
        val results = DbConnection().create().use {
            it.prepareStatement("SELECT * FROM cart").executeQuery()
        }

        val items = mutableListOf<CartItem>()
        while (results.next()) {
            items.add(CartItem(results.getString("name"), results.getInt("price")))
        }

        return items
    }
}