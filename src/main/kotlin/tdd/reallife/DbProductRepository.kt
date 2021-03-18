package tdd.reallife

class DbProductRepository(private val connection: DbConnection) : ProductRepository {

    override fun all(): List<Product> {
        val results = connection.create().use {
            it.prepareStatement("SELECT * FROM products").executeQuery()
        }

        val items = mutableListOf<Product>()
        while (results.next()) {
            items.add(Product(results.getString("name"), results.getString("description"), results.getString("image")))
        }

        return items
    }
}
