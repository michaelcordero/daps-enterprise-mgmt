package database.queries

import database.tables.UsersTable
import model.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * This interface provides the methods for application users' sql operations. Instead of going with the delegate object
 * pattern, via Kotlin's "by" keyword, I decided to go with the default implementation of interface methods. This way
 * the inheritance hierarchy needn't create extra delegate objects, but still gets the power of delegation by declaration.
 * This only works because the db property is abstracted in this class, but overridden and instantiated in the
 * LocalDataService class.
 */
interface UserQuery {
    val db: Database

    /**
     * Attempt to get the user given the [email] and password [hash]
     */
    fun user(email: String, hash: String? = null): User? {
        return transaction(db) {
            UsersTable.select { UsersTable.email.eq(email) }
                .mapNotNull {
                    if (hash == null || it[UsersTable.passwordHash] == hash) {
                        User(
                            it[UsersTable.id],
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

    /**
     * Attempt to get user by using the [email].
     */
    fun userByEmail(email: String ): User? {
        return transaction(db) {
            UsersTable.select { UsersTable.email.eq(email) }
                .map { User(it[UsersTable.id], email, it[UsersTable.first_name], it[UsersTable.last_name], it[UsersTable.passwordHash]) }
                .singleOrNull()
        }
    }

    /**
     * Fetch all users
     */
    fun allUsers(): List<User> = transaction(db) {
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

    /**
     * Creates a new user
     */
    fun addUser(user: User) {
        transaction(db) {
            UsersTable.insert {
                it[id] = user.id
                it[first_name] = user.first_name
                it[last_name] = user.last_name
                it[email] = user.email
                it[passwordHash] = user.passwordHash
            }
        }
    }

    /**
     * Edits a user
     */
    fun editUser(user: User) {
        transaction(db) {
            UsersTable.update({
                UsersTable.id.eq(user.id)
            }){
                it[id] = user.id
                it[first_name] = user.first_name
                it[email] = user.email
                it[passwordHash] = user.passwordHash
            }
        }
    }

    /**
     * Removes user
     */
    fun removeUser( userId: Long ) {
        transaction(db) {
            UsersTable.deleteWhere { UsersTable.id.eq(userId) }
        }
    }
}
