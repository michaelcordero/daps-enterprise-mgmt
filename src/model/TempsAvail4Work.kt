package model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import database.tables.TempsAvail4WorkTable
import model.deserializers.LocalDateDeserializer
import model.serializers.LocalDateSerializer
import java.io.Serializable
import java.sql.ResultSet
import java.sql.Timestamp

data class TempsAvail4Work
@JsonCreator constructor(
    @JsonProperty(value = "rec_num", required = true)
    val rec_num: Int,
    val emp_num: Int?,
    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    val date_can_work: Timestamp?
) : Serializable {
    constructor(result_set: ResultSet) : this(
        result_set.getInt(TempsAvail4WorkTable.rec_num.name),
        result_set.getInt(TempsAvail4WorkTable.emp_num.name),
        result_set.getString(TempsAvail4WorkTable.date_can_work.name)?.let { Timestamp.valueOf(it) }
    )
}
