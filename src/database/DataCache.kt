package database

import database.queries.DataQuery
import java.io.File

class DataCache(val delegate: DataQuery, val storage: File)
