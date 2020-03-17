package database
import database.facades.DataService
import database.tables.BillingTable
import database.tables.UsersTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class LocalDataService: DataService {
    private val dp: DataPool = DataPool()
    override val db: Database

    init {
        db = Database.connect(dp.pool)
        transaction(db) {
           SchemaUtils.create(UsersTable,BillingTable)
        }
    }

    override fun close() {
        dp.pool.close()
    }
}
