package cache

import database.queries.DataQuery
import kotlinx.coroutines.*
import model.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.ConcurrentHashMap

val log: Logger = LoggerFactory.getLogger(InMemoryCache::class.java)

class InMemoryCache(val dq: DataQuery): DataCache {
    lateinit var billings: MutableMap<Int,Billing>
    lateinit var clientFiles: MutableMap<Int,ClientFile>
    lateinit var clientNotes: MutableMap<Int?,ClientNote>
    lateinit var clientPermNotes: MutableMap<Int,ClientPermNotes>
    lateinit var dapsAddress: MutableMap<Int?,DAPSAddress>
    lateinit var dapsStaffMessages: MutableMap<Int?,DAPSStaffMessage>
    lateinit var dapsStaff: MutableMap<String?,DAPSStaff>
    lateinit var interviewGuides: MutableMap<Int?,InterviewGuide>
    lateinit var pasteErrors: MutableMap<String?,PasteErrors>
    lateinit var payments: MutableMap<String?,Payment>
    lateinit var permNotes: MutableMap<Int?,PermNote>
    lateinit var permReqNotes: MutableMap<Int?,PermReqNote>
    lateinit var tempNotes: MutableMap<Int?,TempNote>
    lateinit var tempsAvail4Work: MutableMap<Int?,TempsAvail4Work>
    lateinit var temps: MutableMap<Int?,Temp>
    lateinit var users: MutableMap<Long?,User>
    lateinit var woNotes: MutableMap<Int?,WONotes>
    lateinit var workOrders: MutableMap<Int?,WorkOrder>
    init {
        runBlocking {
            val jobs: MutableList<Job> = mutableListOf()
            withContext(Dispatchers.IO) {
                log.info("Adding data to cache...")
                jobs.addAll(
                    mutableListOf(
                        launch { billings = ConcurrentHashMap(dq.allBilling().associateBy { it.counter }.toMutableMap()) },
                        launch { clientFiles = ConcurrentHashMap(dq.allClientFiles().associateBy { it.client_num }.toMutableMap())},
                        launch { clientNotes = ConcurrentHashMap(dq.allClientNotes().associateBy { it.client_note_key }.toMutableMap())},
//                        launch { clientPermNotes= dq.allClientPermNotes().toMutableList() },
                        launch { dapsAddress = ConcurrentHashMap(dq.allDAPSAddress().associateBy { it.mailing_list_id}.toMutableMap()) },
                        launch { dapsStaffMessages = ConcurrentHashMap(dq.allDAPSStaffMessages().associateBy { it.staff_messages_key }.toMutableMap()) },
                        launch { dapsStaff = ConcurrentHashMap(dq.allDAPSStaff().associateBy { it.initial }.toMutableMap()) },
                        launch { interviewGuides = ConcurrentHashMap(dq.allInterviewGuides().associateBy { it.id }.toMutableMap()) },
                        launch { pasteErrors = ConcurrentHashMap(dq.allPasteErrors().associateBy { it.ref_num }.toMutableMap()) },
                        launch { payments = ConcurrentHashMap(dq.allPayments().associateBy { it.ref_num }.toMutableMap()) },
                        launch { permNotes = ConcurrentHashMap(dq.allPermNotes().associateBy { it.id }.toMutableMap()) },
                        launch { permReqNotes = ConcurrentHashMap(dq.allPermReqNotes().associateBy { it.id }.toMutableMap()) },
                        launch { tempNotes = ConcurrentHashMap(dq.allTempNotes().associateBy { it.temp_note_key }.toMutableMap()) },
                        launch { tempsAvail4Work = ConcurrentHashMap(dq.allTempsAvail4Work().associateBy { it.rec_num }.toMutableMap())},
                        launch { temps = ConcurrentHashMap(dq.allTemps().associateBy { it.emp_num }.toMutableMap()) },
                        launch { users = ConcurrentHashMap(dq.allUsers().associateBy { it.id }.toMutableMap()) },
                        launch { woNotes = ConcurrentHashMap(dq.allWONotes().associateBy { it.id }.toMutableMap()) },
                        launch { workOrders = ConcurrentHashMap(dq.allWorkOrders().associateBy { it.wo_number }.toMutableMap()) }
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
                        billings[billing.counter] = billing
                        return result
                    }
                    is BillType -> {
                        // skip...
                        return -1
                    }
                    is ClientFile -> {
                        val result: Int = dq.createClientFile(obj)
                        val cf: ClientFile = obj.copy(client_num = result)
                        clientFiles[cf.client_num] = cf
                        return result
                    }
                    is ClientNote -> {
                        val result: Int = dq.createClientNotes(obj)
                        val cn = obj.copy(client_note_key = result)
                        clientNotes[cn.client_note_key] = cn
                        return result
                    }
                    is ClientPermNotes -> {
                        val result = dq.createClientPermNotes(obj)
                        val cpn = obj.copy(id = result)
                        clientPermNotes[cpn.id] = cpn
                        return result
                    }
                    is DAPSAddress -> {
                        val result = dq.createDAPSAddress(obj)
                        val da = obj.copy(mailing_list_id = result)
                        dapsAddress[da.mailing_list_id] = da
                        return result
                    }
                    is DAPSStaff -> {
                        // TODO: Revisit this one
                        dq.insertDAPSStaff(obj)
                        dapsStaff[obj.initial] = obj
                        return 0
                    }
                    is DAPSStaffMessage -> {
                        val result = dq.createDAPSStaffMessages(obj)
                        val dsm = obj.copy(staff_messages_key = result)
                        dapsStaffMessages[dsm.staff_messages_key] = dsm
                        return result
                    }
                    is InterviewGuide -> {
                        val result = dq.createInterviewGuide(obj)
                        val ig = obj.copy(id = result)
                        interviewGuides[ig.id] = ig
                        return result
                    }
                    is JobFunction -> {
                        // skip
                        return -1
                    }
                    is PasteErrors -> {
                        // TODO: Revisit
                        dq.insertPasteErrors(obj)
                        pasteErrors[obj.ref_num] = obj
                        return 0
                    }
                    is Payment -> {
                        // TODO: Revisit
                        dq.insertPayment(obj)
                        payments[obj.ref_num] = obj
                        return 0
                    }
                    is PermNote -> {
                        val result = dq.createPermNotes(obj)
                        val pn = obj.copy(id = result)
                        permNotes[pn.id] = pn
                        return result
                    }
                    is PermReqNote -> {
                        val result = dq.createPermReqNotes(obj)
                        val prn = obj.copy(id = result)
                        permReqNotes[prn.id] = prn
                        return result
                    }
                    is TempNote -> {
                        val result = dq.createTempNote(obj)
                        val tn = obj.copy(temp_note_key = result)
                        tempNotes[tn.temp_note_key] = tn
                        return result
                    }
                    is TempsAvail4Work -> {
                        val result = dq.createTempAvail4Work(obj)
                        val ta4w = obj.copy(rec_num = result)
                        tempsAvail4Work[ta4w.rec_num] = ta4w
                        return result
                    }
                    is Temp -> {
                        val result = dq.createTemps(obj)
                        val temp = obj.copy(emp_num = result)
                        temps[temp.emp_num] = temp
                        return result
                    }
                    is User -> {
                        val result = dq.addUser(obj)
                        val user = obj.copy(id = result)
                        users[user.id] = user
                        return result.toInt()
                    }
                    is WONotes -> {
                        val result = dq.createWONotes(obj)
                        val wonote = obj.copy(id = result)
                        woNotes[wonote.id] = wonote
                        return result
                    }
                    is WorkOrder -> {
                        val result = dq.createWorkOrder(obj)
                        val wo = obj.copy(wo_number = result)
                        workOrders[wo.wo_number] = wo
                        return result
                    }
                }
            } catch (e: Exception) {
                log.error("Create failed: ", e)
            }
        throw IllegalArgumentException("Unknown Object")
    }

    override fun <T> edit(obj: T) {
            try {
                when(obj) {
                    is AccountRep -> {
                        // skip...
                    }
                    is Billing -> {
                        val old = billings.replace(obj.counter,obj)
                        val job = GlobalScope.launch { dq.updateBilling(obj) }
                    }
                    is BillType -> {
                        // skip...
                    }
                    is ClientFile -> {
                        val old = clientFiles.replace(obj.client_num,obj)
                        val job = GlobalScope.launch { dq.updateClientFile(obj) }
                    }
                    is ClientNote -> {
                        val old = clientNotes.replace(obj.client_note_key,obj)
                        val job = GlobalScope.launch { dq.updateClientNotes(obj) }
                    }
                    is ClientPermNotes -> {
                        val old = clientPermNotes.replace(obj.id,obj)
                        val job = GlobalScope.launch { dq.updateClientPermNote(obj) }
                    }
                    is DAPSAddress -> {
                        val job = GlobalScope.launch { dq.updateDAPSAddress(obj) }
                        val old = dapsAddress.replace(obj.mailing_list_id,obj)
                    }
                    is DAPSStaff -> {
                        val job = GlobalScope.launch { dq.updateDAPSStaff(obj) }
                        val old = dapsStaff.replace(obj.initial,obj)
                    }
                    is DAPSStaffMessage -> {
                        val job = GlobalScope.launch { dq.updateDAPSStaffMessages(obj) }
                        val old = dapsStaffMessages.replace(obj.staff_messages_key,obj)
                    }
                    is InterviewGuide -> {
                        val job = GlobalScope.launch { dq.updateInterviewGuide(obj) }
                        val old = interviewGuides.replace(obj.id,obj)
                    }
                    is JobFunction -> {
                        // skip...
                    }
                    is PasteErrors -> {
                        val job = GlobalScope.launch { dq.updatePasteErrors(obj) }
                        val old = pasteErrors.replace(obj.ref_num, obj)
                    }
                    is Payment -> {
                        val job = GlobalScope.launch { dq.updatePayment(obj) }
                        val old = payments.replace(obj.ref_num,obj)
                    }
                    is PermNote -> {
                        val job = GlobalScope.launch { dq.updatePermNotes(obj) }
                        val old = permNotes.replace(obj.id,obj)
                    }
                    is PermReqNote -> {
                        val job = GlobalScope.launch { dq.updatePermReqNote(obj) }
                        val old = permReqNotes.replace(obj.id,obj)
                    }
                    is TempNote -> {
                        val job = GlobalScope.launch { dq.updateTempNote(obj) }
                        val old = tempNotes.replace(obj.temp_note_key,obj)
                    }
                    is Temp -> {
                        val job = GlobalScope.launch { dq.updateTemp(obj) }
                        val old = temps.replace(obj.emp_num,obj)
                    }
                    is TempsAvail4Work -> {
                        val job = GlobalScope.launch { dq.updateTempAvail4Work(obj) }
                        val old = tempsAvail4Work.replace(obj.rec_num, obj)
                    }
                    is User -> {
                        val job = GlobalScope.launch { dq.updateUser(obj) }
                        val old = users.replace(obj.id,obj)
                    }
                    is WONotes -> {
                        val job = dq.updateWONotes(obj)
                        val old = woNotes.replace(obj.id, obj)
                    }
                    is WorkOrder -> {
                        val job = GlobalScope.launch { dq.updateWorkOrder(obj) }
                        val old = workOrders.replace(obj.wo_number,obj)
                    }
                    else ->  throw IllegalArgumentException("Unknown Object")
                }
            } catch (e: Exception) {
                log.error("Update failed", e)
            }
    }

    override fun <T> remove(obj: T) {
        try {
            when(obj) {
                is AccountRep -> {
                    // skip...
                }
                is Billing -> {
                    val job = GlobalScope.launch { dq.deleteBilling(obj.counter) }
                    val old = billings.remove(obj.counter)
                }
                is BillType -> {
                    // skip...
                }
                is ClientFile -> {
                    val job = GlobalScope.launch { dq.deleteClientFile(obj.client_num) }
                    val old = clientFiles.remove(obj.client_num)
                }
                is ClientNote -> {
                    val job = GlobalScope.launch { dq.deleteClientNote(obj.client_note_key!!) }
                    val old = clientNotes.remove(obj.client_note_key)
                }
                is ClientPermNotes -> {
                    val job = GlobalScope.launch { dq.deleteClientPermNote(obj.id) }
                    val old = clientPermNotes.remove(obj.id)
                }
                is DAPSAddress -> {
                    val job = GlobalScope.launch { dq.deleteDAPSAddress(obj.mailing_list_id!!) }
                    val old = dapsAddress.remove(obj.mailing_list_id)
                }
                is DAPSStaff -> {
                    // TODO: Add a primary key
                    val job = GlobalScope.launch { dq.deleteDAPSStaff(obj) }
                    val old = dapsStaff.remove(obj.initial)
                }
                is DAPSStaffMessage -> {
                    val job = GlobalScope.launch { dq.deleteDAPSStaffMessages(obj.staff_messages_key!!) }
                    val old = dapsStaffMessages.remove(obj.staff_messages_key)
                }
                is InterviewGuide -> {
                    val job = GlobalScope.launch { dq.deleteInterviewGuide(obj.id!!) }
                    val old = interviewGuides.remove(obj.id)
                }
                is JobFunction -> {
                    // skip...
                }
                is PasteErrors -> {
                    // TODO: Add primary key
                    val job = GlobalScope.launch { dq.deletePasteErrors(obj) }
                    val old = pasteErrors.remove(obj.ref_num)
                }
                is Payment -> {
                    // TODO: Add primary key
                    val job = GlobalScope.launch { dq.deletePayment(obj) }
                    val old = payments.remove(obj.ref_num)
                }
                is PermNote -> {
                    val job = GlobalScope.launch { dq.deletePermNotes(obj.id!!) }
                    val old = permNotes.remove(obj.id)
                }
                is PermReqNote -> {
                    val job = GlobalScope.launch { dq.deletePermReqNote(obj.id!!) }
                    val old = permReqNotes.remove(obj.id)
                }
                is TempNote -> {
                    val job = GlobalScope.launch { dq.deleteTempNote(obj.temp_note_key!!) }
                    val old = tempNotes.remove(obj.temp_note_key)
                }
                is Temp -> {
                    val job = GlobalScope.launch { dq.deleteTemp(obj.emp_num) }
                    val old = temps.remove(obj.emp_num)
                }
                is TempsAvail4Work -> {
                    val job = GlobalScope.launch { dq.deleteTempAvail4Work(obj.rec_num) }
                    val old = tempsAvail4Work.remove(obj.rec_num)
                }
                is User -> {
                    val job = GlobalScope.launch { dq.deleteUser(obj.id) }
                    val old = users.remove(obj.id)
                }
                is WONotes -> {
                    val job = GlobalScope.launch { dq.deleteWONote(obj.id) }
                    val old = woNotes.remove(obj.id)
                }
                is WorkOrder -> {
                    val job = GlobalScope.launch { dq.deleteWorkOrder(obj.wo_number) }
                    val old = workOrders.remove(obj.wo_number)
                }
            }
        } catch (e: Exception) {
            log.error("Update failed", e)
        }
        throw IllegalArgumentException("Unknown Object")
    }

    override fun allBilling(): Map<Int,Billing> {
        return billings
    }

    override fun allBillTypes(): Map<String,BillType> {
        return emptyMap()
    }

    override fun allClientFiles(): Map<Int,ClientFile> {
        return clientFiles
    }

    override fun allClientNotes(): Map<Int?,ClientNote> {
        return clientNotes
    }

    override fun allClientPermNotes(): Map<Int, ClientPermNotes> {
        return clientPermNotes
    }

    override fun allDAPSAddress(): Map<Int?, DAPSAddress> {
        return dapsAddress
    }

    override fun allDAPSStaffMessages(): Map<Int?, DAPSStaffMessage> {
        return dapsStaffMessages
    }

    override fun allDAPSStaff(): Map<String?,DAPSStaff> {
        return dapsStaff
    }

    override fun allInterviewGuides(): Map<Int?, InterviewGuide> {
        return interviewGuides
    }

    override fun allPasteErrors(): Map<String?, PasteErrors> {
        return pasteErrors
    }

    override fun allPayments(): Map<String?, Payment> {
        return payments
    }

    override fun allPermNotes(): Map<Int?, PermNote> {
        return permNotes
    }

    override fun allPermReqNotes(): Map<Int?, PermReqNote> {
        return permReqNotes
    }

    override fun allTempNotes(): Map<Int?, TempNote> {
        return tempNotes
    }

    override fun allTempsAvail4Work(): Map<Int?, TempsAvail4Work> {
        return tempsAvail4Work
    }

    override fun allTemps(): Map<Int?, Temp> {
        return temps
    }

    override fun allUsers(): Map<Long?, User> {
        return users
    }

    override fun allWONotes(): Map<Int?, WONotes> {
        return woNotes
    }

    override fun allWorkOrders(): Map<Int?, WorkOrder> {
        return workOrders
    }

}
