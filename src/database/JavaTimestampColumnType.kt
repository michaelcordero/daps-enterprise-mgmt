package database

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.IDateColumnType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.vendors.currentDialect
import java.sql.Timestamp
import java.time.Instant


class JavaTimestampColumnType : ColumnType(), IDateColumnType {
    override fun sqlType(): String {
       return currentDialect.dataTypeProvider.dateTimeType()
    }

    override fun nonNullValueToString(value: Any): String {
        val timestamp = when (value) {
            is String -> return value
            is Instant -> Timestamp.from(value).toString()
            is Timestamp -> value.toString()
            else -> error("Unexpected value: $value of ${value::class.qualifiedName}")
        }
        return timestamp
    }

    override fun valueFromDB(value: Any): Timestamp = when (value) {
            is Timestamp -> value
            is String -> Timestamp.valueOf(value)
            else -> valueFromDB(value.toString())
        }

    override fun notNullValueToDB(value: Any): Any {
        if (value is Instant) {
            return Timestamp.from(value)
        } else if (value is String) {
            return Timestamp.valueOf(value)
        }
        return value
    }
}

/**
 * A timestamp column to store both a date and a time.
 *
 * @param name The column name
 */
fun Table.realtimestamp(name: String): Column<Timestamp> = registerColumn(name, JavaTimestampColumnType())
