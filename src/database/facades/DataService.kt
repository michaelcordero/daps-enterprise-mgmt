package database.facades

import java.io.Closeable

interface DataService : Closeable, UserData, BillingData, BillTypeData, ClientFileData, ClientNotesData,
    ClientPermNotesData, DAPSAddressData, DAPSStaffData, DAPSStaffMessages, InterviewGuideData, PasteErrorsData
