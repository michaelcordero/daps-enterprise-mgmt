package database.facades

import database.tables.ClientFileTable
import model.ClientFile
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

interface ClientFileData {
    // Abstract property initialized by LocalDataService
val db: Database

    /**
     * Create
     */

    fun createClientFile(cf: ClientFile) {
        transaction (db) {
            ClientFileTable.insert {
//                it[client_num] = cf.client_num auto-increment
                it[ofcname] = cf.ofcname
                it[firstname1] = cf.firstname1
                it[lastname1] = cf.lastname1
                it[firstname2] = cf.firstname2
                it[lastname2] = cf.lastname2
                it[address1] = cf.address1
                it[address2] = cf.address2
                it[city] = cf.city
                it[state] = cf.state
                it[zip] = cf.zip
                it[county] = cf.county
                it[email] = cf.email
                it[ophone] = cf.ophone
                it[oxtension] = cf.oxtension
                it[ofax] = cf.ofax
                it[hphone] = cf.hphone
                it[cellphone] = cf.cellphone
                it[carphone] = cf.carphone
                it[estdate] = cf.estdate
                it[speciality] = cf.specialty
                it[ofchrs] = cf.ofchrs
                it[ofcmanager] = cf.ofcmanager
                it[rateconfirm] = cf.rate_confirm
                it[agreement] = cf.agreement
                it[agreement_perm] = cf.agreement_perm
                it[pktsent] = cf.pktsent
                it[refdby] = cf.refdby
                it[preferences] = cf.preferences
                it[dislikes] = cf.dislikes
                it[temphyg] = cf.temphyg
                it[daps_dollar] = cf.daps_dollar
                it[daps_dollar_two] = cf.daps_dollar_two
                it[needs] = cf.needs
                it[startdate] = cf.start_date
                it[endate] = cf.end_date
                it[days] = cf.days
                it[permconf] = cf.permconf
                it[tempconf] = cf.tempconf
                it[mlplcmnt] = cf.mlplcmnt
                it[lofaplcmnt] = cf.lofaplcmnt
                it[patnttime] = cf.patnttime
                it[warndate1] = cf.warndate1
                it[warndate2] = cf.warndate2
                it[warndate3] = cf.warndate3
                it[cnotes] = cf.cnotes
                it[multioffice] = cf.multioffice
                it[payperiods] = cf.payperiods
                it[yeslist] = cf.yeslist
                it[filler] = cf.filler
                it[filler2] = cf.filler2
            }
        }
    }

    /**
     * Read
     */

    fun allClientFiles(): List<ClientFile> = transaction (db) {
        ClientFileTable.selectAll().toMutableList()
    }.map {
        ClientFile(it[ClientFileTable.client_num],
            it[ClientFileTable.ofcname],
            it[ClientFileTable.firstname1],
            it[ClientFileTable.lastname1],
            it[ClientFileTable.firstname2],
            it[ClientFileTable.lastname2],
            it[ClientFileTable.address1],
            it[ClientFileTable.address2],
            it[ClientFileTable.city],
            it[ClientFileTable.state],
            it[ClientFileTable.zip],
            it[ClientFileTable.county],
            it[ClientFileTable.email],
            it[ClientFileTable.ophone],
            it[ClientFileTable.oxtension],
            it[ClientFileTable.ofax],
            it[ClientFileTable.hphone],
            it[ClientFileTable.cellphone],
            it[ClientFileTable.carphone],
            it[ClientFileTable.estdate],
            it[ClientFileTable.speciality],
            it[ClientFileTable.ofchrs],
            it[ClientFileTable.ofcmanager],
            it[ClientFileTable.rateconfirm],
            it[ClientFileTable.agreement],
            it[ClientFileTable.agreement_perm],
            it[ClientFileTable.pktsent],
            it[ClientFileTable.refdby],
            it[ClientFileTable.preferences],
            it[ClientFileTable.dislikes],
            it[ClientFileTable.temphyg],
            it[ClientFileTable.daps_dollar],
            it[ClientFileTable.daps_dollar_two],
            it[ClientFileTable.needs],
            it[ClientFileTable.startdate],
            it[ClientFileTable.endate],
            it[ClientFileTable.days],
            it[ClientFileTable.permconf],
            it[ClientFileTable.tempconf],
            it[ClientFileTable.mlplcmnt],
            it[ClientFileTable.lofaplcmnt],
            it[ClientFileTable.patnttime],
            it[ClientFileTable.warndate1],
            it[ClientFileTable.warndate2],
            it[ClientFileTable.warndate3],
            it[ClientFileTable.cnotes],
            it[ClientFileTable.multioffice],
            it[ClientFileTable.payperiods],
            it[ClientFileTable.yeslist],
            it[ClientFileTable.filler],
            it[ClientFileTable.filler2])
    }

    fun readClientFile(client_num: Int) : List<ClientFile> = transaction (db) {
        ClientFileTable.select {
            ClientFileTable.client_num.eq(client_num)
        }.mapNotNull {
            ClientFile(
                it[ClientFileTable.client_num],
                it[ClientFileTable.ofcname],
                it[ClientFileTable.firstname1],
                it[ClientFileTable.lastname1],
                it[ClientFileTable.firstname2],
                it[ClientFileTable.lastname2],
                it[ClientFileTable.address1],
                it[ClientFileTable.address2],
                it[ClientFileTable.city],
                it[ClientFileTable.state],
                it[ClientFileTable.zip],
                it[ClientFileTable.county],
                it[ClientFileTable.email],
                it[ClientFileTable.ophone],
                it[ClientFileTable.oxtension],
                it[ClientFileTable.ofax],
                it[ClientFileTable.hphone],
                it[ClientFileTable.cellphone],
                it[ClientFileTable.carphone],
                it[ClientFileTable.estdate],
                it[ClientFileTable.speciality],
                it[ClientFileTable.ofchrs],
                it[ClientFileTable.ofcmanager],
                it[ClientFileTable.rateconfirm],
                it[ClientFileTable.agreement],
                it[ClientFileTable.agreement_perm],
                it[ClientFileTable.pktsent],
                it[ClientFileTable.refdby],
                it[ClientFileTable.preferences],
                it[ClientFileTable.dislikes],
                it[ClientFileTable.temphyg],
                it[ClientFileTable.daps_dollar],
                it[ClientFileTable.daps_dollar_two],
                it[ClientFileTable.needs],
                it[ClientFileTable.startdate],
                it[ClientFileTable.endate],
                it[ClientFileTable.days],
                it[ClientFileTable.permconf],
                it[ClientFileTable.tempconf],
                it[ClientFileTable.mlplcmnt],
                it[ClientFileTable.lofaplcmnt],
                it[ClientFileTable.patnttime],
                it[ClientFileTable.warndate1],
                it[ClientFileTable.warndate2],
                it[ClientFileTable.warndate3],
                it[ClientFileTable.cnotes],
                it[ClientFileTable.multioffice],
                it[ClientFileTable.payperiods],
                it[ClientFileTable.yeslist],
                it[ClientFileTable.filler],
                it[ClientFileTable.filler2]
            )
        }
    }

    /**
     * Update
     */

    fun updateClientFile(cf: ClientFile) {
        transaction (db) {
            ClientFileTable.update({
                ClientFileTable.client_num.eq(cf.client_num)
            }) {
                it[client_num] = cf.client_num
                it[ofcname] = cf.ofcname
                it[firstname1] = cf.firstname1
                it[lastname1] = cf.lastname1
                it[firstname2] = cf.firstname2
                it[lastname2] = cf.lastname2
                it[address1] = cf.address1
                it[address2] = cf.address2
                it[city] = cf.city
                it[state] = cf.state
                it[zip] = cf.zip
                it[county] = cf.county
                it[email] = cf.email
                it[ophone] = cf.ophone
                it[oxtension] = cf.oxtension
                it[ofax] = cf.ofax
                it[hphone] = cf.hphone
                it[cellphone] = cf.cellphone
                it[carphone] = cf.carphone
                it[estdate] = cf.estdate
                it[speciality] = cf.specialty
                it[ofchrs] = cf.ofchrs
                it[ofcmanager] = cf.ofcmanager
                it[rateconfirm] = cf.rate_confirm
                it[agreement] = cf.agreement
                it[agreement_perm] = cf.agreement_perm
                it[pktsent] = cf.pktsent
                it[refdby] = cf.refdby
                it[preferences] = cf.preferences
                it[dislikes] = cf.dislikes
                it[temphyg] = cf.temphyg
                it[daps_dollar] = cf.daps_dollar
                it[daps_dollar_two] = cf.daps_dollar_two
                it[needs] = cf.needs
                it[startdate] = cf.start_date
                it[endate] = cf.end_date
                it[days] = cf.days
                it[permconf] = cf.permconf
                it[tempconf] = cf.tempconf
                it[mlplcmnt] = cf.mlplcmnt
                it[lofaplcmnt] = cf.lofaplcmnt
                it[patnttime] = cf.patnttime
                it[warndate1] = cf.warndate1
                it[warndate2] = cf.warndate2
                it[warndate3] = cf.warndate3
                it[cnotes] = cf.cnotes
                it[multioffice] = cf.multioffice
                it[payperiods] = cf.payperiods
                it[yeslist] = cf.yeslist
                it[filler] = cf.filler
                it[filler2] = cf.filler2
            }
        }
    }

    /**
     * Delete
     */
    fun deleteClientFile(cf: ClientFile) {
        transaction (db) {
            ClientFileTable.deleteWhere { ClientFileTable.client_num.eq(cf.client_num) }
        }
    }
}






