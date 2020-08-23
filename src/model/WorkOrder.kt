
package model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import database.tables.WorkOrderTable
import model.deserializers.BooleanDeserializer
import model.deserializers.DoubleDeserializer
import model.deserializers.LocalDateDeserializer
import model.deserializers.LocalTimeDeserializer
import model.serializers.BooleanSerializer
import model.serializers.DoubleSerializer
import model.serializers.LocalDateSerializer
import model.serializers.LocalTimeSerializer
import java.io.Serializable
import java.sql.ResultSet
import java.sql.Timestamp

data class WorkOrder
    @JsonCreator constructor(
        @JsonProperty(value = "wo_number", required = true)
        val wo_number: Int,
        @JsonProperty(value = "client_num", required = true)
        val client_num: Int,
        val emp_num: Int?,
        val temp_perm: String?,
        @JsonSerialize(using = LocalDateSerializer::class)
        @JsonDeserialize(using = LocalDateDeserializer::class)
        val filled_date: Timestamp?,
        @JsonSerialize(using = DoubleSerializer::class)
        @JsonDeserialize(using = DoubleDeserializer::class)
        val filled_rate: Double?,
        @JsonSerialize(using = LocalDateSerializer::class)
        @JsonDeserialize(using = LocalDateDeserializer::class)
        val start_date: Timestamp?,
        @JsonSerialize(using = LocalTimeSerializer::class)
        @JsonDeserialize(using = LocalTimeDeserializer::class)
        val start_time: Timestamp?,
        @JsonSerialize(using = LocalTimeSerializer::class)
        @JsonDeserialize(using = LocalTimeDeserializer::class)
        val end_time: Timestamp?,
        val services_category: String?,
        val job_description: String?,
        val skills_required: String?,
        val work_hours: String?,
        @JsonSerialize(using = BooleanSerializer::class)
        @JsonDeserialize(using = BooleanDeserializer::class)
        val will_train: Boolean?,
        @JsonSerialize(using = BooleanSerializer::class)
        @JsonDeserialize(using = BooleanDeserializer::class)
        val confidential: Boolean?,
        val contact_name: String?,
        @JsonSerialize(using = BooleanSerializer::class)
        @JsonDeserialize(using = BooleanDeserializer::class)
        val fees_discussed: Boolean?,
        val note: String?,
        val entered_by: String?,
        @JsonSerialize(using = LocalDateSerializer::class)
        @JsonDeserialize(using = LocalDateDeserializer::class)
        val entered_date: Timestamp?,
        @JsonSerialize(using = BooleanSerializer::class)
        @JsonDeserialize(using = BooleanDeserializer::class)
        val post: Boolean?,
        @JsonSerialize(using = BooleanSerializer::class)
        @JsonDeserialize(using = BooleanDeserializer::class)
        val active: Boolean?,
        @JsonSerialize(using = LocalDateSerializer::class)
        @JsonDeserialize(using = LocalDateDeserializer::class)
        val left_message: Timestamp?,
        @JsonSerialize(using = BooleanSerializer::class)
        @JsonDeserialize(using = BooleanDeserializer::class)
        val confirmed: Boolean?) : Serializable {
    constructor(result_set: ResultSet) : this (
        result_set.getInt(WorkOrderTable.wo_number.name),
        result_set.getInt(WorkOrderTable.client_num.name),
        result_set.getInt(WorkOrderTable.emp_num.name),
        result_set.getString(WorkOrderTable.temp_perm.name),
        result_set.getString(WorkOrderTable.filled_date.name)?.let { Timestamp.valueOf(it) },
        result_set.getDouble(WorkOrderTable.filled_rate.name),
        result_set.getString(WorkOrderTable.start_date.name)?.let { Timestamp.valueOf(it) },
        result_set.getString(WorkOrderTable.start_time.name)?.let { Timestamp.valueOf(it) },
        result_set.getString(WorkOrderTable.end_time.name)?.let { Timestamp.valueOf(it) },
        result_set.getString(WorkOrderTable.services_category.name),
        result_set.getString(WorkOrderTable.job_description.name),
        result_set.getString(WorkOrderTable.skills_required.name),
        result_set.getString(WorkOrderTable.work_hours.name),
        result_set.getBoolean(WorkOrderTable.will_train.name),
        result_set.getBoolean(WorkOrderTable.confidential.name),
        result_set.getString(WorkOrderTable.contact_name.name),
        result_set.getBoolean(WorkOrderTable.fees_discussed.name),
        result_set.getString(WorkOrderTable.note.name),
        result_set.getString(WorkOrderTable.entered_by.name),
        result_set.getString(WorkOrderTable.entered_date.name)?.let { Timestamp.valueOf(it) },
        result_set.getBoolean(WorkOrderTable.post.name),
        result_set.getBoolean(WorkOrderTable.active.name),
        result_set.getString(WorkOrderTable.left_message.name)?.let { Timestamp.valueOf(it) },
        result_set.getBoolean(WorkOrderTable.confirmed.name)
    )
}
