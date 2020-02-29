package database.tables

import org.jetbrains.exposed.sql.Table

object UsersTable : Table() {
//    val id = varchar("id", 20).primaryKey()
    val email = varchar("email", 128).primaryKey()
    val first_name = varchar("first_name", 256)
    val last_name = varchar("last_name", 256)
    val passwordHash = varchar("password_hash", 64)
}
