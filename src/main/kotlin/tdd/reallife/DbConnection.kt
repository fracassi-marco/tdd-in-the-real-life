package tdd.reallife

import java.sql.Connection
import java.sql.DriverManager

class DbConnection(private val endpoint: String) {
    fun create(): Connection {
        val url = "jdbc:postgresql://$endpoint/shop?user=postgres&password=tdd"
        return DriverManager.getConnection(url)
    }
}
