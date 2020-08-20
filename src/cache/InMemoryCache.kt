package cache

import application.connections
import cache.JSONRouteValues.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import database.queries.DataQuery
import io.ktor.http.cio.websocket.*
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
            log.info("BEGIN: initializing cache...")
            launch {
                billings = ConcurrentHashMap(dq.allBilling().associateBy { it.counter }.toMutableMap())
            }
            launch {
                clientFiles =
                    ConcurrentHashMap(dq.allClientFiles().associateBy { it.client_num }.toMutableMap())
            }
            launch {
                clientNotes =
                    ConcurrentHashMap(dq.allClientNotes().associateBy { it.client_note_key }.toMutableMap())
            }
            launch {
                clientPermNotes= ConcurrentHashMap(dq.allClientPermNotes().associateBy { it.id }.toMutableMap())
            }
            launch {
                dapsAddress =
                    ConcurrentHashMap(dq.allDAPSAddress().associateBy { it.mailing_list_id }.toMutableMap())
            }
            launch {
                dapsStaffMessages =
                    ConcurrentHashMap(dq.allDAPSStaffMessages().associateBy { it.staff_messages_key }
                        .toMutableMap())
            }
            launch {
                dapsStaff = ConcurrentHashMap(dq.allDAPSStaff().associateBy { it.initial }.toMutableMap())
            }
            launch {
                interviewGuides =
                    ConcurrentHashMap(dq.allInterviewGuides().associateBy { it.id }.toMutableMap())
            }
            launch {
                pasteErrors =
                    ConcurrentHashMap(dq.allPasteErrors().associateBy { it.ref_num }.toMutableMap())
            }
            launch {
                payments = ConcurrentHashMap(dq.allPayments().associateBy { it.ref_num }.toMutableMap())
            }
            launch {
                permNotes = ConcurrentHashMap(dq.allPermNotes().associateBy { it.id }.toMutableMap())
            }
            launch {
                permReqNotes = ConcurrentHashMap(dq.allPermReqNotes().associateBy { it.id }.toMutableMap())
            }
            launch {
                tempNotes =
                    ConcurrentHashMap(dq.allTempNotes().associateBy { it.temp_note_key }.toMutableMap())
            }
            launch {
                tempsAvail4Work =
                    ConcurrentHashMap(dq.allTempsAvail4Work().associateBy { it.rec_num }.toMutableMap())
            }
            launch {
                temps = ConcurrentHashMap(dq.allTemps().associateBy { it.emp_num }.toMutableMap())
            }
            launch {
                users = ConcurrentHashMap(dq.allUsers().associateBy { it.id }.toMutableMap())
            }
            launch {
                woNotes = ConcurrentHashMap(dq.allWONotes().associateBy { it.id }.toMutableMap())
            }
            launch {
                workOrders =
                    ConcurrentHashMap(dq.allWorkOrders().associateBy { it.wo_number }.toMutableMap())
            }
        }.invokeOnCompletion {
            log.info("END: finished initializing cache...")
        }
    }

    /**
     * The purpose of this method is to check if an exception occurred in an asynchronous write thread
     * whose data change failed. If the write does fail, the cache will be reverted in the session that originated the
     * change, but not in the other sessions. If the write succeeds, the other sessions who happen to be viewing the
     * same data (i.e. web page), will see their table update to the newest data.
     * @param job = represents the asynchronous thread
     * @param key = usually the primary key of the data table
     * @param old = the previous value to be re-written to the cache
     * @param map = the cached representation of the data
     * @param route = the alias for the data table that's been changed
     * @param session = Session object holding the sessionId which will map the appropriate connections
     */
    private fun <K, V> exchequer(
        job: Job,
        key: K,
        old: V,
        map: MutableMap<K, V>,
        route: JSONRouteValues,
        session: DAPSSession
    ) {
        job.invokeOnCompletion { throwable ->
            // Fail Case
            if (throwable != null) {
                map.replace(key, old)
                val mapper = ObjectMapper()
                val json_object: ObjectNode = mapper.createObjectNode()
                json_object.put(JSONActionKeys.ALERT.name, route.name)
                json_object.put(JSONActionKeys.ERROR.name, throwable.message.toString())
                val message: String = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json_object)
                CoroutineScope(Dispatchers.IO).launch {
                    connections[session.sessionId]?.outgoing?.send(Frame.Text(message))
                }
            }
            // Success Case, tell the others.
            else {
                val mapper = ObjectMapper()
                val json_object: ObjectNode = mapper.createObjectNode()
                json_object.put(JSONActionKeys.UPDATE.name, route.name)
                val message: String = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json_object)
                CoroutineScope(Dispatchers.IO).launch {
                    connections.entries.forEach { entry ->
                        if (entry.key != session.sessionId) {
                            entry.value.outgoing.send(Frame.Text(message))
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
     * @param route tells the listening web session socket connections, if the update applies to their view, i.e.
     * which table their currently viewing.
     */
    private fun notifier(session: DAPSSession, route: JSONRouteValues) {
        val mapper = ObjectMapper()
        val json_object: ObjectNode = mapper.createObjectNode()
        json_object.put(JSONActionKeys.UPDATE.name, route.name)
        val message: String = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json_object)
        CoroutineScope(Dispatchers.IO).launch {
            connections.entries.forEach {
                if (it.key != session.sessionId) {
                    it.value.outgoing.send(Frame.Text(message))
                }
            }
        }
    }

    /**
     * The add method persists the passed in DAPS data object, writing to the database first, for the primary key, then
     * writing to the cache, and notifying other connections in another co-routine (thread).
     * @param obj The object to be written
     * @param session We need the session object, so we can know which session id requested this change, so that we may
     * route the update or error messages appropriately.
     */
    @Suppress("unchecked_cast")
    override fun <T> add(obj: T, session: DAPSSession): T {
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
                        notifier(session, BILLINGS)
                    }
                    return billing as T
                }
                is BillType -> {
                    // skip...
//                    return object
                }
                is ClientFile -> {
                    val result: Int = dq.createClientFile(obj)
                    val cf: ClientFile = obj.copy(client_num = result)
                    clientFiles[cf.client_num] = cf
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session, CLIENTS)
                    }
                    return cf as T
                }
                is ClientNote -> {
                    val result: Int = dq.createClientNotes(obj)
                    val cn = obj.copy(client_note_key = result)
                    clientNotes[cn.client_note_key] = cn
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session, CLIENT_NOTES)
                    }
                    return cn as T
                }
                is ClientPermNotes -> {
                    val result = dq.createClientPermNotes(obj)
                    val cpn = obj.copy(id = result)
                    clientPermNotes[cpn.id] = cpn
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session, CLIENT_PERM_NOTES)
                    }
                    return cpn as T
                }
                is DAPSAddress -> {
                    val result = dq.createDAPSAddress(obj)
                    val da = obj.copy(mailing_list_id = result)
                    dapsAddress[da.mailing_list_id] = da
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session, DAPS_ADDRESSES)
                    }
                    return da as T
                }
                is DAPSStaff -> {
                    // TODO: Revisit this one
                    dq.insertDAPSStaff(obj)
                    dapsStaff[obj.initial] = obj
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session,DAPS_STAFFS)
                    }
                    return obj
                }
                is DAPSStaffMessage -> {
                    val result = dq.createDAPSStaffMessages(obj)
                    val dsm = obj.copy(staff_messages_key = result)
                    dapsStaffMessages[dsm.staff_messages_key] = dsm
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session, DAPS_STAFF_MESSAGES)
                    }
                    return dsm as T
                }
                is InterviewGuide -> {
                    val result = dq.createInterviewGuide(obj)
                    val ig = obj.copy(id = result)
                    interviewGuides[ig.id] = ig
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session, INTERVIEW_GUIDES)
                    }
                    return ig as T
                }
                is JobFunction -> {
                    // skip
//                    return -1
                }
                is PasteErrors -> {
                    // TODO: Revisit
                    dq.insertPasteErrors(obj)
                    pasteErrors[obj.ref_num] = obj
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session, PASTE_ERRORS)
                    }
                    return obj
                }
                is Payment -> {
                    // TODO: Revisit
                    dq.insertPayment(obj)
                    payments[obj.ref_num] = obj
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session, PAYMENTS)
                    }
                    return obj
                }
                is PermNote -> {
                    val result = dq.createPermNotes(obj)
                    val pn = obj.copy(id = result)
                    permNotes[pn.id] = pn
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session, PERM_NOTES)
                    }
                    return pn as T
                }
                is PermReqNote -> {
                    val result = dq.createPermReqNotes(obj)
                    val prn = obj.copy(id = result)
                    permReqNotes[prn.id] = prn
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session, PERM_REQ_NOTES)
                    }
                    return prn as T
                }
                is TempNote -> {
                    val result = dq.createTempNote(obj)
                    val tn = obj.copy(temp_note_key = result)
                    tempNotes[tn.temp_note_key] = tn
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session, TEMP_NOTES)
                    }
                    return tn as T
                }
                is TempsAvail4Work -> {
                    val result = dq.createTempAvail4Work(obj)
                    val ta4w = obj.copy(rec_num = result)
                    tempsAvail4Work[ta4w.rec_num] = ta4w
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session, TEMPS_AVAIL_FOR_WORKS)
                    }
                    return ta4w as T
                }
                is Temp -> {
                    val result = dq.createTemps(obj)
                    val temp = obj.copy(emp_num = result)
                    temps[temp.emp_num] = temp
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session, TEMPS)
                    }
                    return temp as T
                }
                is User -> {
                    val result = dq.addUser(obj)
                    val user = obj.copy(id = result)
                    users[user.id] = user
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session, USERS)
                    }
                    return user as T
                }
                is WONotes -> {
                    val result = dq.createWONotes(obj)
                    val wonote = obj.copy(id = result)
                    woNotes[wonote.id] = wonote
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session, WORK_ORDER_NOTES)
                    }
                    return wonote as T
                }
                is WorkOrder -> {
                    val result = dq.createWorkOrder(obj)
                    val wo = obj.copy(wo_number = result)
                    workOrders[wo.wo_number] = wo
                    CoroutineScope(Dispatchers.IO).launch {
                        notifier(session, WORK_ORDERS)
                    }
                    return wo as T
                }
            }
        } catch (e: Exception) {
            log.error("Create failed: ", e)
        }
        throw IllegalArgumentException("Unknown Object")
    }

    /**
     * The edit method modifies the record currently held in the database and cache, updating the cache first for
     * performance. If the update to the database fails for any reason, the cache is then rolled back to be holding the
     * original object. If the update succeeds, other connections are notified to update.
     * @param obj The object to be persisted
     * @param session The session holds the session id, so the update or error messages are routed appropriately.
     */
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
                        }, obj.counter, old!!, billings, BILLINGS, session)
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
                            obj.client_num, old!!, clientFiles, CLIENTS,
                            session
                        )
                    }
                }
                is ClientNote -> {
                    val old = clientNotes.replace(obj.client_note_key, obj)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.updateClientNotes(obj) },
                            obj.client_note_key, old!!, clientNotes, CLIENT_NOTES,
                            session
                        )
                    }
                }
                is ClientPermNotes -> {
                    val old = clientPermNotes.replace(obj.id, obj)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.updateClientPermNote(obj) },
                            obj.id, old!!, clientPermNotes, CLIENT_PERM_NOTES,
                            session
                        )
                    }
                }
                is DAPSAddress -> {
                    val old = dapsAddress.replace(obj.mailing_list_id, obj)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.updateDAPSAddress(obj) },
                            obj.mailing_list_id, old!!, dapsAddress, DAPS_ADDRESSES,
                            session
                        )
                    }
                }
                is DAPSStaff -> {
                    val old = dapsStaff.replace(obj.initial, obj)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.updateDAPSStaff(obj) },
                            obj.initial, old!!, dapsStaff, DAPS_STAFFS,
                            session
                        )
                    }
                }
                is DAPSStaffMessage -> {
                    val old = dapsStaffMessages.replace(obj.staff_messages_key, obj)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.updateDAPSStaffMessages(obj) },
                            obj.staff_messages_key, old!!, dapsStaffMessages, DAPS_STAFF_MESSAGES, session
                        )
                    }
                }
                is InterviewGuide -> {
                    val old = interviewGuides.replace(obj.id, obj)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.updateInterviewGuide(obj) },
                            obj.id, old!!, interviewGuides, INTERVIEW_GUIDES,
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
                            obj.ref_num, old!!, pasteErrors, PASTE_ERRORS, session
                        )
                    }
                }
                is Payment -> {
                    val old = payments.replace(obj.ref_num, obj)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.updatePayment(obj) },
                            obj.ref_num, old!!, payments, PAYMENTS, session
                        )
                    }
                }
                is PermNote -> {
                    val old = permNotes.replace(obj.id, obj)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.updatePermNotes(obj) },
                            obj.id, old!!, permNotes, PERM_NOTES, session
                        )
                    }
                }
                is PermReqNote -> {
                    val old = permReqNotes.replace(obj.id, obj)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.updatePermReqNote(obj) },
                            obj.id, old!!, permReqNotes, PERM_REQ_NOTES, session
                        )
                    }
                }
                is TempNote -> {
                    val old = tempNotes.replace(obj.temp_note_key, obj)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.updateTempNote(obj) },
                            obj.temp_note_key, old!!, tempNotes,TEMP_NOTES, session
                        )
                    }
                }
                is Temp -> {
                    val old = temps.replace(obj.emp_num, obj)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.updateTemp(obj) },
                            obj.emp_num, old!!, temps, TEMPS, session
                        )
                    }
                }
                is TempsAvail4Work -> {
                    val old = tempsAvail4Work.replace(obj.rec_num, obj)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.updateTempAvail4Work(obj) },
                            obj.rec_num, old!!, tempsAvail4Work, TEMPS_AVAIL_FOR_WORKS, session
                        )
                    }
                }
                is User -> {
                    val old = users.replace(obj.id, obj)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.updateUser(obj) },
                            obj.id, old!!, users, USERS, session
                        )
                    }
                }
                is WONotes -> {
                    val old = woNotes.replace(obj.id, obj)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.updateWONotes(obj) },
                            obj.id, old!!, woNotes, WORK_ORDER_NOTES, session
                        )
                    }
                }
                is WorkOrder -> {
                    val old = workOrders.replace(obj.wo_number, obj)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.updateWorkOrder(obj) },
                            obj.wo_number, old!!, workOrders, WORK_ORDERS, session
                        )
                    }
                }
                else -> throw IllegalArgumentException("Unknown Object")
            }
        } catch (e: Exception) {
            log.error("Update failed", e)
        }
    }

    /**
     * The remove object removes the requested object from the database and cache.
     * @param obj The object to be removed
     * @param session The session holds the id, so update/error messages are routed to the appropriate connections.
     */
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
                            obj.counter, old!!, billings, BILLINGS, session
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
                            obj.client_num, old!!, clientFiles, CLIENTS,
                            session
                        )
                    }
                }
                is ClientNote -> {
                    val old = clientNotes.remove(obj.client_note_key)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.deleteClientNote(obj.client_note_key!!) },
                            obj.client_note_key, old!!, clientNotes, CLIENT_NOTES, session
                        )
                    }
                }
                is ClientPermNotes -> {
                    val old = clientPermNotes.remove(obj.id)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.deleteClientPermNote(obj.id) },
                            obj.id, old!!, clientPermNotes, CLIENT_PERM_NOTES, session
                        )
                    }
                }
                is DAPSAddress -> {
                    val old = dapsAddress.remove(obj.mailing_list_id)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.deleteDAPSAddress(obj.mailing_list_id!!) },
                            obj.mailing_list_id, old!!, dapsAddress, DAPS_ADDRESSES,
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
                            obj.initial, old!!, dapsStaff, DAPS_STAFFS, session
                        )
                    }
                }
                is DAPSStaffMessage -> {
                    val old = dapsStaffMessages.remove(obj.staff_messages_key)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.deleteDAPSStaffMessages(obj.staff_messages_key!!) },
                            obj.staff_messages_key, old!!, dapsStaffMessages, DAPS_STAFF_MESSAGES, session
                        )
                    }
                }
                is InterviewGuide -> {
                    val old = interviewGuides.remove(obj.id)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.deleteInterviewGuide(obj.id!!) },
                            obj.id, old!!, interviewGuides, INTERVIEW_GUIDES, session
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
                            obj.ref_num, old!!, pasteErrors, PASTE_ERRORS, session
                        )
                    }
                }
                is Payment -> {
                    // TODO: Add primary key
                    val old = payments.remove(obj.ref_num)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.deletePayment(obj) },
                            obj.ref_num, old!!, payments, PAYMENTS, session
                        )
                    }
                }
                is PermNote -> {
                    val old = permNotes.remove(obj.id)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.deletePermNotes(obj.id!!) },
                            obj.id, old!!, permNotes, PERM_NOTES, session
                        )
                    }
                }
                is PermReqNote -> {
                    val old = permReqNotes.remove(obj.id)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.deletePermReqNote(obj.id!!) },
                            obj.id, old!!, permReqNotes, PERM_REQ_NOTES, session
                        )
                    }
                }
                is TempNote -> {
                    val old = tempNotes.remove(obj.temp_note_key)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.deleteTempNote(obj.temp_note_key!!) },
                            obj.temp_note_key, old!!, tempNotes, TEMP_NOTES, session
                        )
                    }
                }
                is Temp -> {
                    val old = temps.remove(obj.emp_num)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.deleteTemp(obj.emp_num) },
                            obj.emp_num, old!!, temps, TEMPS, session
                        )
                    }
                }
                is TempsAvail4Work -> {
                    val old = tempsAvail4Work.remove(obj.rec_num)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.deleteTempAvail4Work(obj.rec_num) },
                            obj.rec_num, old!!, tempsAvail4Work, TEMPS_AVAIL_FOR_WORKS, session
                        )
                    }
                }
                is User -> {
                    val old = users.remove(obj.id)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.deleteUser(obj.id) },
                            obj.id, old!!, users, USERS, session
                        )
                    }
                }
                is WONotes -> {
                    val old = woNotes.remove(obj.id)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.deleteWONote(obj.id) },
                            obj.id, old!!, woNotes, WORK_ORDER_NOTES, session
                        )
                    }
                }
                is WorkOrder -> {
                    val old = workOrders.remove(obj.wo_number)
                    CoroutineScope(Dispatchers.IO).launch {
                        exchequer(
                            launch { dq.deleteWorkOrder(obj.wo_number) },
                            obj.wo_number, old!!, workOrders, WORK_ORDERS, session
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
