package database
import com.zaxxer.hikari.HikariDataSource
import java.io.File

class DataPool {
    // File where the database is to be stored.
    private val database_directory: String = "."
    val dir: File = File( database_directory )
    // Pool of JDBC Connections used
    val pool: HikariDataSource = HikariDataSource().apply {
        jdbcUrl = "jdbc:h2:file:${dir.canonicalFile.absolutePath}"
        username = ""
        password = ""
    }
}
