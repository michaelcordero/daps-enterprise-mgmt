package model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import database.tables.ClientFileTable
import model.deserializers.BooleanDeserializer
import model.deserializers.LocalDateDeserializer
import model.serializers.BooleanSerializer
import model.serializers.DoubleSerializer
import model.serializers.LocalDateSerializer
import java.io.Serializable
import java.sql.ResultSet
import java.sql.Timestamp

data class ClientFile @JsonCreator
constructor(
    @JsonProperty(value = "client_num", required = true)
    val client_num: Int,
    val ofcname: String?,
    val firstname1: String?,
    val lastname1: String?,
    val firstname2: String?,
    val lastname2: String?,
    val address1: String?,
    val address2: String?,
    val city: String?,
    val state: String?,
    val zip: String?,
    val county: String?,
    val email: String?,
    val ophone: String?,
    val oxtension: String?,
    val ofax: String?,
    val hphone: String?,
    val cellphone: String?,
    val carphone: String?,
    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    val estdate: Timestamp?,
    val specialty: String?,
    val ofchrs: String?,
    val ofcmanager: String?,
    @JsonSerialize(using = BooleanSerializer::class)
    @JsonDeserialize(using = BooleanDeserializer::class)
    val rate_confirm: Boolean?,
    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    val agreement: Timestamp?,
    @JsonSerialize(using = LocalDateSerializer::class)
    val agreement_perm: Timestamp?,
    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    val pktsent: Timestamp?,
    val refdby: String?,
    val preferences: String?,
    val dislikes: String?,
    @JsonSerialize(using = BooleanSerializer::class)
    @JsonDeserialize(using = BooleanDeserializer::class)
    val temphyg: Boolean?,
    @JsonSerialize(using = DoubleSerializer::class)
    val daps_dollar: Double?,
    @JsonSerialize(using = DoubleSerializer::class)
    val daps_dollar_two: Double?,
    val needs: String?,
    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    val start_date: Timestamp?,
    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    val end_date: Timestamp?,
    val days: String?,
    @JsonSerialize(using = BooleanSerializer::class)
    @JsonDeserialize(using = BooleanDeserializer::class)
    val permconf: Boolean?,
    @JsonSerialize(using = BooleanSerializer::class)
    @JsonDeserialize(using = BooleanDeserializer::class)
    val tempconf: Boolean?,
    @JsonSerialize(using = BooleanSerializer::class)
    @JsonDeserialize(using = BooleanDeserializer::class)
    val mlplcmnt: Boolean?,
    @JsonSerialize(using = BooleanSerializer::class)
    @JsonDeserialize(using = BooleanDeserializer::class)
    val lofaplcmnt: Boolean?,
    val patnttime: String?,
    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    val warndate1: Timestamp?,
    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    val warndate2: Timestamp?,
    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    val warndate3: Timestamp?,
    val cnotes: String?,
    val multioffice: String?,
    val payperiods: Int?,
    @JsonSerialize(using = BooleanSerializer::class)
    @JsonDeserialize(using = BooleanDeserializer::class)
    val yeslist: Boolean?,
    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    val filler: Timestamp?,
    @JsonSerialize(using = DoubleSerializer::class)
    val filler2: Double?
) : Serializable {
    constructor(result_set: ResultSet) : this(
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
        result_set.getString(ClientFileTable.zip.name),
        result_set.getString(ClientFileTable.county.name),
        result_set.getString(ClientFileTable.email.name),
        result_set.getString(ClientFileTable.ophone.name),
        result_set.getString(ClientFileTable.oxtension.name),
        result_set.getString(ClientFileTable.ofax.name),
        result_set.getString(ClientFileTable.hphone.name),
        result_set.getString(ClientFileTable.cellphone.name),
        result_set.getString(ClientFileTable.carphone.name),
        result_set.getString(ClientFileTable.estdate.name)?.let { Timestamp.valueOf(it) },
        result_set.getString(ClientFileTable.speciality.name),
        result_set.getString(ClientFileTable.ofchrs.name),
        result_set.getString(ClientFileTable.ofcmanager.name),
        result_set.getBoolean(ClientFileTable.rateconfirm.name),
        result_set.getString(ClientFileTable.agreement.name)?.let { Timestamp.valueOf(it) },
        result_set.getString(ClientFileTable.agreement_perm.name)?.let { Timestamp.valueOf(it) },
        result_set.getString(ClientFileTable.pktsent.name)?.let { Timestamp.valueOf(it) },
        result_set.getString(ClientFileTable.refdby.name),
        result_set.getString(ClientFileTable.preferences.name),
        result_set.getString(ClientFileTable.dislikes.name),
        result_set.getBoolean(ClientFileTable.temphyg.name),
        result_set.getDouble(ClientFileTable.daps_dollar.name),
        result_set.getDouble(ClientFileTable.daps_dollar_two.name),
        result_set.getString(ClientFileTable.needs.name),
        result_set.getString(ClientFileTable.startdate.name)?.let { Timestamp.valueOf(it) },
        result_set.getString(ClientFileTable.endate.name)?.let { Timestamp.valueOf(it) },
        result_set.getString(ClientFileTable.days.name),
        result_set.getBoolean(ClientFileTable.permconf.name),
        result_set.getBoolean(ClientFileTable.tempconf.name),
        result_set.getBoolean(ClientFileTable.mlplcmnt.name),
        result_set.getBoolean(ClientFileTable.lofaplcmnt.name),
        result_set.getString(ClientFileTable.patnttime.name),
        result_set.getString(ClientFileTable.warndate1.name)?.let { Timestamp.valueOf(it) },
        result_set.getString(ClientFileTable.warndate2.name)?.let { Timestamp.valueOf(it) },
        result_set.getString(ClientFileTable.warndate3.name)?.let { Timestamp.valueOf(it) },
        result_set.getString(ClientFileTable.cnotes.name),
        result_set.getString(ClientFileTable.multioffice.name),
        result_set.getInt(ClientFileTable.payperiods.name),
        result_set.getBoolean(ClientFileTable.yeslist.name),
        result_set.getString(ClientFileTable.filler.name)?.let { Timestamp.valueOf(it) },
        result_set.getDouble(ClientFileTable.filler2.name)
    )
}
