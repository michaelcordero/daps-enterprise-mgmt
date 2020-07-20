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
    lateinit var temps: MutableList<Temp>
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
                        billings.add(billing)
                        return result
                    }
                    is BillType -> {
                        // skip...
                        return -1
                    }
                    is ClientFile -> {
                        val result: Int = dq.createClientFile(obj)
                        val cf: ClientFile = obj.copy(client_num = result)
                        clientFiles.add(cf)
                        return result
                    }
                    is ClientNotes -> {
                        val result: Int = dq.createClientNotes(obj)
                        val cn = obj.copy(client_note_key = result)
                        clientNotes.add(cn)
                        return result
                    }
                    is ClientPermNotes -> {
                        val result = dq.createClientPermNotes(obj)
                        val cpn = obj.copy(id = result)
                        clientPermNotes.add(cpn)
                        return result
                    }
                    is DAPSAddress -> {
                        val result = dq.createDAPSAddress(obj)
                        val da = obj.copy(mailing_list_id = result)
                        dapsAddress.add(da)
                        return result
                    }
                    is DAPSStaff -> {
                        // TODO: Revisit this one
                        dq.insertDAPSStaff(obj)
                        dapsStaff.add(obj)
                        return 0
                    }
                    is DAPSStaffMessages -> {
                        val result = dq.createDAPSStaffMessages(obj)
                        val dsm = obj.copy(staff_messages_key = result)
                        dapsStaffMessages.add(dsm)
                        return result
                    }
                    is InterviewGuide -> {
                        val result = dq.createInterviewGuide(obj)
                        val ig = obj.copy(id = result)
                        interviewGuides.add(ig)
                        return result
                    }
                    is JobFunction -> {
                        // skip
                        return -1
                    }
                    is PasteErrors -> {
                        // TODO: Revisit
                        dq.insertPasteErrors(obj)
                        pasteErrors.add(obj)
                        return 0
                    }
                    is Payment -> {
                        // TODO: Revisit
                        dq.insertPayment(obj)
                        payments.add(obj)
                        return 0
                    }
                    is PermNotes -> {
                        val result = dq.createPermNotes(obj)
                        val pn = obj.copy(id = result)
                        permNotes.add(pn)
                        return result
                    }
                    is PermReqNotes -> {
                        val result = dq.createPermReqNotes(obj)
                        val prn = obj.copy(id = result)
                        permReqNotes.add(prn)
                        return result
                    }
                    is TempNotes -> {
                        val result = dq.createTempNote(obj)
                        val tn = obj.copy(temp_note_key = result)
                        tempNotes.add(tn)
                        return result
                    }
                    is TempsAvail4Work -> {
                        val result = dq.createTempAvail4Work(obj)
                        val ta4w = obj.copy(rec_num = result)
                        tempsAvail4Work.add(ta4w)
                        return result
                    }
                    is Temp -> {
                        val result = dq.createTemps(obj)
                        val temp = obj.copy(emp_num = result)
                        temps.add(temp)
                        return result
                    }
                    is User -> {
                        val result = dq.addUser(obj)
                        val user = obj.copy(id = result)
                        users.add(user)
                        return result.toInt()
                    }
                    is WONotes -> {
                        val result = dq.createWONotes(obj)
                        val wonote = obj.copy(id = result)
                        woNotes.add(wonote)
                        return result
                    }
                    is WorkOrder -> {
                        val result = dq.createWorkOrder(obj)
                        val wo = obj.copy(wo_number = result)
                        workOrders.add(wo)
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
                    billings.removeIf{b -> b.counter == obj.counter}
                    billings.add(obj)
                    return result
                }
                is BillType -> {
                    // skip...
                }
                is ClientFile -> {
                    val result = dq.updateClientFile(obj)
                    clientFiles.removeIf { cf -> cf.client_num == obj.client_num }
                    clientFiles.add(obj)
                    return result
                }
                is ClientNotes -> {
                    val result = dq.updateClientNotes(obj)
                    clientNotes.removeIf { cn -> cn.client_note_key == obj.client_note_key }
                    clientNotes.add(obj)
                    return result
                }
                is ClientPermNotes -> {
                    val result = dq.updateClientPermNote(obj)
                    clientPermNotes.removeIf { cpn -> cpn.id == obj.id }
                    clientPermNotes.add(obj)
                    return result
                }
                is DAPSAddress -> {
                    val result = dq.updateDAPSAddress(obj)
                    dapsAddress.removeIf { da -> da.mailing_list_id == obj.mailing_list_id }
                    dapsAddress.add(obj)
                    return result
                }
                is DAPSStaff -> {
                    val result = dq.updateDAPSStaff(obj)
                    dapsStaff.removeIf { ds -> ds.initial == obj.initial }
                    dapsStaff.add(obj)
                    return result
                }
                is DAPSStaffMessages -> {
                    val result = dq.updateDAPSStaffMessages(obj)
                    dapsStaffMessages.removeIf { dsm -> dsm.staff_messages_key == obj.staff_messages_key }
                    dapsStaffMessages.add(obj)
                    return result
                }
                is InterviewGuide -> {
                    val result = dq.updateInterviewGuide(obj)
                    interviewGuides.removeIf { ig -> ig.id == obj.id }
                    interviewGuides.add(obj)
                    return result
                }
                is JobFunction -> {
                    // skip...
                }
                is PasteErrors -> {
                    val result = dq.updatePasteErrors(obj)
                    pasteErrors.removeIf { pe -> pe.ref_num == obj.ref_num }
                    pasteErrors.add(obj)
                    return result
                }
                is Payment -> {
                    val result = dq.updatePayment(obj)
                    payments.removeIf { p -> p.ref_num == obj.ref_num }
                    payments.add(obj)
                    return result
                }
                is PermNotes -> {
                    val result = dq.updatePermNotes(obj)
                    permNotes.removeIf { pn -> pn.id == obj.id }
                    permNotes.add(obj)
                    return result
                }
                is PermReqNotes -> {
                    val result = dq.updatePermReqNote(obj)
                    permReqNotes.removeIf { prn -> prn.id == obj.id }
                    permReqNotes.add(obj)
                    return result
                }
                is TempNotes -> {
                    val result = dq.updateTempNote(obj)
                    tempNotes.removeIf { tn -> tn.temp_note_key == obj.temp_note_key }
                    tempNotes.add(obj)
                    return result
                }
                is Temp -> {
                    val result = dq.updateTemp(obj)
                    temps.removeIf { t -> t.emp_num == obj.emp_num }
                    temps.add(obj)
                    return result
                }
                is TempsAvail4Work -> {
                    val result = dq.updateTempAvail4Work(obj)
                    tempsAvail4Work.removeIf { ta4w -> ta4w.rec_num == obj.rec_num }
                    tempsAvail4Work.add(obj)
                    return result
                }
                is User -> {
                    val result = dq.updateUser(obj)
                    users.removeIf { u -> u.id == obj.id }
                    users.add(obj)
                    return result
                }
                is WONotes -> {
                    val result = dq.updateWONotes(obj)
                    woNotes.removeIf { wn -> wn.id == obj.id }
                    woNotes.add(obj)
                    return result
                }
                is WorkOrder -> {
                    val result = dq.updateWorkOrder(obj)
                    workOrders.removeIf { wo -> wo.wo_number == obj.wo_number }
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
                    billings.removeIf{b -> b.counter == obj.counter}
                    return result
                }
                is BillType -> {
                    // skip...
                }
                is ClientFile -> {
                    val result = dq.deleteClientFile(obj.client_num)
                    clientFiles.removeIf { cf -> cf.client_num == obj.client_num }
                    return result
                }
                is ClientNotes -> {
                    val result = dq.deleteClientNote(obj.client_note_key!!)
                    clientNotes.removeIf { cn -> cn.client_note_key == obj.client_note_key }
                    return result
                }
                is ClientPermNotes -> {
                    val result = dq.deleteClientPermNote(obj.id)
                    clientPermNotes.removeIf { cpn -> cpn.id == obj.id }
                    return result
                }
                is DAPSAddress -> {
                    val result = dq.deleteDAPSAddress(obj.mailing_list_id!!)
                    dapsAddress.removeIf { da -> da.mailing_list_id == obj.mailing_list_id }
                    return result
                }
                is DAPSStaff -> {
                    // TODO: Add a primary key
                    val result = dq.deleteDAPSStaff(obj)
                    dapsStaff.remove(obj)
                    return result
                }
                is DAPSStaffMessages -> {
                    val result = dq.deleteDAPSStaffMessages(obj.staff_messages_key!!)
                    dapsStaffMessages.removeIf { dsm -> dsm.staff_messages_key == obj.staff_messages_key }
                    return result
                }
                is InterviewGuide -> {
                    val result = dq.deleteInterviewGuide(obj.id!!)
                    interviewGuides.removeIf { ig -> ig.id == obj.id }
                    return result
                }
                is JobFunction -> {
                    // skip...
                }
                is PasteErrors -> {
                    // TODO: Add primary key
                    val result = dq.deletePasteErrors(obj)
                    pasteErrors.remove(obj)
                    return result
                }
                is Payment -> {
                    // TODO: Add primary key
                    val result = dq.deletePayment(obj)
                    payments.remove(obj)
                    return result
                }
                is PermNotes -> {
                    val result = dq.deletePermNotes(obj.id!!)
                    permNotes.removeIf { pn -> pn.id == obj.id }
                    return result
                }
                is PermReqNotes -> {
                    val result = dq.deletePermReqNote(obj.id!!)
                    permReqNotes.removeIf { prn -> prn.id == obj.id }
                    return result
                }
                is TempNotes -> {
                    val result = dq.deleteTempNote(obj.temp_note_key!!)
                    tempNotes.removeIf { tn -> tn.temp_note_key == obj.temp_note_key }
                    return result
                }
                is Temp -> {
                    val result = dq.deleteTemp(obj.emp_num)
                    temps.removeIf { t -> t.emp_num == obj.emp_num }
                    return result
                }
                is TempsAvail4Work -> {
                    val result = dq.deleteTempAvail4Work(obj.rec_num)
                    tempsAvail4Work.removeIf { ta4w -> ta4w.rec_num == obj.rec_num }
                    return result
                }
                is User -> {
                    val result = dq.deleteUser(obj.id)
                    users.removeIf { u -> u.id == obj.id }
                    return result
                }
                is WONotes -> {
                    val result = dq.deleteWONote(obj.id)
                    woNotes.removeIf { wn -> wn.id == obj.id }
                    return result
                }
                is WorkOrder -> {
                    val result = dq.deleteWorkOrder(obj.wo_number)
                    workOrders.removeIf { wo -> wo.wo_number == obj.wo_number }
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

    override fun allTemps(): List<Temp> {
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
