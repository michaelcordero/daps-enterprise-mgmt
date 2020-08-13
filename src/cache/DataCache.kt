package cache

import model.*

interface DataCache {
    /**
     * All of these methods write to the database and write to the cache, ensuring the app is in a consistent state.
     */
    fun <T> add(obj: T): Int
    fun <T> edit(obj: T)
    fun <T> remove(obj: T)
    fun allBilling(): Map<Int,Billing>
    fun allBillTypes(): Map<String,BillType>
    fun allClientFiles(): Map<Int,ClientFile>
    fun allClientNotes(): Map<Int?,ClientNote>
    fun allClientPermNotes(): Map<Int,ClientPermNotes>
    fun allDAPSAddress(): Map<Int?,DAPSAddress>
    fun allDAPSStaffMessages(): Map<Int?,DAPSStaffMessage>
    fun allDAPSStaff(): Map<String?,DAPSStaff>
    fun allInterviewGuides(): Map<Int?,InterviewGuide>
    fun allPasteErrors(): Map<String?,PasteErrors>
    fun allPayments(): Map<String?,Payment>
    fun allPermNotes(): Map<Int?,PermNote>
    fun allPermReqNotes(): Map<Int?,PermReqNote>
    fun allTempNotes(): Map<Int?,TempNote>
    fun allTempsAvail4Work(): Map<Int?,TempsAvail4Work>
    fun allTemps(): Map<Int?,Temp>
    fun allUsers(): Map<Long?,User>
    fun allWONotes(): Map<Int?,WONotes>
    fun allWorkOrders(): Map<Int?,WorkOrder>
}
