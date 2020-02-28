package com.daps.ent.facades

import java.io.Closeable

interface DataService : Closeable, UserData {
    /**
     * Initializes the database.
     */
    fun init()
}
