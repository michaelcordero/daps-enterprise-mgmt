package utilities

import io.ktor.utils.io.errors.IOException
import model.*
import org.h2.tools.Csv
import java.io.File
import java.sql.ResultSet
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
    fun process(directory: File) {
        try {
//            val dao: LocalDataService = LocalDataService()
            println("Directory passed in: $directory")
            val files: List<File> = directory.listFiles()?.filterNotNull().orEmpty()
            for (f in files){
                if (f.name.endsWith(".csv") && f.name == "Temps.csv") {
                    println("=======================================")
                    println("Processing... ${f.name}")
                    val result_set: ResultSet = Csv().read("${directory}/${f.name}", null, null)
                    println("=======================================")
                    while (result_set.next()) {
                            when(f.name) {
                                "Billing.csv" -> println(Billing(result_set)) //application.dao.createBilling(Billing(result_set))
                                "ClientFile.csv" -> println(ClientFile(result_set))
                                "ClientNotes.csv" -> println(ClientNotes(result_set))
                                "ClientPermNotes.csv" -> println(ClientPermNotes(result_set))
                                "DAPSaddress.csv" -> println(DAPSAddress(result_set))
                                "DAPS Staff Messages.csv" -> println(DAPSStaffMessages(result_set))
                                "DAPSstaff.csv" -> println(DAPSStaff(result_set))
                                "InterviewGuide.csv" -> println(InterviewGuide(result_set))
                                "JobFunctionDropDown.csv" -> println(JobFunction(result_set))
                                "Paste Errors.csv" -> println(PasteErrors(result_set))
                                "Payment.csv" -> println(Payment(result_set))
                                "PermNotes.csv" -> println(PermNotes(result_set))
                                "PermReqNotes.csv" -> println(PermReqNotes(result_set))
                                "TempNotes.csv" -> println(TempNotes(result_set))
                                "Temps.csv" -> println(Temps(result_set))
                        }
                    }
                }
            }
        } catch (e: Exception) {
            println("Error occurred during migration:")
            println(e.message)
        } finally {
//            dao.close()
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
