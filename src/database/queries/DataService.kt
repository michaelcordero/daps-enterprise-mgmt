package database.queries

import java.io.Closeable

interface DataService : Closeable, UserQuery, AccountRepQuery, BillingQuery, BillTypeQuery, ClientFileQuery, ClientNotesQuery,
    ClientPermNotesQuery, DAPSAddressQuery, DAPSStaffQuery, DAPSStaffMessagesQuery, InterviewGuideQuery, PasteErrorsQuery,
        PaymentQuery, PermNotesQuery, PermReqNotesQuery, TempNotesQuery, TempsAvail4WorkQuery, TempsQuery, WONotesQuery,
        WorkOrderQuery
