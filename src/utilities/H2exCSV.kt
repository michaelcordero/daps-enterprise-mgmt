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

    fun <T> doSomething(data: T) {
        // pretend like we're processing data...
    }

    fun count_rows(resultSet: ResultSet): Int {
        var counter = 0
        while (resultSet.next()) {
            counter++
        }
        return counter
    }

    fun process(directory: File) {
        val start: Long = System.currentTimeMillis()
        try {
//            val dao: LocalDataService = LocalDataService()
            println("Directory passed in: $directory")
            val files: List<File> = directory.listFiles()?.filterNotNull().orEmpty()
            for (f in files) {
                if (f.name.endsWith(".csv")) {
                    println("=======================================")
                    println("Processing: ${f.name}...")
                    val counter_set: ResultSet = Csv().read("${directory}/${f.name}", null, null)
                    val total_rows: Int = count_rows(counter_set)
                    counter_set.close()
                    val result_set: ResultSet = Csv().read("${directory}/${f.name}", null, null)
                    var cursor = 0
                    println("=======================================")
                    while (result_set.next()) {
                        when (f.name) {
                            "AccountRepDropDown.csv" -> doSomething(AccountRep(result_set)).also {
                                cursor++
                                print("${cursor}/${total_rows}\r")
                            }
                            "BillTypeDropDown.csv" -> doSomething(BillType(result_set)).also {
                                cursor++
                                print("${cursor}/${total_rows}\r")
                            }
                            "Billing.csv" -> doSomething(Billing(result_set)).also {
                                cursor++
                                print("${cursor}/${total_rows}\r")
                            }
                            "ClientFile.csv" -> doSomething(ClientFile(result_set)).also {
                                cursor++
                                print("${cursor}/${total_rows}\r")
                            }
                            "ClientNotes.csv" -> doSomething(ClientNotes(result_set)).also {
                                cursor++
                                print("${cursor}/${total_rows}\r")
                            }
                            "ClientPermNotes.csv" -> doSomething(ClientPermNotes(result_set)).also {
                                cursor++
                                print("${cursor}/${total_rows}\r")
                            }
                            "DAPS Staff Messages.csv" -> doSomething(DAPSStaffMessages(result_set)).also {
                                cursor++
                                print("${cursor}/${total_rows}\r")
                            }
                            "DAPSaddress.csv" -> doSomething(DAPSAddress(result_set)).also {
                                cursor++
                                print("${cursor}/${total_rows}\r")
                            }
                            "DAPSstaff.csv" -> doSomething(DAPSStaff(result_set)).also {
                                cursor++
                                print("${cursor}/${total_rows}\r")
                            }
                            "InterviewGuide.csv" -> doSomething(InterviewGuide(result_set)).also {
                                cursor++
                                print("${cursor}/${total_rows}\r")
                            }
                            "JobFunctionDropDown.csv" -> doSomething(JobFunction(result_set)).also {
                                cursor++
                                print("${cursor}/${total_rows}\r")

                            }
                            "Paste Errors.csv" -> doSomething(PasteErrors(result_set)).also {
                                cursor++
                                print("${cursor}/${total_rows}\r")
                            }
                            "Payment.csv" -> doSomething(Payment(result_set)).also {
                                cursor++
                                print("${cursor}/${total_rows}\r")
                            }
                            "PermNotes.csv" -> doSomething(PermNotes(result_set)).also {
                                cursor++
                                print("${cursor}/${total_rows}\r")
                            }
                            "PermReqNotes.csv" -> doSomething(PermReqNotes(result_set)).also {
                                cursor++
                                print("${cursor}/${total_rows}\r")
                            }
                            "TempNotes.csv" -> doSomething(TempNotes(result_set)).also {
                                cursor++
                                print("${cursor}/${total_rows}\r")
                            }
                            "Temps.csv" -> doSomething(Temps(result_set)).also {
                                cursor++
                                print("${cursor}/${total_rows}\r")
                            }
                            "TempsAvail4Work.csv" -> doSomething(TempsAvail4Work(result_set)).also {
                                cursor++
                                print("${cursor}/${total_rows}\r")
                            }
                            "WOnotes.csv" -> doSomething(WONotes(result_set)).also {
                                cursor++
                                print("${cursor}/${total_rows}\r")
                            }
                            "WorkOrder.csv" -> doSomething(WorkOrder(result_set)).also {
                                cursor++
                                print("${cursor}/${total_rows}\r")
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
            println("Total Processing Time: ${(end - start)/1000}/seconds")
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
