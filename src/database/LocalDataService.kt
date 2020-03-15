package database
import database.facades.DataService
import database.tables.BillingTable
import database.tables.UsersTable
import org.jetbrains.exposed.sql.Database

class LocalDataService: DataService {
    private val dp: DataPool = DataPool()
    override val db: Database

    init {
        db = Database.connect(dp.pool)
        db.transaction {
            create(UsersTable, BillingTable)
        }
    }

    override fun close() {
        dp.pool.close()
    }
}
