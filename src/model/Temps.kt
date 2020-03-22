package model

import database.tables.TempsTable
import java.io.Serializable
import java.sql.ResultSet
import java.time.LocalDateTime

data class Temps(val emp_num: Int, val type_a: String?, val type_b: String?,
                 val status: Boolean, val firstname: String?, val lastname: String?,
                 val address1: String?, val address2: String?, val city: String?,
                 val state: String?, val zip: String?, val county: String?,
                 val email: String?, val hphone: String?, val wphone: String?,
                 val wext: String?, val callwork: Boolean?, val fax: String?,
                 val cellphone: String?, val social_sec_num: String?, val daps_start: LocalDateTime?,
                 val daps_dollar: Double?, val hourly_rate: String?, val chart_complete: Boolean?,
                 val license_file: Boolean?, val license: String?, val license_date: LocalDateTime?,
                 val malp_ins: Boolean?, val mapl_info: String?, val exper_since: LocalDateTime?,
                 val photo_id: Boolean?, val work_auth: Boolean?, val ref_check: Boolean?,
                 val cont_sub_lic: Boolean?, val exper_type: String?, val emergency_contact: String?,
                 val emergency_phone: String?, val eext: String?, val preferences: String?,
                 val dislikes: String?, val temp_needs: Boolean?, val perm_needs: Boolean?,
                 val computer: String?, val oncall: Boolean?, val avail_m: Boolean?,
                 val avail_t: Boolean?, val avail_w: Boolean?, val avail_r: Boolean?,
                 val avail_f: Boolean?, val avail_s: Boolean?, val avail_u: Boolean?,
                 val avail_nos: Boolean?, val pref_loc: String?, val max_miles: String?,
                 val ref_by: String?, val notes: String?, val account_rep: String?,
                 val account_rep_enddate: LocalDateTime?, val perm_notes: String?,
                 val set_flag: Boolean?, val yes_list: Boolean?, val resident_alien: Boolean?,
                 val resident_alien_exp: LocalDateTime?, val fillter_two: Double?) : Serializable {
    constructor(result_set: ResultSet) : this (
        result_set.getInt(TempsTable.emp_num.name),
        result_set.getString(TempsTable.type_a.name),
        result_set.getString(TempsTable.type_b.name),
        result_set.getBoolean(TempsTable.status.name),
        result_set.getString(TempsTable.firstname.name),
        result_set.getString(TempsTable.lastname.name),
        result_set.getString(TempsTable.address1.name),
        result_set.getString(TempsTable.address2.name),
        result_set.getString(TempsTable.city.name),
        result_set.getString(TempsTable.state.name),
        result_set.getString(TempsTable.zip.name),
        result_set.getString(TempsTable.county.name),
        result_set.getString(TempsTable.email.name),
        result_set.getString(TempsTable.hphone.name),
        result_set.getString(TempsTable.wphone.name),
        result_set.getString(TempsTable.wext.name),
        result_set.getBoolean(TempsTable.callwork.name),
        result_set.getString(TempsTable.fax.name),
        result_set.getString(TempsTable.cellphone.name),
        result_set.getString(TempsTable.social_sec_num.name),
        result_set.getString(TempsTable.daps_start.name)?.let {
            LocalDateTime.parse(it.replace(" ", "T"))
        },
        result_set.getDouble(TempsTable.daps_dollar.name),
        result_set.getString(TempsTable.hourly_rate.name),
        result_set.getBoolean(TempsTable.chart_complete.name),
        result_set.getBoolean(TempsTable.license_file.name),
        result_set.getString(TempsTable.license.name),
        result_set.getString(TempsTable.license_date.name)?.let {
            LocalDateTime.parse(it.replace(" ", "T"))
        },
        result_set.getBoolean(TempsTable.malp_ins.name),
        result_set.getString(TempsTable.mapl_info.name),
        result_set.getString(TempsTable.exper_since.name)?.let {
            LocalDateTime.parse(it.replace(" ", "T"))
        },
        result_set.getBoolean(TempsTable.photo_id.name),
        result_set.getBoolean(TempsTable.work_auth.name),
        result_set.getBoolean(TempsTable.ref_check.name),
        result_set.getBoolean(TempsTable.cont_sub_lic.name),
        result_set.getString(TempsTable.exper_type.name),
        result_set.getString(TempsTable.emergency_contact.name),
        result_set.getString(TempsTable.emergency_phone.name),
        result_set.getString(TempsTable.eext.name),
        result_set.getString(TempsTable.preferences.name),
        result_set.getString(TempsTable.dislikes.name),
        result_set.getBoolean(TempsTable.temp_needs.name),
        result_set.getBoolean(TempsTable.perm_needs.name),
        result_set.getString(TempsTable.computer.name),
        result_set.getBoolean(TempsTable.oncall.name),
        result_set.getBoolean(TempsTable.avail_m.name),
        result_set.getBoolean(TempsTable.avail_t.name),
        result_set.getBoolean(TempsTable.avail_w.name),
        result_set.getBoolean(TempsTable.avail_r.name),
        result_set.getBoolean(TempsTable.avail_f.name),
        result_set.getBoolean(TempsTable.avail_s.name),
        result_set.getBoolean(TempsTable.avail_u.name),
        result_set.getBoolean(TempsTable.avail_nos.name),
        result_set.getString(TempsTable.pref_loc.name),
        result_set.getString(TempsTable.max_miles.name),
        result_set.getString(TempsTable.ref_by.name),
        result_set.getString(TempsTable.notes.name),
        result_set.getString(TempsTable.account_rep.name),
        result_set.getString(TempsTable.account_rep_enddate.name)?.let {
            LocalDateTime.parse(it.replace(" ", "T"))
        },
        result_set.getString(TempsTable.perm_notes.name),
        result_set.getBoolean(TempsTable.set_flag.name),
        result_set.getBoolean(TempsTable.yes_list.name),
        result_set.getBoolean(TempsTable.resident_alien.name),
        result_set.getString(TempsTable.resident_alien_exp.name)?.let {
            LocalDateTime.parse(it.replace(" ", "T"))
        },
        result_set.getDouble(TempsTable.filler_two.name)
    )
}
