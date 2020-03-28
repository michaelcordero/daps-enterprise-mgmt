package database

import database.queries.DataService
import java.io.File

class DataCache(val delegate: DataService, val storage: File)
