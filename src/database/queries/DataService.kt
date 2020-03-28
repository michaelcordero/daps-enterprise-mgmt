package database.queries

import java.io.Closeable

interface DataService : Closeable, UserData, BillingData, BillTypeData, ClientFileData, ClientNotesData,
    ClientPermNotesData, DAPSAddressData, DAPSStaffData, DAPSStaffMessages, InterviewGuideData, PasteErrorsData,
        PaymentData, PermNotesData, PermReqNotesData, TempNotesData, TempsAvail4WorkData, TempsData, WONotesData,
        WorkOrderData
