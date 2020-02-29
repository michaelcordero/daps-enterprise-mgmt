package com.daps.ent.database
import com.daps.ent.Tables.UsersTable
import com.daps.ent.facades.DataService
import model.User
import org.jetbrains.exposed.sql.*

class LocalDatabase(val db: Database ): DataService {
    override fun init() {
        // creates the used database.tables
        db.transaction {
            create(UsersTable)
        }
    }

    override fun close() {
        // may remove this method because the DataPool closes the connections.
    }

    override fun user(email: String, hash: String?): User? {
        return db.transaction {
            UsersTable.select { UsersTable.email.eq(email) }
                .mapNotNull {
                    if (hash == null || it[UsersTable.passwordHash] == hash) {
                        User(
                            it[UsersTable.email],
                            it[UsersTable.first_name],
                            it[UsersTable.last_name],
                            it[UsersTable.passwordHash]
                        )
                    } else {
                        null
                    }
                }
                .singleOrNull()
        }
    }

    override fun userByEmail(email: String): User? {
        return db.transaction {
            UsersTable.select { UsersTable.email.eq(email) }
                .map { User(email, it[UsersTable.first_name], it[UsersTable.last_name], it[UsersTable.passwordHash]) }
                .singleOrNull()
        }
    }

    override fun all(): List<User> = db.transaction {
        UsersTable.selectAll().toMutableList()
    }.map {
        User(
            it[UsersTable.email],
            it[UsersTable.first_name],
            it[UsersTable.last_name],
            it[UsersTable.passwordHash]
        )
    }

    override fun addUser(user: User) {
        db.transaction {
            UsersTable.insert {
                it[first_name] = user.first_name
                it[last_name] = user.last_name
                it[email] = user.email
                it[passwordHash] = user.passwordHash
            }
        }

    }

    override fun editUser(user: User) {
        db.transaction {
           UsersTable.update({
               UsersTable.email.eq(user.email)
           }){
               it[first_name] = user.first_name
               it[email] = user.email
               it[passwordHash] = user.passwordHash
           }
        }
    }

    override fun removeUser(userId: String) {
        db.transaction {
            UsersTable.deleteWhere { UsersTable.email.eq(userId) }
        }
    }
}
