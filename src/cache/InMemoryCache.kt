package cache

import database.queries.DataQuery
import kotlinx.coroutines.*
import model.*

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
            val deferreds: MutableList<Deferred<Unit>> = mutableListOf()
            withContext(Dispatchers.IO) {
                deferreds.addAll(
                    mutableListOf(
                        async { billings = dq.allBilling().toMutableList() }.also { println("Loading Billings...") },
                        async { clientFiles = dq.allClientFiles().toMutableList()}.also { println("Loading Client Files...")},
                        async { clientNotes = dq.allClientNotes().toMutableList() }.also { println("Loading Client Notes...") },
//                        async { clientPermNotes= dq.allClientPermNotes().toMutableList() },
                        async { dapsAddress = dq.allDAPSAddress().toMutableList() }.also { println("Loading DAPS Address...") },
                        async { dapsStaffMessages = dq.allDAPSStaffMessages().toMutableList() }.also { println("Loading DAPS Staff Messages...") },
                        async { dapsStaff = dq.allDAPSStaff().toMutableList() }.also { println("Loading DAPS Staff...") },
                        async { interviewGuides = dq.allInterviewGuides().toMutableList() }.also { println("Loading Interview Guides...") },
                        async { pasteErrors = dq.allPasteErrors().toMutableList() }.also { println("Loading Paste Errors...") },
                        async { payments = dq.allPayments().toMutableList() }.also { println("Loading Payments...") },
                        async { permNotes = dq.allPermNotes().toMutableList() }.also { println("Loading Perm Notes...") },
                        async { permReqNotes = dq.allPermReqNotes().toMutableList() }.also { println("Loading Perm Req Notes...") },
                        async { tempNotes = dq.allTempNotes().toMutableList() }.also { println("Loading TempNotes... ") },
                        async { tempsAvail4Work = dq.allTempsAvail4Work().toMutableList()}.also { println("Loading Temps Avail 4 Work...") },
                        async { temps = dq.allTemps().toMutableList() }.also { println("Loading all Temps...") },
                        async { users = dq.allUsers().toMutableList() }.also { println("Loading all Users...") },
                        async { woNotes = dq.allWONotes().toMutableList() }.also { println("Loading Work Order Notes...") },
                        async { workOrders = dq.allWorkOrders().toMutableList() }.also { println("Loading Work Orders...") }
                    )
                )
            }
            deferreds.awaitAll()
        }
    }

}
