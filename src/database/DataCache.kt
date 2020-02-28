package com.daps.ent.database

import com.daps.ent.facades.DataService
import java.io.File

class DataCache(val delegate: DataService, val storage: File)
