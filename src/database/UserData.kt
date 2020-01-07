package com.daps.ent.database

import com.daps.ent.model.User

interface UserData {

    /**
     * Attempt to get the user given the [userId] and password [hash]
     */
    fun user(email: String, hash: String? = null): User?

    /**
     * Attempt to get user by using the [email].
     */
    fun userByEmail(email: String ): User?

    /**
     * Fetch all users
     */
    fun all(): List<User>

    /**
     * Creates a new user
     */
    fun addUser(user: User)

    /**
     * Edits a user
     */
    fun editUser(user: User)

    /**
     * Removes user
     */
    fun removeUser( userId: String )

}
