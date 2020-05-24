package cache

import database.queries.DataQuery
import kotlinx.coroutines.*
import model.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val log: Logger = LoggerFactory.getLogger(InMemoryCache::class.java)

class InMemoryCache(val dq: DataQuery): DataCache {
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
                jobs.joinAll()
            }
        }
    }


    override fun <T> add(obj: T): Int {
            when (obj) {
                is AccountRep -> {
                    // skip...
                }
                is Billing -> {
                    val result: Int = dq.createBilling(obj)
                    val billing: Billing = obj.copy(counter = result)
                    this.billings.add(billing)
                    return result
                }
                is BillType -> {
                    // skip...
                    return -1
                }
                is ClientFile -> {
                    val result: Int = dq.createClientFile(obj)
                    val cf: ClientFile = obj.copy(client_num = result)
                    this.clientFiles.add(cf)
                    return result
                }
                is ClientNotes -> {
                    val result: Int = dq.createClientNotes(obj)
                    val cn = obj.copy(client_note_key = result)
                    this.clientNotes.add(cn)
                    return result
                }
                is ClientPermNotes -> {
                    val result = dq.createClientPermNotes(obj)
                    val cpn = obj.copy(id = result)
                    this.clientPermNotes.add(cpn)
                    return result
                }
                is DAPSAddress -> {
                    val result = dq.createDAPSAddress(obj)
                    val da = obj.copy(mailing_list_id = result)
                    this.dapsAddress.add(da)
                    return result
                }
                is DAPSStaff -> {
                    // TODO: Revisit this one
                    val result = dq.insertDAPSStaff(obj)
                    this.dapsStaff.add(obj)
                    return 0
                }
                is DAPSStaffMessages -> {
                    val result = dq.createDAPSStaffMessages(obj)
                    val dsm = obj.copy(staff_messages_key = result)
                    this.dapsStaffMessages.add(dsm)
                    return result
                }
                is InterviewGuide -> {
                    val result = dq.createInterviewGuide(obj)
                    val ig = obj.copy(id = result)
                    this.interviewGuides.add(ig)
                    return result
                }
                is JobFunction -> {
                    // skip
                    return -1
                }
                is PasteErrors -> {
                    // TODO: Revisit
                    dq.insertPasteErrors(obj)
                    this.pasteErrors.add(obj)
                    return 0
                }
                is Payment -> {
                    // TODO: Revisit
                    dq.insertPayment(obj)
                    this.payments.add(obj)
                    return 0
                }
                is PermNotes -> {
                    val result = dq.createPermNotes(obj)
                    val pn = obj.copy(id = result)
                    this.permNotes.add(pn)
                    return result
                }
                is PermReqNotes -> {
                    val result = dq.createPermReqNotes(obj)
                    val prn = obj.copy(id = result)
                    this.permReqNotes.add(prn)
                    return result
                }
                is TempNotes -> {
                    val result = dq.createTempNote(obj)
                    val tn = obj.copy(temp_note_key = result)
                    this.tempNotes.add(tn)
                    return result
                }
                is TempsAvail4Work -> {
                    val result = dq.createTempAvail4Work(obj)
                    val ta4w = obj.copy(rec_num = result)
                    this.tempsAvail4Work.add(ta4w)
                    return result
                }
                is Temps -> {
                    val result = dq.createTemps(obj)
                    val temp = obj.copy(emp_num = result)
                    this.temps.add(temp)
                    return result
                }
                is User -> {
                    val result = dq.addUser(obj)
                    val user = obj.copy(id = result)
                    this.users.add(user)
                    return result.toInt()
                }
                is WONotes -> {
                    val result = dq.createWONotes(obj)
                    val wonote = obj.copy(id = result)
                    this.woNotes.add(wonote)
                    return result
                }
                is WorkOrder -> {
                    val result = dq.createWorkOrder(obj)
                    val wo = obj.copy(wo_number = result)
                    this.workOrders.add(wo)
                    return result
                }
            }
        throw IllegalArgumentException("Write Failed. Unknown object.")
    }

    override fun <T> edit(obj: T) {
        TODO("Not yet implemented")
    }

    override fun <T> remove(obj: T) {
        TODO("Not yet implemented")
    }

}
