package tdd.reallife

class DbCartRepository : CartRepository {
    override fun empty() {
        DbConnection().create().use { it.prepareStatement("TRUNCATE TABLE cart").execute() }
    }
}