package model

import java.sql.ResultSet

interface ResultSetConstructor<T> {
    fun create(resultSet: ResultSet) : T
}

