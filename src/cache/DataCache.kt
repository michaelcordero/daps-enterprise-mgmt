package cache

import model.*

interface DataCache {
    /**
     * All of these methods write to the database and write to the cache, ensuring the app is in a consistent state.
     */
    fun <T> add(obj: T): Int
    fun <T> edit(obj: T): Int
    fun <T> remove(obj: T): Int
    fun allBilling(): List<Billing>
    fun allBillTypes(): List<BillType>
    fun allClientFiles(): List<ClientFile>
    fun allClientNotes(): List<ClientNotes>
    fun allClientPermNotes(): List<ClientPermNotes>
    fun allDAPSAddress(): List<DAPSAddress>
    fun allDAPSStaffMessages(): List<DAPSStaffMessages>
    fun allDAPSStaff(): List<DAPSStaff>
    fun allInterviewGuides(): List<InterviewGuide>
    fun allPasteErrors(): List<PasteErrors>
    fun allPayments(): List<Payment>
    fun allPermNotes(): List<PermNotes>
    fun allPermReqNotes(): List<PermReqNotes>
    fun allTempNotes(): List<TempNotes>
    fun allTempsAvail4Work(): List<TempsAvail4Work>
    fun allTemps(): List<Temps>
    fun allUsers(): List<User>
    fun allWONotes(): List<WONotes>
    fun allWorkOrders(): List<WorkOrder>
}
