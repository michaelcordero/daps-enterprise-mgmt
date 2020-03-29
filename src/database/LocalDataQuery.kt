package database
import database.queries.DataQuery
import database.tables.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class LocalDataQuery: DataQuery {
    private val dp: DataPool = DataPool()
    override val db: Database

    init {
        db = Database.connect(dp.pool)
        transaction(db) {
           SchemaUtils.create(AccountRepDropDownTable,BillingTable,BillTypeDropDownTable, ClientFileTable, ClientNotesTable,
           ClientPermNotesTable, DAPSAddressTable, DAPSStaffMessagesTable, DAPSStaffTable, InterviewGuideTable,
           JobFunctionDropDownTable, PasteErrorsTable, PasteErrorsTable, PaymentTable, PermNotesTable, PermReqNotesTable,
           TempNotesTable, TempsAvail4WorkTable, TempsTable,  UsersTable, WONotesTable, WorkOrderTable)
        }
    }

    override fun close() {
        dp.pool.close()
    }
}
