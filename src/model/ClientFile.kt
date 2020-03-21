package model

import database.tables.ClientFileTable
import java.io.Serializable
import java.sql.ResultSet
import java.time.LocalDateTime

data class ClientFile(val client_num: Int, val ofcname: String?,
                      val firstname1: String?, val lastname1: String?,
                      val firstname2: String?, val lastname2: String?,
                      val address1: String?, val address2: String?,
                      val city: String?, val state: String?,
                      val county: String?, val email: String?,
                      val ophone: String?, val oxtension: String?,
                      val ofax: String?, val hphone: String?,
                      val cellphone: String?, val carphone: String?,
                      val estdate: LocalDateTime?, val specialty: String?,
                      val ofchrs: String?, val ofcmanager: String?,
                      val rate_confirm: Boolean?, val agreement: LocalDateTime?,
                      val agreement_perm: LocalDateTime?, val pktsent: LocalDateTime?,
                      val refdby: String?, val preferences: String?,
                      val dislikes: String?, val temphyg: Boolean?,
                      val daps_dollar: Double, val daps_dollar_two: Double,
                      val needs: String?, val start_date: LocalDateTime?,
                      val end_date: LocalDateTime?, val days: String?,
                      val permconf: Boolean?, val tempconf: Boolean?,
                      val mlplcmnt: Boolean?, val lofaplcmnt: Boolean?,
                      val patnttime: String?, val warndate1: LocalDateTime?,
                      val warndate2: LocalDateTime?, val warndate3: LocalDateTime?,
                      val cnotes: String?, val multioffice: String?,
                      val payperiods: Int?, val yeslist: Boolean?,
                      val filler: LocalDateTime?, val filler2: Double
                      ) : Serializable {
    constructor(result_set: ResultSet) : this (
        result_set.getInt(ClientFileTable.client_num.name),
        result_set.getString(ClientFileTable.ofcname.name),
        result_set.getString(ClientFileTable.firstname1.name),
        result_set.getString(ClientFileTable.lastname1.name),
        result_set.getString(ClientFileTable.firstname2.name),
        result_set.getString(ClientFileTable.lastname2.name),
        result_set.getString(ClientFileTable.address1.name),
        result_set.getString(ClientFileTable.address2.name),
        result_set.getString(ClientFileTable.city.name),
        result_set.getString(ClientFileTable.state.name),
        result_set.getString(ClientFileTable.county.name),
        result_set.getString(ClientFileTable.email.name),
        result_set.getString(ClientFileTable.ophone.name),
        result_set.getString(ClientFileTable.oxtension.name),
        result_set.getString(ClientFileTable.ofax.name),
        result_set.getString(ClientFileTable.hphone.name),
        result_set.getString(ClientFileTable.cellphone.name),
        result_set.getString(ClientFileTable.carphone.name),
        result_set.getString(ClientFileTable.estdate.name)?.let { LocalDateTime.parse(it.replace(" ","T")) },
        result_set.getString(ClientFileTable.speciality.name),
        result_set.getString(ClientFileTable.ofchrs.name),
        result_set.getString(ClientFileTable.ofcmanager.name),
        result_set.getBoolean(ClientFileTable.rateconfirm.name),
        result_set.getString(ClientFileTable.agreement.name)?.let { LocalDateTime.parse(it.replace(" ","T")) },
        result_set.getString(ClientFileTable.agreement_perm.name)?.let { LocalDateTime.parse(it.replace(" ","T")) },
        result_set.getString(ClientFileTable.pktsent.name)?.let { LocalDateTime.parse(it.replace(" ","T")) },
        result_set.getString(ClientFileTable.refdby.name),
        result_set.getString(ClientFileTable.preferences.name),
        result_set.getString(ClientFileTable.dislikes.name),
        result_set.getBoolean(ClientFileTable.temphyg.name),
        result_set.getDouble(ClientFileTable.daps_dollar.name),
        result_set.getDouble(ClientFileTable.daps_dollar_two.name),
        result_set.getString(ClientFileTable.needs.name),
        result_set.getString(ClientFileTable.startdate.name)?.let { LocalDateTime.parse(it.replace(" ","T")) },
        result_set.getString(ClientFileTable.endate.name)?.let { LocalDateTime.parse(it.replace(" ","T")) },
        result_set.getString(ClientFileTable.days.name),
        result_set.getBoolean(ClientFileTable.permconf.name),
        result_set.getBoolean(ClientFileTable.tempconf.name),
        result_set.getBoolean(ClientFileTable.mlplcmnt.name),
        result_set.getBoolean(ClientFileTable.lofaplcmnt.name),
        result_set.getString(ClientFileTable.patnttime.name),
        result_set.getString(ClientFileTable.warndate1.name)?.let { LocalDateTime.parse(it.replace(" ","T")) },
        result_set.getString(ClientFileTable.warndate2.name)?.let { LocalDateTime.parse(it.replace(" ","T")) },
        result_set.getString(ClientFileTable.warndate3.name)?.let { LocalDateTime.parse(it.replace(" ","T")) },
        result_set.getString(ClientFileTable.cnotes.name),
        result_set.getString(ClientFileTable.multioffice.name),
        result_set.getInt(ClientFileTable.payperiods.name),
        result_set.getBoolean(ClientFileTable.yeslist.name),
        result_set.getString(ClientFileTable.filler.name)?.let { LocalDateTime.parse(it.replace(" ","T")) },
        result_set.getDouble(ClientFileTable.filler2.name)
    )
}