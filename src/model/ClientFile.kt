package model

import org.joda.time.DateTime

data class ClientFile(val client_num: Int, val ofcname: String?,
                      val firstname1: String?, val lastname1: String?,
                      val firstname2: String?, val lastname2: String?,
                      val address1: String?, val address2: String?,
                      val city: String?, val state: String?,
                      val county: String?, val email: String?,
                      val ophone: String?, val oxtension: String?,
                      val ofax: String?, val hphone: String?,
                      val cellphone: String?, val carphone: String?,
                      val estdate: DateTime?, val specialty: String?,
                      val ofchrs: String?, val ofcmanager: String?,
                      val rate_confirm: Boolean?, val agreement: DateTime?,
                      val agreement_perm: DateTime?, val pktsent: DateTime?,
                      val refdby: String?, val preferences: String?,
                      val dislikes: String?, val tmphyg: Boolean?,
                      val daps_dollar: Double, val daps_dollar_two: Double,
                      val needs: String?, val start_date: DateTime?,
                      val end_date: DateTime?, val days: String?,
                      val permconf: Boolean?, val tempconf: Boolean?,
                      val mlplcmnt: Boolean?, val lofaplcmnt: Boolean?,
                      val patnttime: String?, val warndate1: DateTime?,
                      val warndate2: DateTime?, val warndate3: DateTime?,
                      val cnotes: String?, val multioffice: String?,
                      val payperiods: Int?, val yeslist: Boolean?,
                      val filler: DateTime?, val filler2: Double
                      )
