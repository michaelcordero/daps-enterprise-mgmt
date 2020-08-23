package database.queries

import database.tables.UsersTable
import model.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import security.DAPSRole

/**
 * This interface provides the methods for application users' sql operations. Instead of going with the delegate object
 * pattern, via Kotlin's "by" keyword, I decided to go with the default implementation of interface methods. This way
 * the inheritance hierarchy needn't create extra delegate objects, but still gets the power of delegation by declaration.
 * This only works because the db property is abstracted in this class, but overridden and instantiated in the
 * LocalDataQuery class.
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
                            it[UsersTable.ID],
                            it[UsersTable.email],
                            it[UsersTable.first_name],
                            it[UsersTable.last_name],
                            it[UsersTable.passwordHash],
                            DAPSRole.values().first{ role -> role.key == it[UsersTable.role]}
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
    fun userByEmail(email: String): User? {
        return transaction(db) {
            UsersTable.select { UsersTable.email.eq(email) }
                .map {
                    User(
                        it[UsersTable.ID],
                        email,
                        it[UsersTable.first_name],
                        it[UsersTable.last_name],
                        it[UsersTable.passwordHash],
                        DAPSRole.values().first{ role -> role.key == it[UsersTable.role]}
                    )
                }
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
            it[UsersTable.ID],
            it[UsersTable.email],
            it[UsersTable.first_name],
            it[UsersTable.last_name],
            it[UsersTable.passwordHash],
            DAPSRole.values().first{ role -> role.key == it[UsersTable.role]}
        )
    }

    /**
     * Creates a new user
     */
    fun addUser(user: User): Long {
        return transaction(db) {
            UsersTable.insert {
                it[ID] = user.id
                it[first_name] = user.first_name
                it[last_name] = user.last_name
                it[email] = user.email
                it[passwordHash] = user.passwordHash
                it[role] = user.role.key
            }
        } get UsersTable.ID
    }

    /**
     * Edits a user
     */
    fun updateUser(user: User): Int {
        return transaction(db) {
            UsersTable.update({
                UsersTable.ID.eq(user.id)
            }) {
                it[ID] = user.id
                it[first_name] = user.first_name
                it[email] = user.email
                it[passwordHash] = user.passwordHash
                it[role] = user.role.key
            }
        }
    }

    /**
     * Removes user
     */
    fun deleteUser(userId: Long): Int {
        return transaction(db) {
            UsersTable.deleteWhere { UsersTable.ID.eq(userId) }
        }
    }
}
