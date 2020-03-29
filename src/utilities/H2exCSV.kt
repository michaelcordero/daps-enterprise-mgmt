package utilities

import database.LocalDataQuery
import database.queries.DataQuery
import io.ktor.utils.io.errors.IOException
import me.tongfei.progressbar.ProgressBar
import me.tongfei.progressbar.ProgressBarStyle
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

    fun count_rows(resultSet: ResultSet): Int {
        var counter = 0
        while (resultSet.next()) {
            counter++
        }
        return counter
    }

    fun process(directory: File) {
        val start: Long = System.currentTimeMillis()
        val dq: DataQuery = LocalDataQuery()
        try {
            println("Directory passed in: $directory")
            println("=======================================")
            val files: List<File> = directory.listFiles()?.filterNotNull().orEmpty()
            for (f in files) {
                if (f.name.endsWith(".csv")) {
                    val counter_set: ResultSet = Csv().read("${directory}/${f.name}", null, null)
                    val total_rows: Int = count_rows(counter_set)
                    counter_set.close()
                    if (total_rows == 0 ) continue // no rows to process  ¯\_(ツ)_/¯
                    ProgressBar("Processing...${f.name}",total_rows.toLong(), ProgressBarStyle.ASCII).use { pb ->
                        val result_set: ResultSet = Csv().read("${directory}/${f.name}", null, null)
                        while (result_set.next()) {
                            when (f.name) {
                                "AccountRepDropDown.csv" -> dq.insertAccountRep(AccountRep(result_set)).also { pb.step() }
                                "BillTypeDropDown.csv" -> (BillType(result_set)).also { pb.step() }
                                "Billing.csv" -> dq.insertBilling(Billing(result_set)).also { pb.step() }
                                "ClientFile.csv" -> dq.insertClientFile(ClientFile(result_set)).also { pb.step() }
                                "ClientNotes.csv" -> dq.insertClientNotes(ClientNotes(result_set)).also { pb.step() }
                                "ClientPermNotes.csv" -> dq.insertClientPermNotes(ClientPermNotes(result_set)).also { pb.step() }
                                "DAPS Staff Messages.csv" -> dq.insertDAPSStaffMessages(DAPSStaffMessages(result_set)).also { pb.step() }
                                "DAPSaddress.csv" -> dq.insertDAPSAddress(DAPSAddress(result_set)).also { pb.step() }
                                "DAPSstaff.csv" -> dq.insertDAPSStaff(DAPSStaff(result_set)).also { pb.step() }
                                "InterviewGuide.csv" -> dq.insertInterviewGuide(InterviewGuide(result_set)).also { pb.step() }
                                "JobFunctionDropDown.csv" -> (JobFunction(result_set)).also { pb.step() }
                                "Paste Errors.csv" -> dq.insertPasteErrors(PasteErrors(result_set)).also { pb.step() }
                                "Payment.csv" -> dq.insertPayment(Payment(result_set)).also { pb.step() }
                                "PermNotes.csv" -> dq.insertPermNotes(PermNotes(result_set)).also { pb.step() }
                                "PermReqNotes.csv" -> dq.insertPermReqNotes(PermReqNotes(result_set)).also { pb.step() }
                                "TempNotes.csv" -> dq.insertTempNote(TempNotes(result_set)).also { pb.step() }
                                "Temps.csv" -> dq.insertTemp(Temps(result_set)).also { pb.step() }
                                "TempsAvail4Work.csv" -> dq.insertTempAvail4Work(TempsAvail4Work(result_set)).also { pb.step() }
                                "WOnotes.csv" -> dq.insertWONotes(WONotes(result_set)).also { pb.step() }
                                "WorkOrder.csv" -> dq.insertWorkOrder(WorkOrder(result_set)).also { pb.step() }
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            println("Error occurred during migration:")
            println(e)
        } finally {
            val end: Long = System.currentTimeMillis()
            println("=======================================")
            println("Total Processing Time: ${(end - start)/1000}/seconds")
            dq.close()
        }
    }
}

fun main(args: Array<String>) {
    println("=======================================")
    println("=============CSV Processor=============")
    println("=======================================")
    try {
        val path: String
        if (args.isEmpty()) {
            println("No root directory passed in. Exiting process...")
            exitProcess(-1)
        } else {
            path = args[0]
        }
        val file = File(path)
        if (!file.isDirectory) {
            throw IOException("File must be a directory.")
        }
        val h2csv = H2exCSV()
        h2csv.process(file)
    } catch (e: Exception) {
        println("Error processing files: ${e.cause}")
    } finally {
        println("===================================")
    }
}
