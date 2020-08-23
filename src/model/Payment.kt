package model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import database.tables.PaymentTable
import model.deserializers.DoubleDeserializer
import model.deserializers.LocalDateDeserializer
import model.serializers.DoubleSerializer
import model.serializers.LocalDateSerializer
import java.io.Serializable
import java.sql.ResultSet
import java.sql.Timestamp

data class Payment
    @JsonCreator constructor(
        @JsonProperty(value = "client_num", required = true)
        val client_num: Int,
        val pmt_type: String?,
        val ref_num: String?,
        @JsonSerialize(using = LocalDateSerializer::class)
        @JsonDeserialize(using = LocalDateDeserializer::class)
        val pmt_date: Timestamp?,
        @JsonSerialize(using = DoubleSerializer::class)
        @JsonDeserialize(using = DoubleDeserializer::class)
        val amount: Double?) : Serializable {
    constructor(result_set: ResultSet) : this (
        result_set.getInt(PaymentTable.client_num.name),
        result_set.getString(PaymentTable.pmt_type.name),
        result_set.getString(PaymentTable.ref_num.name),
        Timestamp.valueOf(result_set.getString(PaymentTable.pmt_date.name)),
        result_set.getDouble(PaymentTable.amount.name)
    )
}
