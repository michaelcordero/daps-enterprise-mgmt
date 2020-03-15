package database.facades

import java.io.Closeable

interface DataService : Closeable, UserData, BillingData
