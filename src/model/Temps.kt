package model

import org.joda.time.DateTime

data class Temps(val emp_num: Int, val type_a: String?, val type_b: String?,
                 val status: Boolean, val firstname: String?, val lastname: String?,
                 val address1: String?, val address2: String?, val city: String?,
                 val state: String?, val zip: String?, val county: String?,
                 val email: String?, val hphone: String?, val wphone: String?,
                 val wext: String?, val callwork: Boolean?, val fax: String?,
                 val cellphone: String?, val social_sec_num: String?, val daps_start: DateTime?,
                 val daps_dollar: Double?, val hourly_rate: String?, val chart_complete: Boolean?,
                 val license_file: Boolean?, val license: String?, val license_date: DateTime?,
                 val malp_ins: Boolean?, val mapl_info: String?, val exper_since: DateTime?,
                 val photo_id: Boolean?, val work_auth: Boolean?, val ref_check: Boolean?,
                 val cont_sub_lic: Boolean?, val exper_type: String?, val emergency_contact: String?,
                 val emergency_phone: String?, val eext: String?, val preferences: String?,
                 val dislikes: String?, val temp_needs: Boolean?, val perm_needs: Boolean?,
                 val computer: String?, val oncall: Boolean?, val avail_m: Boolean?,
                 val avail_t: Boolean?, val avail_w: Boolean?, val avail_r: Boolean?,
                 val avail_f: Boolean?, val avail_s: Boolean?, val avail_u: Boolean?,
                 val avail_nos: Boolean?, val pref_loc: String?, val max_miles: String?,
                 val ref_by: String?, val notes: String?, val account_rep: String?,
                 val account_rep_enddate: DateTime?, val perm_notes: String?,
                 val set_flag: Boolean?, val yes_list: Boolean?, val resident_alien: Boolean?,
                 val resident_alien_exp: DateTime?, val fillter_two: Double?)
