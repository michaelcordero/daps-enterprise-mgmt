package cache

import application.connections
import database.queries.DataQuery
import io.ktor.http.cio.websocket.Frame
import kotlinx.coroutines.*
import model.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import security.DAPSSession
import java.util.concurrent.ConcurrentHashMap

val log: Logger = LoggerFactory.getLogger(InMemoryCache::class.java)

class InMemoryCache(private val dq: DataQuery) : DataCache {
    private lateinit var billings: MutableMap<Int, Billing>
    private lateinit var clientFiles: MutableMap<Int, ClientFile>
    private lateinit var clientNotes: MutableMap<Int?, ClientNote>
    private lateinit var clientPermNotes: MutableMap<Int, ClientPermNotes>
    private lateinit var dapsAddress: MutableMap<Int?, DAPSAddress>
    private lateinit var dapsStaffMessages: MutableMap<Int?, DAPSStaffMessage>
    private lateinit var dapsStaff: MutableMap<String?, DAPSStaff>
    private lateinit var interviewGuides: MutableMap<Int?, InterviewGuide>
    private lateinit var pasteErrors: MutableMap<String?, PasteErrors>
    private lateinit var payments: MutableMap<String?, Payment>
    private lateinit var permNotes: MutableMap<Int?, PermNote>
    private lateinit var permReqNotes: MutableMap<Int?, PermReqNote>
    private lateinit var tempNotes: MutableMap<Int?, TempNote>
    private lateinit var tempsAvail4Work: MutableMap<Int?, TempsAvail4Work>
    private lateinit var temps: MutableMap<Int?, Temp>
    private lateinit var users: MutableMap<Long?, User>
    private lateinit var woNotes: MutableMap<Int?, WONotes>
    private lateinit var workOrders: MutableMap<Int?, WorkOrder>

    /**
     * Front loading all the data onto the JVM's heap space is an OK performance optimization because:
     * 1. It saves us a slow read step from hard disk. When the java objects are already in memory, the
     * the only step left in application code is the Jackson JSON conversion, with regards to the
     * HTTP Request-Response life-cycle.
     * 2. Computers are being built with more & more RAM. This program was written on a 32GB RAM machine.
     * JVM heap space defaults to 1/4 of the total RAM. Therefore 8GB'S were allocated. Present data only
     * takes up about 1.5 GB's. So we're good.
     */
    init {
        runBlocking {
            val jobs: MutableList<Job> = mutableListOf()
            withContext(Dispatchers.IO) {
                log.info("Adding data to cache...")
                jobs.addAll(
                    mutableListOf(
                        launch {
                            billings = ConcurrentHashMap(dq.allBilling().associateBy { it.counter }.toMutableMap())
                        },
                        launch {
                            clientFiles =
                                ConcurrentHashMap(dq.allClientFiles().associateBy { it.client_num }.toMutableMap())
                        },
                        launch {
                            clientNotes =
                                ConcurrentHashMap(dq.allClientNotes().associateBy { it.client_note_key }.toMutableMap())
                        },
//                        launch { clientPermNotes= dq.allClientPermNotes().toMutableList() },
                        launch {
                            dapsAddress =
                                ConcurrentHashMap(dq.allDAPSAddress().associateBy { it.mailing_list_id }.toMutableMap())
                        },
                        launch {
                            dapsStaffMessages =
                                ConcurrentHashMap(dq.allDAPSStaffMessages().associateBy { it.staff_messages_key }
                                    .toMutableMap())
                        },
                        launch {
                            dapsStaff = ConcurrentHashMap(dq.allDAPSStaff().associateBy { it.initial }.toMutableMap())
                        },
                        launch {
                            interviewGuides =
                                ConcurrentHashMap(dq.allInterviewGuides().associateBy { it.id }.toMutableMap())
                        },
                        launch {
                            pasteErrors =
                                ConcurrentHashMap(dq.allPasteErrors().associateBy { it.ref_num }.toMutableMap())
                        },
                        launch {
                            payments = ConcurrentHashMap(dq.allPayments().associateBy { it.ref_num }.toMutableMap())
                        },
                        launch {
                            permNotes = ConcurrentHashMap(dq.allPermNotes().associateBy { it.id }.toMutableMap())
                        },
                        launch {
                            permReqNotes = ConcurrentHashMap(dq.allPermReqNotes().associateBy { it.id }.toMutableMap())
                        },
                        launch {
                            tempNotes =
                                ConcurrentHashMap(dq.allTempNotes().associateBy { it.temp_note_key }.toMutableMap())
                        },
                        launch {
                            tempsAvail4Work =
                                ConcurrentHashMap(dq.allTempsAvail4Work().associateBy { it.rec_num }.toMutableMap())
                        },
                        launch { temps = ConcurrentHashMap(dq.allTemps().associateBy { it.emp_num }.toMutableMap()) },
                        launch { users = ConcurrentHashMap(dq.allUsers().associateBy { it.id }.toMutableMap()) },
                        launch { woNotes = ConcurrentHashMap(dq.allWONotes().associateBy { it.id }.toMutableMap()) },
                        launch {
                            workOrders =
                                ConcurrentHashMap(dq.allWorkOrders().associateBy { it.wo_number }.toMutableMap())
                        }
                    )
                )
                jobs.joinAll()
            }
        }
    }

    /**
     * The purpose of this method is to check if an exception occurred in an asynchronous write thread
     * whose data change failed. Hopefully it will never have to be called.
     * @param job = represents the asynchronous thread
     * @param key = usually the primary key of the data table
     * @param old = the previous value to be re-written to the cache
     * @param map = the cached representation of the data
     */
    private fun <K, V> exchequer(
        job: Job,
        key: K,
        old: V,
        map: MutableMap<K, V>,
        route: String,
        session: DAPSSession
    ) {
        job.invokeOnCompletion { throwable ->
            // Fail Case
            if (throwable != null) {
                map.replace(key, old)
                CoroutineScope(Dispatchers.IO).launch {
                    connections[session.sessionId]?.outgoing?.send(Frame.Text("alert:${route}"))
                }
            }
            // Success Case, tell the others.
            else {
                CoroutineScope(Dispatchers.IO).launch {
                    connections.entries.forEach { entry ->
                        if (entry.key != session.sessionId) {
                            entry.value.outgoing.send(Frame.Text(route))
                        }
                    }
                }
            }
        }
    }

    /**
     * Create methods need to run synchronously in order to get the result key, from the relational database.
     * However, broadcasting to other web socket connections--asynchronously-- about the newly created row is permissible.
     * Therefore, this method does just that.
     * @param session we need the session id, so we can broadcast to the other web socket sessions, and not the creating
     * session of the new row, because his view will be updated already.
     * @param route tells the listening web session socket connections,
     */
    private suspend fun notifier(session: DAPSSession, route: String) {
        connections.entries.forEach {
            if (it.key != session.sessionId) {
                it.value.outgoing.send(Frame.Text(route))
            }
        }
    }


    override fun <T> add(obj: T, session: DAPSSession): Int {
        try {
            when (obj) {
                is AccountRep -> {
                    // skip...
                }
                is Billing -> {
                    val result: Int = dq.createBilling(obj)
                    val billing: Billing = obj.copy(counter = result)
                    billings[billing.counter] = billing
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session, "billings")
                    }
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
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session, "clients")
                    }
                    return result
                }
                is ClientNote -> {
                    val result: Int = dq.createClientNotes(obj)
                    val cn = obj.copy(client_note_key = result)
                    clientNotes[cn.client_note_key] = cn
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session, "client_notes")
                    }
                    return result
                }
                is ClientPermNotes -> {
                    val result = dq.createClientPermNotes(obj)
                    val cpn = obj.copy(id = result)
                    clientPermNotes[cpn.id] = cpn
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session, "client_perm_notes")
                    }
                    return result
                }
                is DAPSAddress -> {
                    val result = dq.createDAPSAddress(obj)
                    val da = obj.copy(mailing_list_id = result)
                    dapsAddress[da.mailing_list_id] = da
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session, "daps_addresses")
                    }
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
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session, "daps_staff_messages")
                    }
                    return result
                }
                is InterviewGuide -> {
                    val result = dq.createInterviewGuide(obj)
                    val ig = obj.copy(id = result)
                    interviewGuides[ig.id] = ig
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session, "interview_guides")
                    }
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
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session, "paste_errors")
                    }
                    return 0
                }
                is Payment -> {
                    // TODO: Revisit
                    dq.insertPayment(obj)
                    payments[obj.ref_num] = obj
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session, "payments")
                    }
                    return 0
                }
                is PermNote -> {
                    val result = dq.createPermNotes(obj)
                    val pn = obj.copy(id = result)
                    permNotes[pn.id] = pn
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session, "perm_notes")
                    }
                    return result
                }
                is PermReqNote -> {
                    val result = dq.createPermReqNotes(obj)
                    val prn = obj.copy(id = result)
                    permReqNotes[prn.id] = prn
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session, "perm_req_notes")
                    }
                    return result
                }
                is TempNote -> {
                    val result = dq.createTempNote(obj)
                    val tn = obj.copy(temp_note_key = result)
                    tempNotes[tn.temp_note_key] = tn
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session, "tempnotes")
                    }
                    return result
                }
                is TempsAvail4Work -> {
                    val result = dq.createTempAvail4Work(obj)
                    val ta4w = obj.copy(rec_num = result)
                    tempsAvail4Work[ta4w.rec_num] = ta4w
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session, "temps_avail_for_work")
                    }
                    return result
                }
                is Temp -> {
                    val result = dq.createTemps(obj)
                    val temp = obj.copy(emp_num = result)
                    temps[temp.emp_num] = temp
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session, "temps")
                    }
                    return result
                }
                is User -> {
                    val result = dq.addUser(obj)
                    val user = obj.copy(id = result)
                    users[user.id] = user
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session, "users")
                    }
                    return result.toInt()
                }
                is WONotes -> {
                    val result = dq.createWONotes(obj)
                    val wonote = obj.copy(id = result)
                    woNotes[wonote.id] = wonote
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session, "work_order_notes")
                    }
                    return result
                }
                is WorkOrder -> {
                    val result = dq.createWorkOrder(obj)
                    val wo = obj.copy(wo_number = result)
                    workOrders[wo.wo_number] = wo
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session, "work_orders")
                    }
                    return result
                }
            }
        } catch (e: Exception) {
            log.error("Create failed: ", e)
        }
        throw IllegalArgumentException("Unknown Object")
    }

    override fun <T> edit(obj: T, session: DAPSSession) {
        try {
            when (obj) {
                is AccountRep -> {
                    // skip...
                }
                is Billing -> {
                    val old = billings.replace(obj.counter, obj)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(launch {
                            dq.updateBilling(obj)
                        }, obj.counter, old!!, billings, "billings", session)
                    }
                }
                is BillType -> {
                    // skip...
                }
                is ClientFile -> {
                    val old = clientFiles.replace(obj.client_num, obj)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.updateClientFile(obj) },
                            obj.client_num, old!!, clientFiles, "clients",
                            session
                        )
                    }
                }
                is ClientNote -> {
                    val old = clientNotes.replace(obj.client_note_key, obj)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.updateClientNotes(obj) },
                            obj.client_note_key, old!!, clientNotes, "client_notes",
                            session
                        )
                    }
                }
                is ClientPermNotes -> {
                    val old = clientPermNotes.replace(obj.id, obj)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.updateClientPermNote(obj) },
                            obj.id, old!!, clientPermNotes, "client_perm_notes",
                            session
                        )
                    }
                }
                is DAPSAddress -> {
                    val old = dapsAddress.replace(obj.mailing_list_id, obj)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.updateDAPSAddress(obj) },
                            obj.mailing_list_id, old!!, dapsAddress, "daps_addresses",
                            session
                        )
                    }
                }
                is DAPSStaff -> {
                    val old = dapsStaff.replace(obj.initial, obj)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.updateDAPSStaff(obj) },
                            obj.initial, old!!, dapsStaff, "daps_staff",
                            session
                        )
                    }
                }
                is DAPSStaffMessage -> {
                    val old = dapsStaffMessages.replace(obj.staff_messages_key, obj)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.updateDAPSStaffMessages(obj) },
                            obj.staff_messages_key, old!!, dapsStaffMessages, "daps_staff_messages", session
                        )
                    }
                }
                is InterviewGuide -> {
                    val old = interviewGuides.replace(obj.id, obj)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.updateInterviewGuide(obj) },
                            obj.id, old!!, interviewGuides, "interview_guides",
                            session
                        )
                    }
                }
                is JobFunction -> {
                    // skip...
                }
                is PasteErrors -> {
                    val old = pasteErrors.replace(obj.ref_num, obj)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.updatePasteErrors(obj) },
                            obj.ref_num, old!!, pasteErrors, "paste_errors", session
                        )
                    }
                }
                is Payment -> {
                    val old = payments.replace(obj.ref_num, obj)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.updatePayment(obj) },
                            obj.ref_num, old!!, payments, "payments", session
                        )
                    }
                }
                is PermNote -> {
                    val old = permNotes.replace(obj.id, obj)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.updatePermNotes(obj) },
                            obj.id, old!!, permNotes, "perm_notes", session
                        )
                    }
                }
                is PermReqNote -> {
                    val old = permReqNotes.replace(obj.id, obj)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.updatePermReqNote(obj) },
                            obj.id, old!!, permReqNotes, "perm_req_notes", session
                        )
                    }
                }
                is TempNote -> {
                    val old = tempNotes.replace(obj.temp_note_key, obj)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.updateTempNote(obj) },
                            obj.temp_note_key, old!!, tempNotes, "tempnotes", session
                        )
                    }
                }
                is Temp -> {
                    val old = temps.replace(obj.emp_num, obj)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.updateTemp(obj) },
                            obj.emp_num, old!!, temps, "temps", session
                        )
                    }
                }
                is TempsAvail4Work -> {
                    val old = tempsAvail4Work.replace(obj.rec_num, obj)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.updateTempAvail4Work(obj) },
                            obj.rec_num, old!!, tempsAvail4Work, "temps_avail_for_work", session
                        )
                    }
                }
                is User -> {
                    val old = users.replace(obj.id, obj)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.updateUser(obj) },
                            obj.id, old!!, users, "users", session
                        )
                    }
                }
                is WONotes -> {
                    val old = woNotes.replace(obj.id, obj)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.updateWONotes(obj) },
                            obj.id, old!!, woNotes, "work_order_notes", session
                        )
                    }
                }
                is WorkOrder -> {
                    val old = workOrders.replace(obj.wo_number, obj)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.updateWorkOrder(obj) },
                            obj.wo_number, old!!, workOrders, "work_orders", session
                        )
                    }
                }
                else -> throw IllegalArgumentException("Unknown Object")
            }
        } catch (e: Exception) {
            log.error("Update failed", e)
        }
    }

    override fun <T> remove(obj: T, session: DAPSSession) {
        try {
            when (obj) {
                is AccountRep -> {
                    // skip...
                }
                is Billing -> {
                    val old = billings.remove(obj.counter)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.deleteBilling(obj.counter) },
                            obj.counter, old!!, billings, "billings", session
                        )
                    }
                }
                is BillType -> {
                    // skip...
                }
                is ClientFile -> {
                    val old = clientFiles.remove(obj.client_num)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.deleteClientFile(obj.client_num) },
                            obj.client_num, old!!, clientFiles, "clients",
                            session
                        )
                    }
                }
                is ClientNote -> {
                    val old = clientNotes.remove(obj.client_note_key)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.deleteClientNote(obj.client_note_key!!) },
                            obj.client_note_key, old!!, clientNotes, "client_notes", session
                        )
                    }
                }
                is ClientPermNotes -> {
                    val old = clientPermNotes.remove(obj.id)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.deleteClientPermNote(obj.id) },
                            obj.id, old!!, clientPermNotes, "client_perm_notes", session
                        )
                    }
                }
                is DAPSAddress -> {
                    val old = dapsAddress.remove(obj.mailing_list_id)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.deleteDAPSAddress(obj.mailing_list_id!!) },
                            obj.mailing_list_id, old!!, dapsAddress, "daps_addresses",
                            session
                        )
                    }
                }
                is DAPSStaff -> {
                    // TODO: Add a primary key
                    val old = dapsStaff.remove(obj.initial)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.deleteDAPSStaff(obj) },
                            obj.initial, old!!, dapsStaff, "daps_staff", session
                        )
                    }
                }
                is DAPSStaffMessage -> {
                    val old = dapsStaffMessages.remove(obj.staff_messages_key)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.deleteDAPSStaffMessages(obj.staff_messages_key!!) },
                            obj.staff_messages_key, old!!, dapsStaffMessages, "daps_staff_messages", session
                        )
                    }
                }
                is InterviewGuide -> {
                    val old = interviewGuides.remove(obj.id)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.deleteInterviewGuide(obj.id!!) },
                            obj.id, old!!, interviewGuides, "interview_guides", session
                        )
                    }
                }
                is JobFunction -> {
                    // skip...
                }
                is PasteErrors -> {
                    // TODO: Add primary key
                    val old = pasteErrors.remove(obj.ref_num)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.deletePasteErrors(obj) },
                            obj.ref_num, old!!, pasteErrors, "paste_errors", session
                        )
                    }
                }
                is Payment -> {
                    // TODO: Add primary key
                    val old = payments.remove(obj.ref_num)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.deletePayment(obj) },
                            obj.ref_num, old!!, payments, "payments", session
                        )
                    }
                }
                is PermNote -> {
                    val old = permNotes.remove(obj.id)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.deletePermNotes(obj.id!!) },
                            obj.id, old!!, permNotes, "perm_notes", session
                        )
                    }
                }
                is PermReqNote -> {
                    val old = permReqNotes.remove(obj.id)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.deletePermReqNote(obj.id!!) },
                            obj.id, old!!, permReqNotes, "perm_req_notes", session
                        )
                    }
                }
                is TempNote -> {
                    val old = tempNotes.remove(obj.temp_note_key)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.deleteTempNote(obj.temp_note_key!!) },
                            obj.temp_note_key, old!!, tempNotes, "tempnotes", session
                        )
                    }
                }
                is Temp -> {
                    val old = temps.remove(obj.emp_num)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.deleteTemp(obj.emp_num) },
                            obj.emp_num, old!!, temps, "temps", session
                        )
                    }
                }
                is TempsAvail4Work -> {
                    val old = tempsAvail4Work.remove(obj.rec_num)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.deleteTempAvail4Work(obj.rec_num) },
                            obj.rec_num, old!!, tempsAvail4Work, "temps_avail_for_work", session
                        )
                    }
                }
                is User -> {
                    val old = users.remove(obj.id)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.deleteUser(obj.id) },
                            obj.id, old!!, users, "users", session
                        )
                    }
                }
                is WONotes -> {
                    val old = woNotes.remove(obj.id)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.deleteWONote(obj.id) },
                            obj.id, old!!, woNotes, "work_order_notes", session
                        )
                    }
                }
                is WorkOrder -> {
                    val old = workOrders.remove(obj.wo_number)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.deleteWorkOrder(obj.wo_number) },
                            obj.wo_number, old!!, workOrders, "work_orders", session
                        )
                    }
                }
            }
        } catch (e: Exception) {
            log.error("Update failed", e)
        }
    }

    // Intentional read-access only maps
    override fun billings_map(): Map<Int, Billing> {
        return billings
    }

    override fun bill_types_map(): Map<String, BillType> {
        return emptyMap()
    }

    override fun client_files_map(): Map<Int, ClientFile> {
        return clientFiles
    }

    override fun client_notes_map(): Map<Int?, ClientNote> {
        return clientNotes
    }

    override fun client_perm_notes_map(): Map<Int, ClientPermNotes> {
        return clientPermNotes
    }

    override fun daps_address_map(): Map<Int?, DAPSAddress> {
        return dapsAddress
    }

    override fun daps_staff_messages_map(): Map<Int?, DAPSStaffMessage> {
        return dapsStaffMessages
    }

    override fun daps_staff_map(): Map<String?, DAPSStaff> {
        return dapsStaff
    }

    override fun interview_guides_map(): Map<Int?, InterviewGuide> {
        return interviewGuides
    }

    override fun paste_errors_map(): Map<String?, PasteErrors> {
        return pasteErrors
    }

    override fun payments_map(): Map<String?, Payment> {
        return payments
    }

    override fun perm_notes_map(): Map<Int?, PermNote> {
        return permNotes
    }

    override fun perm_req_notes_map(): Map<Int?, PermReqNote> {
        return permReqNotes
    }

    override fun temp_notes_map(): Map<Int?, TempNote> {
        return tempNotes
    }

    override fun temps_avail_for_work_map(): Map<Int?, TempsAvail4Work> {
        return tempsAvail4Work
    }

    override fun temps_map(): Map<Int?, Temp> {
        return temps
    }

    override fun users_map(): Map<Long?, User> {
        return users
    }

    override fun wo_notes_map(): Map<Int?, WONotes> {
        return woNotes
    }

    override fun work_orders_map(): Map<Int?, WorkOrder> {
        return workOrders
    }

}
