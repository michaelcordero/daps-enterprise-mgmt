package database.queries

import database.tables.InterviewGuideTable
import model.InterviewGuide
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

interface InterviewGuideQuery {
    // Abstract property initialized by LocalDataQuery
    val db: Database
    /**
     * Create
     */
    fun createInterviewGuide(ig: InterviewGuide): Int {
        return transaction (db) {
            InterviewGuideTable.insert {
                // id is auto-incremented
                it[client_num] = ig.client_num
                it[client_contact] = ig.client_contact
                it[employee_num] = ig.employee_num
                it[employee_name] = ig.employee_name
                it[referral_date] = ig.referral_date
                it[referral_notes] = ig.referral_notes
                it[interview_complete] = ig.interview_complete
                it[interview_notes] = ig.interview_notes
                it[wo_number] = ig.wo_number
                it[emp_notes_id] = ig.emp_notes_id
                it[client_notes_id] = ig.client_notes_id
            }
        } get InterviewGuideTable.ID
    }

    fun insertInterviewGuide(ig: InterviewGuide) {
        transaction (db) {
            InterviewGuideTable.insert {
                it[ID] = ig.id!!
                it[client_num] = ig.client_num
                it[client_contact] = ig.client_contact
                it[employee_num] = ig.employee_num
                it[employee_name] = ig.employee_name
                it[referral_date] = ig.referral_date
                it[referral_notes] = ig.referral_notes
                it[interview_complete] = ig.interview_complete
                it[interview_notes] = ig.interview_notes
                it[wo_number] = ig.wo_number
                it[emp_notes_id] = ig.emp_notes_id
                it[client_notes_id] = ig.client_notes_id
            }
        }
    }

    /**
     * Read
     */

    fun allInterviewGuides() : List<InterviewGuide> {
        return transaction (db) {
            InterviewGuideTable.selectAll().toList()
        }.map {
            InterviewGuide(
                it[InterviewGuideTable.ID],
                it[InterviewGuideTable.client_num],
                it[InterviewGuideTable.client_contact],
                it[InterviewGuideTable.employee_num],
                it[InterviewGuideTable.employee_name],
                it[InterviewGuideTable.referral_date],
                it[InterviewGuideTable.referral_notes],
                it[InterviewGuideTable.interview_complete],
                it[InterviewGuideTable.interview_notes],
                it[InterviewGuideTable.wo_number],
                it[InterviewGuideTable.emp_notes_id],
                it[InterviewGuideTable.client_notes_id]
            )
        }
    }

    /**
     * Update
     */

    fun updateInterviewGuide(ig: InterviewGuide): Int {
        return transaction (db) {
            InterviewGuideTable.update({
                InterviewGuideTable.ID.eq(ig.id!!)
            }) {
                it[client_num] = ig.client_num
                it[client_contact] = ig.client_contact
                it[employee_num] = ig.employee_num
                it[employee_name] = ig.employee_name
                it[referral_date] = ig.referral_date
                it[referral_notes] = ig.referral_notes
                it[interview_complete] = ig.interview_complete
                it[interview_notes] = ig.interview_notes
                it[wo_number] = ig.wo_number
                it[emp_notes_id] = ig.emp_notes_id
                it[client_notes_id] = ig.client_notes_id
            }
        }
    }

    /**
     * Delete
     */

    fun deleteInterviewGuide(id: Int): Int {
        return transaction (db) {
            InterviewGuideTable.deleteWhere {
                InterviewGuideTable.ID.eq(id)
            }
        }
    }

}
