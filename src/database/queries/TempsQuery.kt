package database.queries

import database.tables.TempsTable
import model.Temps
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

interface TempsQuery {
    // Abstract property initialized by LocalDataQuery
    val db: Database

    /**
     * Create
     */
    fun createTemps(t: Temps): Int {
        return transaction (db) {
            TempsTable.insert {
                // emp_num will be auto-incremented
                it[type_a] = t.type_a
                it[type_b] = t.type_b
                it[status] = t.status
                it[firstname] = t.firstname
                it[lastname] = t.lastname
                it[address1] = t.address1
                it[address2] = t.address2
                it[city] = t.city
                it[state] = t.state
                it[zip] = t.zip
                it[county] = t.county
                it[email] = t.email
                it[hphone] = t.hphone
                it[wphone] = t.wphone
                it[wext] = t.wext
                it[callwork] = t.callwork
                it[fax] = t.fax
                it[cellphone] = t.cellphone
                it[social_sec_num] = t.social_sec_num
                it[daps_start] = t.daps_start
                it[daps_dollar] = t.daps_dollar
                it[hourly_rate] = t.hourly_rate
                it[chart_complete] = t.chart_complete
                it[license_file] = t.license_file
                it[license] = t.license
                it[license_date] = t.license_date
                it[malp_ins] = t.malp_ins
                it[mapl_info] = t.mapl_info
                it[exper_since] = t.exper_since
                it[photo_id] = t.photo_id
                it[work_auth] = t.work_auth
                it[ref_check] = t.ref_check
                it[cont_sub_lic] = t.cont_sub_lic
                it[exper_type] = t.exper_type
                it[emergency_contact] = t.emergency_contact
                it[emergency_phone] = t.emergency_phone
                it[eext] = t.eext
                it[preferences] = t.preferences
                it[dislikes] = t.dislikes
                it[temp_needs] = t.temp_needs
                it[perm_needs] = t.perm_needs
                it[computer] = t.computer
                it[oncall] = t.oncall
                it[avail_m] = t.avail_m
                it[avail_t] = t.avail_t
                it[avail_w] = t.avail_w
                it[avail_r] = t.avail_r
                it[avail_f] = t.avail_f
                it[avail_s] = t.avail_s
                it[avail_u] = t.avail_u
                it[avail_nos] = t.avail_nos
                it[pref_loc] = t.pref_loc
                it[max_miles] = t.max_miles
                it[ref_by] = t.ref_by
                it[notes] = t.notes
                it[account_rep] = t.account_rep
                it[account_rep_enddate] = t.account_rep_enddate
                it[perm_notes] = t.perm_notes
                it[set_flag] = t.set_flag
                it[yes_list] = t.yes_list
                it[resident_alien] = t.resident_alien
                it[resident_alien_exp] = t.resident_alien_exp
                it[filler_two] = t.fillter_two
            } get TempsTable.emp_num
        }
    }

    fun insertTemp(t: Temps) {
        transaction (db) {
            TempsTable.insert {
                it[emp_num] = t.emp_num
                it[type_a] = t.type_a
                it[type_b] = t.type_b
                it[status] = t.status
                it[firstname] = t.firstname
                it[lastname] = t.lastname
                it[address1] = t.address1
                it[address2] = t.address2
                it[city] = t.city
                it[state] = t.state
                it[zip] = t.zip
                it[county] = t.county
                it[email] = t.email
                it[hphone] = t.hphone
                it[wphone] = t.wphone
                it[wext] = t.wext
                it[callwork] = t.callwork
                it[fax] = t.fax
                it[cellphone] = t.cellphone
                it[social_sec_num] = t.social_sec_num
                it[daps_start] = t.daps_start
                it[daps_dollar] = t.daps_dollar
                it[hourly_rate] = t.hourly_rate
                it[chart_complete] = t.chart_complete
                it[license_file] = t.license_file
                it[license] = t.license
                it[license_date] = t.license_date
                it[malp_ins] = t.malp_ins
                it[mapl_info] = t.mapl_info
                it[exper_since] = t.exper_since
                it[photo_id] = t.photo_id
                it[work_auth] = t.work_auth
                it[ref_check] = t.ref_check
                it[cont_sub_lic] = t.cont_sub_lic
                it[exper_type] = t.exper_type
                it[emergency_contact] = t.emergency_contact
                it[emergency_phone] = t.emergency_phone
                it[eext] = t.eext
                it[preferences] = t.preferences
                it[dislikes] = t.dislikes
                it[temp_needs] = t.temp_needs
                it[perm_needs] = t.perm_needs
                it[computer] = t.computer
                it[oncall] = t.oncall
                it[avail_m] = t.avail_m
                it[avail_t] = t.avail_t
                it[avail_w] = t.avail_w
                it[avail_r] = t.avail_r
                it[avail_f] = t.avail_f
                it[avail_s] = t.avail_s
                it[avail_u] = t.avail_u
                it[avail_nos] = t.avail_nos
                it[pref_loc] = t.pref_loc
                it[max_miles] = t.max_miles
                it[ref_by] = t.ref_by
                it[notes] = t.notes
                it[account_rep] = t.account_rep
                it[account_rep_enddate] = t.account_rep_enddate
                it[perm_notes] = t.perm_notes
                it[set_flag] = t.set_flag
                it[yes_list] = t.yes_list
                it[resident_alien] = t.resident_alien
                it[resident_alien_exp] = t.resident_alien_exp
                it[filler_two] = t.fillter_two
            }
        }
    }

    /**
     * Read
     */

    fun allTemps(): List<Temps> {
        return transaction (db) {
            TempsTable.selectAll().toList()
        }.map {
            Temps(
                it[TempsTable.emp_num],
                it[TempsTable.type_a],
                it[TempsTable.type_b],
                it[TempsTable.status],
                it[TempsTable.firstname],
                it[TempsTable.lastname],
                it[TempsTable.address1],
                it[TempsTable.address2],
                it[TempsTable.city],
                it[TempsTable.state],
                it[TempsTable.zip],
                it[TempsTable.county],
                it[TempsTable.email],
                it[TempsTable.hphone],
                it[TempsTable.wphone],
                it[TempsTable.wext],
                it[TempsTable.callwork],
                it[TempsTable.fax],
                it[TempsTable.cellphone],
                it[TempsTable.social_sec_num],
                it[TempsTable.daps_start],
                it[TempsTable.daps_dollar],
                it[TempsTable.hourly_rate],
                it[TempsTable.chart_complete],
                it[TempsTable.license_file],
                it[TempsTable.license],
                it[TempsTable.license_date],
                it[TempsTable.malp_ins],
                it[TempsTable.mapl_info],
                it[TempsTable.exper_since],
                it[TempsTable.photo_id],
                it[TempsTable.work_auth],
                it[TempsTable.ref_check],
                it[TempsTable.cont_sub_lic],
                it[TempsTable.exper_type],
                it[TempsTable.emergency_contact],
                it[TempsTable.emergency_phone],
                it[TempsTable.eext],
                it[TempsTable.preferences],
                it[TempsTable.dislikes],
                it[TempsTable.temp_needs],
                it[TempsTable.perm_needs],
                it[TempsTable.computer],
                it[TempsTable.oncall],
                it[TempsTable.avail_m],
                it[TempsTable.avail_t],
                it[TempsTable.avail_w],
                it[TempsTable.avail_r],
                it[TempsTable.avail_f],
                it[TempsTable.avail_s],
                it[TempsTable.avail_u],
                it[TempsTable.avail_nos],
                it[TempsTable.pref_loc],
                it[TempsTable.max_miles],
                it[TempsTable.ref_by],
                it[TempsTable.notes],
                it[TempsTable.account_rep],
                it[TempsTable.account_rep_enddate],
                it[TempsTable.perm_notes],
                it[TempsTable.set_flag],
                it[TempsTable.yes_list],
                it[TempsTable.resident_alien],
                it[TempsTable.resident_alien_exp],
                it[TempsTable.filler_two]
            )
        }
    }

    fun readTemp(emp_num: Int): Temps {
        return transaction (db) {
            TempsTable.select {
                TempsTable.emp_num.eq(emp_num)
            }.map {
                Temps(
                    it[TempsTable.emp_num],
                    it[TempsTable.type_a],
                    it[TempsTable.type_b],
                    it[TempsTable.status],
                    it[TempsTable.firstname],
                    it[TempsTable.lastname],
                    it[TempsTable.address1],
                    it[TempsTable.address2],
                    it[TempsTable.city],
                    it[TempsTable.state],
                    it[TempsTable.zip],
                    it[TempsTable.county],
                    it[TempsTable.email],
                    it[TempsTable.hphone],
                    it[TempsTable.wphone],
                    it[TempsTable.wext],
                    it[TempsTable.callwork],
                    it[TempsTable.fax],
                    it[TempsTable.cellphone],
                    it[TempsTable.social_sec_num],
                    it[TempsTable.daps_start],
                    it[TempsTable.daps_dollar],
                    it[TempsTable.hourly_rate],
                    it[TempsTable.chart_complete],
                    it[TempsTable.license_file],
                    it[TempsTable.license],
                    it[TempsTable.license_date],
                    it[TempsTable.malp_ins],
                    it[TempsTable.mapl_info],
                    it[TempsTable.exper_since],
                    it[TempsTable.photo_id],
                    it[TempsTable.work_auth],
                    it[TempsTable.ref_check],
                    it[TempsTable.cont_sub_lic],
                    it[TempsTable.exper_type],
                    it[TempsTable.emergency_contact],
                    it[TempsTable.emergency_phone],
                    it[TempsTable.eext],
                    it[TempsTable.preferences],
                    it[TempsTable.dislikes],
                    it[TempsTable.temp_needs],
                    it[TempsTable.perm_needs],
                    it[TempsTable.computer],
                    it[TempsTable.oncall],
                    it[TempsTable.avail_m],
                    it[TempsTable.avail_t],
                    it[TempsTable.avail_w],
                    it[TempsTable.avail_r],
                    it[TempsTable.avail_f],
                    it[TempsTable.avail_s],
                    it[TempsTable.avail_u],
                    it[TempsTable.avail_nos],
                    it[TempsTable.pref_loc],
                    it[TempsTable.max_miles],
                    it[TempsTable.ref_by],
                    it[TempsTable.notes],
                    it[TempsTable.account_rep],
                    it[TempsTable.account_rep_enddate],
                    it[TempsTable.perm_notes],
                    it[TempsTable.set_flag],
                    it[TempsTable.yes_list],
                    it[TempsTable.resident_alien],
                    it[TempsTable.resident_alien_exp],
                    it[TempsTable.filler_two]
                )
            }
        }.first()
    }

    /**
     * Update
     */

    fun updateTemp(t: Temps) {
        transaction (db) {
            TempsTable.update({
                TempsTable.emp_num.eq(t.emp_num)
            }) {
                it[emp_num] = t.emp_num
                it[type_a] = t.type_a
                it[type_b] = t.type_b
                it[status] = t.status
                it[firstname] = t.firstname
                it[lastname] = t.lastname
                it[address1] = t.address1
                it[address2] = t.address2
                it[city] = t.city
                it[state] = t.state
                it[zip] = t.zip
                it[county] = t.county
                it[email] = t.email
                it[hphone] = t.hphone
                it[wphone] = t.wphone
                it[wext] = t.wext
                it[callwork] = t.callwork
                it[fax] = t.fax
                it[cellphone] = t.cellphone
                it[social_sec_num] = t.social_sec_num
                it[daps_start] = t.daps_start
                it[daps_dollar] = t.daps_dollar
                it[hourly_rate] = t.hourly_rate
                it[chart_complete] = t.chart_complete
                it[license_file] = t.license_file
                it[license] = t.license
                it[license_date] = t.license_date
                it[malp_ins] = t.malp_ins
                it[mapl_info] = t.mapl_info
                it[exper_since] = t.exper_since
                it[photo_id] = t.photo_id
                it[work_auth] = t.work_auth
                it[ref_check] = t.ref_check
                it[cont_sub_lic] = t.cont_sub_lic
                it[exper_type] = t.exper_type
                it[emergency_contact] = t.emergency_contact
                it[emergency_phone] = t.emergency_phone
                it[eext] = t.eext
                it[preferences] = t.preferences
                it[dislikes] = t.dislikes
                it[temp_needs] = t.temp_needs
                it[perm_needs] = t.perm_needs
                it[computer] = t.computer
                it[oncall] = t.oncall
                it[avail_m] = t.avail_m
                it[avail_t] = t.avail_t
                it[avail_w] = t.avail_w
                it[avail_r] = t.avail_r
                it[avail_f] = t.avail_f
                it[avail_s] = t.avail_s
                it[avail_u] = t.avail_u
                it[avail_nos] = t.avail_nos
                it[pref_loc] = t.pref_loc
                it[max_miles] = t.max_miles
                it[ref_by] = t.ref_by
                it[notes] = t.notes
                it[account_rep] = t.account_rep
                it[account_rep_enddate] = t.account_rep_enddate
                it[perm_notes] = t.perm_notes
                it[set_flag] = t.set_flag
                it[yes_list] = t.yes_list
                it[resident_alien] = t.resident_alien
                it[resident_alien_exp] = t.resident_alien_exp
                it[filler_two] = t.fillter_two
            }
        }
    }

    /**
     * Delete
     */

    fun deleteTemp(t: Temps) {
        transaction (db) {
            TempsTable.deleteWhere {
                TempsTable.emp_num.eq(t.emp_num)
            }
        }
    }

    fun deleteAllTemps() {
        transaction (db) {
            TempsTable.deleteAll()
        }
    }
}
