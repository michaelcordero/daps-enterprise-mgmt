package database

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.IDateColumnType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.vendors.currentDialect
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalTime

class JavaLocalTimeColumnType(override val hasTimePart: Boolean) : ColumnType(), IDateColumnType {
    override fun sqlType(): String {
        return currentDialect.dataTypeProvider.dateTimeType()
    }

    override fun nonNullValueToString(value: Any): String {
        val localTime = when (value) {
            is String -> return LocalTime.parse(value).toString()
            is Instant -> Timestamp.from(value).toLocalDateTime().toLocalTime().toString()
            else -> error("Unexpected value: $value of ${value::class.qualifiedName}")
        }
        return localTime
    }

    override fun notNullValueToDB(value: Any): Any {
        if (value is Instant){
            return Timestamp.from(value).toLocalDateTime().toLocalTime()
        } else if (value is String){
            return LocalTime.parse(value)
        }
        return value;
    }
}
/**
 * A LocalTime column to store only time
 */
fun Table.localtime(name: String): Column<LocalTime> = registerColumn(name, JavaLocalTimeColumnType(true))

