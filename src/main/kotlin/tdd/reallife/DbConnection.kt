package tdd.reallife

import java.sql.Connection
import java.sql.DriverManager

class DbConnection {
    fun create(): Connection {
        val url = "jdbc:postgresql://localhost:5432/shop?user=postgres&password=tdd"
        return DriverManager.getConnection(url)
    }
}
