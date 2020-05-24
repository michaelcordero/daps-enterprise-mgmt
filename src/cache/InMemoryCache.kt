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
            try {
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
            } catch (e: Exception) {
                log.error("Create failed: ", e)
            }
        throw IllegalArgumentException("Unknown Object")
    }

    override fun <T> edit(obj: T): Int {
        try {
            when(obj) {
                is AccountRep -> {
                    // skip...
                }
                is Billing -> {
                    val result = dq.updateBilling(obj)
                    billings.add(obj)
                    return result
                }
                is BillType -> {
                    // skip...
                }
                is ClientFile -> {
                    val result = dq.updateClientFile(obj)
                    clientFiles.add(obj)
                    return result
                }
                is ClientNotes -> {
                    val result = dq.updateClientNotes(obj)
                    clientNotes.add(obj)
                    return result
                }
                is ClientPermNotes -> {
                    val result = dq.updateClientPermNote(obj)
                    clientPermNotes.add(obj)
                    return result
                }
                is DAPSAddress -> {
                    val result = dq.updateDAPSAddress(obj)
                    dapsAddress.add(obj)
                    return result
                }
                is DAPSStaff -> {
                    val result = dq.updateDAPSStaff(obj)
                    dapsStaff.add(obj)
                    return result
                }
                is DAPSStaffMessages -> {
                    val result = dq.updateDAPSStaffMessages(obj)
                    dapsStaffMessages.add(obj)
                    return result
                }
                is InterviewGuide -> {
                    val result = dq.updateInterviewGuide(obj)
                    interviewGuides.add(obj)
                    return result
                }
                is JobFunction -> {
                    // skip...
                }
                is PasteErrors -> {
                    val result = dq.updatePasteErrors(obj)
                    pasteErrors.add(obj)
                    return result
                }
                is Payment -> {
                    val result = dq.updatePayment(obj)
                    payments.add(obj)
                    return result
                }
                is PermNotes -> {
                    val result = dq.updatePermNotes(obj)
                    permNotes.add(obj)
                    return result
                }
                is PermReqNotes -> {
                    val result = dq.updatePermReqNote(obj)
                    permReqNotes.add(obj)
                    return result
                }
                is TempNotes -> {
                    val result = dq.updateTempNote(obj)
                    tempNotes.add(obj)
                    return result
                }
                is Temps -> {
                    val result = dq.updateTemp(obj)
                    temps.add(obj)
                    return result
                }
                is TempsAvail4Work -> {
                    val result = dq.updateTempAvail4Work(obj)
                    tempsAvail4Work.add(obj)
                    return result
                }
                is User -> {
                    val result = dq.updateUser(obj)
                    users.add(obj)
                    return result
                }
                is WONotes -> {
                    val result = dq.updateWONotes(obj)
                    woNotes.add(obj)
                    return result
                }
                is WorkOrder -> {
                    val result = dq.updateWorkOrder(obj)
                    workOrders.add(obj)
                    return result
                }
            }
        } catch (e: Exception) {
            log.error("Update failed", e)
        }
        throw IllegalArgumentException("Unknown Object")
    }

    override fun <T> remove(obj: T): Int {
        try {
            when(obj) {
                is AccountRep -> {
                    // skip...
                }
                is Billing -> {
                    val result = dq.deleteBilling(obj.counter)
                    return result
                }
                is BillType -> {
                    // skip...
                }
                is ClientFile -> {
                    val result = dq.deleteClientFile(obj.client_num)
                    return result
                }
                is ClientNotes -> {
                    val result = dq.deleteClientNote(obj.client_note_key!!)
                    return result
                }
                is ClientPermNotes -> {
                    val result = dq.deleteClientPermNote(obj.id)
                    return result
                }
                is DAPSAddress -> {
                    val result = dq.deleteDAPSAddress(obj.mailing_list_id!!)
                    return result
                }
                is DAPSStaff -> {
                    // TODO: Add a primary key
                    val result = dq.deleteDAPSStaff(obj)
                    return result
                }
                is DAPSStaffMessages -> {
                    val result = dq.deleteDAPSStaffMessages(obj.staff_messages_key!!)
                    return result
                }
                is InterviewGuide -> {
                    val result = dq.deleteInterviewGuide(obj.id!!)
                    return result
                }
                is JobFunction -> {
                    // skip...
                }
                is PasteErrors -> {
                    // TODO: Add primary key
                    val result = dq.deletePasteErrors(obj)
                    return result
                }
                is Payment -> {
                    // TODO: Add primary key
                    val result = dq.deletePayment(obj)
                    return result
                }
                is PermNotes -> {
                    val result = dq.deletePermNotes(obj.id!!)
                    return result
                }
                is PermReqNotes -> {
                    val result = dq.deletePermReqNote(obj.id!!)
                    return result
                }
                is TempNotes -> {
                    val result = dq.deleteTempNote(obj.temp_note_key!!)
                    return result
                }
                is Temps -> {
                    val result = dq.deleteTemp(obj.emp_num)
                    return result
                }
                is TempsAvail4Work -> {
                    val result = dq.deleteTempAvail4Work(obj.rec_num)
                    return result
                }
                is User -> {
                    val result = dq.deleteUser(obj.id)
                    return result
                }
                is WONotes -> {
                    val result = dq.deleteWONote(obj.id)
                    return result
                }
                is WorkOrder -> {
                    val result = dq.deleteWorkOrder(obj.wo_number)
                    return result
                }
            }
        } catch (e: Exception) {
            log.error("Update failed", e)
        }
        throw IllegalArgumentException("Unknown Object")
    }

    override fun allBilling(): List<Billing> {
        return billings
    }

    override fun allBillTypes(): List<BillType> {
        return emptyList()
    }

    override fun allClientFiles(): List<ClientFile> {
        return clientFiles
    }

    override fun allClientNotes(): List<ClientNotes> {
        return clientNotes
    }

    override fun allClientPermNotes(): List<ClientPermNotes> {
return clientPermNotes
    }

    override fun allDAPSAddress(): List<DAPSAddress> {
        return dapsAddress
    }

    override fun allDAPSStaffMessages(): List<DAPSStaffMessages> {
        return dapsStaffMessages
    }

    override fun allDAPSStaff(): List<DAPSStaff> {
        return dapsStaff
    }

    override fun allInterviewGuides(): List<InterviewGuide> {
        return interviewGuides
    }

    override fun allPasteErrors(): List<PasteErrors> {
        return pasteErrors
    }

    override fun allPayments(): List<Payment> {
        return payments
    }

    override fun allPermNotes(): List<PermNotes> {
        return permNotes
    }

    override fun allPermReqNotes(): List<PermReqNotes> {
        return permReqNotes
    }

    override fun allTempNotes(): List<TempNotes> {
        return tempNotes
    }

    override fun allTempsAvail4Work(): List<TempsAvail4Work> {
        return tempsAvail4Work
    }

    override fun allTemps(): List<Temps> {
        return temps
    }

    override fun allUsers(): List<User> {
        return users
    }

    override fun allWONotes(): List<WONotes> {
        return woNotes
    }

    override fun allWorkOrders(): List<WorkOrder> {
        return workOrders
    }

}
