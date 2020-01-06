package com.daps.ent.database
import com.daps.ent.model.User
import org.jetbrains.exposed.sql.*

class LocalDatabase(val db: Database ): DataService {
    override fun init() {
        // creates the used tables
        db.transaction {
            create(UsersTable)
        }
    }

    override fun close() {
        // may remove this method because the DataPool closes the connections.
    }

    override fun user(userId: String, hash: String?): User? {
        val user: User? = db.transaction {
            UsersTable.select { UsersTable.id.eq(userId) }
                .mapNotNull {
                    if (hash == null || it[UsersTable.passwordHash] == hash) {
                        User(userId, it[UsersTable.email], it[UsersTable.first_name],it[UsersTable.last_name], it[UsersTable.passwordHash])
                    } else {
                        null
                    }
                }
                .singleOrNull()
        }
        return user
    }

    override fun userByEmail(email: String): User? {
        val user: User? = db.transaction {
            UsersTable.select { UsersTable.email.eq(email) }
                .map { User(it[UsersTable.id], email, it[UsersTable.first_name], it[UsersTable.last_name], it[UsersTable.passwordHash]) }
                .singleOrNull()
        }
        return user
    }

    override fun all(): List<User> = db.transaction {
        UsersTable.selectAll().toMutableList()
    }.map {
        User(
            it[UsersTable.id],
            it[UsersTable.email],
            it[UsersTable.first_name],
            it[UsersTable.last_name],
            it[UsersTable.passwordHash]
        )
    }

    override fun addUser(user: User) {
        db.transaction {
            UsersTable.insert {
                it[id] = user.userId
                it[first_name] = user.first_name
                it[email] = user.email
                it[passwordHash] = user.passwordHash
            }
        }

    }

    override fun editUser(user: User) {
        db.transaction {
           UsersTable.update({
               UsersTable.id.eq(user.userId)
           }){
               it[first_name] = user.first_name
               it[email] = user.email
               it[passwordHash] = user.passwordHash
           }
        }
    }

    override fun removeUser(userId: String) {
        db.transaction {
            UsersTable.deleteWhere { UsersTable.id.eq(userId) }
        }
    }
}
