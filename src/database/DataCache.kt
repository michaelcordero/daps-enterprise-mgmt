package database

import database.facades.DataService
import java.io.File

class DataCache(val delegate: DataService, val storage: File)
