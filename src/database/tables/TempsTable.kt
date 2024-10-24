package database.tables

import database.realtimestamp
import org.jetbrains.exposed.sql.Table

object TempsTable: Table() {
    val emp_num = integer("Emp#").autoIncrement()
    override val primaryKey: PrimaryKey = PrimaryKey(emp_num, name = "Emp#")
    val type_a = text("TypeA").nullable()
    val type_b = text("TypeB").nullable()
    val status = bool("Status").nullable()
    val firstname = text("FName").nullable()
    val lastname = text("LName").nullable()
    val address1 = text("Addr1").nullable()
    val address2 = text("Addr2").nullable()
    val city = text("City").nullable()
    val state = text("State").nullable()
    val zip = text("Zip").nullable()
    val county = text("County").nullable()
    val email = text("e-Mail").nullable()
    val hphone = text("HPhone").nullable()
    val wphone = text("WPhone").nullable()
    val wext = text("WExt").nullable()
    val callwork = bool("CallWrk?").nullable()
    val fax = text("Fax").nullable()
    val cellphone = text("CellPhone").nullable()
    val social_sec_num = text("SS#").nullable()
    val daps_start = realtimestamp("DAPSstart").nullable()
    val daps_dollar = double("DAPS$").nullable()
    val hourly_rate = text("HourlyRate").nullable()
    val chart_complete = bool("ChartComplete").nullable()
    val license_file = bool("LicenseFile").nullable()
    val license = text("License").nullable()
    val license_date = realtimestamp("LicDate").nullable()
    val malp_ins = bool("MALPins?").nullable()
    val mapl_info = text("MAPLinfo").nullable()
    val exper_since = realtimestamp("ExperSince").nullable()
    val photo_id = bool("PhotoId").nullable()
    val work_auth = bool("WorkAuth").nullable()
    val ref_check = bool("RefCheck").nullable()
    val cont_sub_lic = bool("ContSubLic").nullable()
    val exper_type = text("ExperType").nullable()
    val emergency_contact = text("EmrgyContact").nullable()
    val emergency_phone = text("EmrgyPhone").nullable()
    val eext = text("EExt").nullable()
    val preferences = text("Preferences").nullable()
    val dislikes = text("Dislikes").nullable()
    val temp_needs = bool("TempNeeds").nullable()
    val perm_needs = bool("PermNeeds").nullable()
    val computer = text("Computer").nullable()
    val oncall = bool("OnCall").nullable()
    val avail_m = bool("Avail-M").nullable()
    val avail_t = bool("Avail-T").nullable()
    val avail_w = bool("Avail-W").nullable()
    val avail_r = bool("Avail-R").nullable()
    val avail_f = bool("Avail-F").nullable()
    val avail_s = bool("Avail-S").nullable()
    val avail_u = bool("Avail-U").nullable()
    val avail_nos = bool("Avail-NoS").nullable()
    val pref_loc = text("PrefLoc").nullable()
    val max_miles = text("MaxMiles").nullable()
    val ref_by = text("RefBy").nullable()
    val notes = text("Notes").nullable()
    val account_rep = text("AccountRep").nullable()
    val account_rep_enddate = realtimestamp("AccountRepEndDate").nullable()
    val perm_notes = text("PermNotes").nullable()
    val set_flag = bool("SetFlag").nullable()
    val yes_list = bool("YesList").nullable()
    val resident_alien = bool("Resident Alien").nullable()
    val resident_alien_exp = realtimestamp("Resident Alien Exp").nullable()
    val filler_two = double("Filler-2").nullable()
}
