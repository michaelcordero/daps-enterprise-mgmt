package database.tables

import org.jetbrains.exposed.sql.Table

object UsersTable : Table() {
    val ID = long("ID").autoIncrement()
    override val primaryKey = PrimaryKey(ID)
    val email = varchar("email", 128)
    val first_name = varchar("first_name", 256)
    val last_name = varchar("last_name", 256)
    val passwordHash = varchar("password_hash", 64)
    val role = varchar("role",19)
}
