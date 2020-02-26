package com.daps.ent.utilities

import io.ktor.utils.io.errors.IOException
import org.h2.tools.Csv
import java.io.File
import java.sql.ResultSet
import java.sql.ResultSetMetaData
import kotlin.system.exitProcess

/**
 * Both H2 and Flyway have existing migration tools that will take a .sql file and migrate it to the database dialect of
 * your choice with the commands:
 *
 * H2: java -cp ~/h2/bin/h2*.jar org.h2.tools.RunScript -url jdbc:h2:/db-url -script /daps.sql
 * Flyway: flyway migrate (after you made the /conf/flyway.conf file changes of course)
 *
 * However, neither of these worked for me, the data given could be just bad data. In the event that it's not,
 * CSV did produce consistent results, but of course the data still has to be normalized and then inserted into the
 * app's database. Thus the purpose of this program is to serve as a data processor and batch database updater.
 */
class H2exCSV {
    fun process(directory: File): Unit {
        println("Directory passed in: ${directory}")
        val files: List<File> = directory.listFiles()?.filterNotNull().orEmpty()
        for (f in files){
//            if (f.name.equals("DAPSaddress.csv")) {
            if (f.name.endsWith(".csv")){
                println("=======================================")
                println("Processing... ${f.name}")
                val result_set: ResultSet = Csv().read("${directory}/${f.name}", null, null)
                val meta: ResultSetMetaData = result_set.metaData
                println("=======================================")
                while (result_set.next()) {
                    // Print the column labels once
                    if ( result_set.row.equals(1) ){
                        for (i in 0..meta.columnCount-1) {
                            print(meta.getColumnLabel(i+1)+"  \t")
                        }
                    }
                    println()
                    // Print each of the rows
                    for (i in 0..meta.columnCount-1) {
                        print(result_set.getString(i+1)+"   \t")
                    }
                    println()
                }
                println("=======================================")
                result_set.close()
            }
//            }
        }
    }
}

fun main(args: Array<String>) {
    println("=======================================")
    println("=============CSV Processor=============")
    println("=======================================")
    try {
        val path: String
        if ( args.isEmpty()){
            println("No root directory passed in. Exiting process...")
            exitProcess(-1)
        } else {
            path = args[0]
        }
        val file = File(path)
        if (!file.isDirectory){
            throw IOException("File must be a directory.")
        }
        val h2csv = H2exCSV()
        h2csv.process(file)
    } catch (e: Exception){
        println("Error processing files: ${e.cause}")
    } finally {
        println("===================================")
    }
}
