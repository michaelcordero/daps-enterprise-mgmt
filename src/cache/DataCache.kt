package cache

import model.*
import security.DAPSSession

interface DataCache {
    /**
     * All of these methods write to the database and write to the cache, ensuring the app is in a consistent state.
     */
    fun <T> add(obj: T, session: DAPSSession): T
    fun <T> edit(obj: T, session: DAPSSession)
    fun <T> remove(obj: T, session: DAPSSession)
    fun billings_map(): Map<Int,Billing>
    fun bill_types_map(): Map<String,BillType>
    fun client_files_map(): Map<Int,ClientFile>
    fun client_notes_map(): Map<Int?,ClientNote>
    fun client_perm_notes_map(): Map<Int,ClientPermNote>
    fun daps_address_map(): Map<Int?,DAPSAddress>
    fun daps_staff_messages_map(): Map<Int?,DAPSStaffMessage>
    fun daps_staff_map(): Map<String?,DAPSStaff>
    fun interview_guides_map(): Map<Int?,InterviewGuide>
    fun paste_errors_map(): Map<String?,PasteErrors>
    fun payments_map(): Map<String?,Payment>
    fun perm_notes_map(): Map<Int?,PermNote>
    fun perm_req_notes_map(): Map<Int?,PermReqNote>
    fun temp_notes_map(): Map<Int?,TempNote>
    fun temps_avail_for_work_map(): Map<Int?,TempsAvail4Work>
    fun temps_map(): Map<Int?,Temp>
    fun users_map(): Map<Long?,User>
    fun wo_notes_map(): Map<Int?,WONotes>
    fun work_orders_map(): Map<Int?,WorkOrder>
}
