package cache

import database.queries.DataQuery
import kotlinx.coroutines.*
import model.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val log: Logger = LoggerFactory.getLogger(InMemoryCache::class.java)

class InMemoryCache(val dq: DataQuery){
    lateinit var billings: MutableList<Billing>
    lateinit var clientFiles: MutableList<ClientFile>
    lateinit var clientNotes: MutableList<ClientNotes>
    lateinit var clientPermNotes: MutableList<ClientPermNotes>
    lateinit var dapsAddress: MutableList<DAPSAddress>
    lateinit var dapsStaffMessages: MutableList<DAPSStaffMessages>
    lateinit var dapsStaff: MutableList<DAPSStaff>
    lateinit var interviewGuides: MutableList<InterviewGuide>
    lateinit var pasteErrors: MutableList<PasteErrors>
    lateinit var payments: MutableList<Payment>
    lateinit var permNotes: MutableList<PermNotes>
    lateinit var permReqNotes: MutableList<PermReqNotes>
    lateinit var tempNotes: MutableList<TempNotes>
    lateinit var tempsAvail4Work: MutableList<TempsAvail4Work>
    lateinit var temps: MutableList<Temps>
    lateinit var users: MutableList<User>
    lateinit var woNotes: MutableList<WONotes>
    lateinit var workOrders: MutableList<WorkOrder>
    init {
        runBlocking {
            val jobs: MutableList<Job> = mutableListOf()
            withContext(Dispatchers.IO) {
                log.info("Adding data to cache...")
                jobs.addAll(
                    mutableListOf(
                        launch { billings = dq.allBilling().toMutableList() },
                        launch { clientFiles = dq.allClientFiles().toMutableList()},
                        launch { clientNotes = dq.allClientNotes().toMutableList() },
//                        launch { clientPermNotes= dq.allClientPermNotes().toMutableList() },
                        launch { dapsAddress = dq.allDAPSAddress().toMutableList() },
                        launch { dapsStaffMessages = dq.allDAPSStaffMessages().toMutableList() },
                        launch { dapsStaff = dq.allDAPSStaff().toMutableList() },
                        launch { interviewGuides = dq.allInterviewGuides().toMutableList() },
                        launch { pasteErrors = dq.allPasteErrors().toMutableList() },
                        launch { payments = dq.allPayments().toMutableList() },
                        launch { permNotes = dq.allPermNotes().toMutableList() },
                        launch { permReqNotes = dq.allPermReqNotes().toMutableList() },
                        launch { tempNotes = dq.allTempNotes().toMutableList() },
                        launch { tempsAvail4Work = dq.allTempsAvail4Work().toMutableList()},
                        launch { temps = dq.allTemps().toMutableList() },
                        launch { users = dq.allUsers().toMutableList() },
                        launch { woNotes = dq.allWONotes().toMutableList() },
                        launch { workOrders = dq.allWorkOrders().toMutableList() }
                    )
                )
            }
            jobs.joinAll()
        }
    }

}
