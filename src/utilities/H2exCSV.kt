package utilities

import database.LocalDataQuery
import database.queries.DataQuery
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.*
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

private fun countRows(resultSet: ResultSet): Int {
    var counter = 0
    while (resultSet.next()) {
        counter++
}
    return counter
}

suspend fun process(directory: File) {
    val start: Long = System.currentTimeMillis()
    val dq: DataQuery = LocalDataQuery()
    try {
        println("Directory passed in: $directory")
        println("=======================================")
        val files: List<File> = directory.listFiles()?.filterNotNull().orEmpty()
        val jobs: MutableList<Job> = ArrayList()
            for (f in files) {
                    val counterSet: ResultSet = Csv().read("${directory}/${f.name}", null, null)
                    val totalRows: Int = countRows(counterSet)
                    counterSet.close()
                    if (totalRows == 0) continue // "no rows to process" // ¯\_(ツ)_/¯
                    val job = CoroutineScope(Dispatchers.IO).launch {
                        ProgressBar(
                            "Processing...${f.name}",
                            totalRows.toLong(),
                            ProgressBarStyle.ASCII
                        ).use { pb ->
                            val resultSet: ResultSet = Csv().read("${directory}/${f.name}", null, null)
                            while (resultSet.next()) {
                                when (f.name) {
                                    "AccountRepDropDown.csv" -> dq.insertAccountRep(AccountRep(resultSet))
                                        .also { pb.step() }
                                    "BillTypeDropDown.csv" -> (BillType(resultSet)).also { pb.step() }
                                    "Billing.csv" -> dq.insertBilling(Billing(resultSet)).also { pb.step() }
                                    "ClientFile.csv" -> dq.insertClientFile(ClientFile(resultSet))
                                        .also { pb.step() }
                                    "ClientNotes.csv" -> dq.insertClientNotes(ClientNotes(resultSet))
                                        .also { pb.step() }
                                    "ClientPermNotes.csv" -> dq.insertClientPermNotes(ClientPermNotes(resultSet))
                                        .also { pb.step() }
                                    "DAPS Staff Messages.csv" -> dq.insertDAPSStaffMessages(
                                        DAPSStaffMessages(
                                            resultSet
                                        )
                                    )
                                        .also { pb.step() }
                                    "DAPSaddress.csv" -> dq.insertDAPSAddress(DAPSAddress(resultSet))
                                        .also { pb.step() }
                                    "DAPSstaff.csv" -> dq.insertDAPSStaff(DAPSStaff(resultSet))
                                        .also { pb.step() }
                                    "InterviewGuide.csv" -> dq.insertInterviewGuide(InterviewGuide(resultSet))
                                        .also { pb.step() }
                                    "JobFunctionDropDown.csv" -> (JobFunction(resultSet)).also { pb.step() }
                                    "Paste Errors.csv" -> dq.insertPasteErrors(PasteErrors(resultSet))
                                        .also { pb.step() }
                                    "Payment.csv" -> dq.insertPayment(Payment(resultSet)).also { pb.step() }
                                    "PermNotes.csv" -> dq.insertPermNotes(PermNotes(resultSet))
                                        .also { pb.step() }
                                    "PermReqNotes.csv" -> dq.insertPermReqNotes(PermReqNotes(resultSet))
                                        .also { pb.step() }
                                    "TempNotes.csv" -> dq.insertTempNote(TempNotes(resultSet))
                                        .also { pb.step() }
                                    "Temps.csv" -> dq.insertTemp(Temps(resultSet)).also { pb.step() }
                                    "TempsAvail4Work.csv" -> dq.insertTempAvail4Work(TempsAvail4Work(resultSet))
                                        .also { pb.step() }
                                    "WOnotes.csv" -> dq.insertWONotes(WONotes(resultSet)).also { pb.step() }
                                    "WorkOrder.csv" -> dq.insertWorkOrder(WorkOrder(resultSet))
                                        .also { pb.step() }
                                }
                            }
                        }
                    }
                jobs.add(job)
            }
        jobs.joinAll()
        jobs.last().invokeOnCompletion { dq.close() }
    } catch (e: Exception) {
        println("Error occurred during migration:")
        println(e)
    } finally {
        val end: Long = System.currentTimeMillis()
        println("=======================================")
        println("Total Processing Time: ${(end - start) / 1000}/seconds")
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
    runBlocking {
        h2csv.process(file)
    }
} catch (e: Exception) {
    println("Error processing files: ${e.cause}")
} finally {
    println("===================================")
}
}
