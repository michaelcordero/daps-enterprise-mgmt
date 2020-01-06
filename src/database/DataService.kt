package com.daps.ent.database

import java.io.Closeable

interface DataService : Closeable, UserData {
    /**
     * Initializes the database.
     */
    fun init()
}
