package cache

import model.*

interface DataCache {
    fun allBilling(): MutableList<Billing>
    fun allBillTypes(): MutableList<BillType>
    fun allClientFiles(): MutableList<ClientFile>
    fun allClientNotes(): MutableList<ClientNotes>
    fun allClientPermNotes(): MutableList<ClientPermNotes>
    fun allDAPSAddress(): MutableList<DAPSAddress>
    fun allDAPSStaffMessages(): MutableList<DAPSStaffMessages>
    fun allDAPSStaff(): MutableList<DAPSStaff>
    fun allInterviewGuides(): MutableList<InterviewGuide>
    fun allPasteErrors(): MutableList<PasteErrors>
    fun allPayments(): MutableList<Payment>
    fun allPermNotes(): MutableList<PermNotes>
    fun allPermReqNotes(): MutableList<PermReqNotes>
    fun allTempNotes(): MutableList<TempNotes>
    fun allTempsAvail4Work(): MutableList<TempsAvail4Work>
    fun allTemps(): MutableList<Temps>
    fun allUsers(): MutableList<User>
    fun allWONotes(): MutableList<WONotes>
    fun allWorkOrders(): MutableList<WorkOrder>
}
