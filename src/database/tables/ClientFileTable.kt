package database.tables

import org.jetbrains.exposed.sql.Table

object ClientFileTable : Table(){
    val client_num = integer("Client#").primaryKey()
    val ofcname = text("OfcName")
    val firstname1 = text("FirstName1")
    val lastname1 = text("LastName1")
    val firstname2 = text("FirstName2")
    val lastname2 = text("LastName2")
    val address1 = text("Address1")
    val address2 = text("Address2")
    val city = text("City")
    val state = text("State")
    val zip = text("Zip")
    val county = text("County")
    val email = text("e-Mail")
    val ophone = text("OPhone")
    val oxtension = text("OXtension")
    val ofax = text("OFax")
    val hphone = text("HPhone")
    val cellphone = text("CellPhone")
    val carphone = text("CarPhone")
    val estdate = datetime("EstDate")
    val speciality = text("Speciality")
    val ofchrs = text("OfcHrs")
    val ofcmanager = text("OfcManager")
    val rateconfirm = bool("RateCnfrm?")
    val agreement = datetime("Agrmt?")
    val agreement_perm = datetime("AgrmtPerm")
    val pktsent = datetime("PktSent?")
    val refdby = text("RefdBy")
    val preferences = text("Preferences")
    val dislikes = text("Dislikes")
    val temphyg = bool("TempHyg?")
    val daps_dollar = decimal("DAPS$", 15, 15)
    val daps_dollar_two = decimal("DAPS$", 15,15)
    val needs = text("Needs")
    val startdate = datetime("StartDate")
    val endate = datetime("EndDate")
    val days = text("Days")
    val permconf = bool("PermConf?")
    val tempconf = bool("TempConf?")
    val mlplcmnt = bool("MLPlcmnt")
    val lofaplcmnt = bool("LofAPlcmnt")
    val patnttime = text("PatntTime")
    val warndate1 = datetime("WarnDate1")
    val warndate2 = datetime("WarnDate2")
    val warndate3 = datetime("WarnDate3")
    val cnotes = text("CNotes")
    val multioffice = text("MultiOffice?")
    val payperiods = integer("PayPeriods")
    val yeslist = bool("YesList")
    val filler = datetime("Filler")
    val filler2 = decimal("Filler-2", 15,15)
}
