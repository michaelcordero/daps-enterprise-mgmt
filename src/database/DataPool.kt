package com.daps.ent.database
import com.mchange.v2.c3p0.*
import org.h2.Driver
import java.io.File

class DataPool {
    // File where the database is to be stored.
    val dir: File = File("build/db")
    // Pool of JDBC Connections used
    val pool: ComboPooledDataSource = ComboPooledDataSource().apply {
        driverClass = Driver::class.java.name
        jdbcUrl = "jdbc:h2:file:${dir.canonicalFile.absolutePath}"
        user = ""
        password = ""
    }
}
