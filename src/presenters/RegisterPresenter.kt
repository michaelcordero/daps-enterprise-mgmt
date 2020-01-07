package com.daps.ent.presenters

import com.daps.ent.dao
import com.daps.ent.database.DataService
import com.daps.ent.model.User
import io.ktor.util.KtorExperimentalAPI
import java.io.IOException

@KtorExperimentalAPI
class RegisterPresenter (dao: DataService) : AbstractPresenter(dao) {

    @Throws(IOException::class)
    suspend fun createUser(first_name: String, last_name: String, email: String, password: String): User? {
        val error: String = validate(first_name, last_name, email, password)
        if ( error.isNotEmpty() ){
            throw Exception(error)
        } else {
            val new_password: String = hashPassword(password)
            val newUser = User(email, first_name, last_name, new_password)
//            val co: CoroutineScope = CoroutineScope()
//            co.async {  }
            dao.addUser(newUser)
            val created: User = dao.user(email, new_password)!!
            return created
        }
    }

    fun validate(first_name: String, last_name: String, email: String, password: String ): String {
        val message: StringBuilder = StringBuilder()
        if (!validFirstName(first_name)) {
            message.append("invalid first name\n")
        }
        if (!validLastName(last_name)){
            message.append("invalid last name")
        }
        if (!validPassword(password)){
            message.append("password must be 6-20 characters in length and" +
                    " must include at least one special character: @#$%\n")
        }
        if (!validEmail(email)){
            message.append("email must be of the form: text@domain.com\n")
        }
        return message.toString()
    }

    companion object Validator {
        // https://www.mkyong.com/regular-expressions/10-java-regular-expression-examples-you-should-know/
        val user_name_pattern: Regex = "[a-zA-Z0-9_.]+".toRegex()
        fun validFirstName(first_name: String): Boolean {
            return first_name.matches(user_name_pattern)
        }
        fun validLastName(last_name: String): Boolean {
            return last_name.matches(user_name_pattern)
        }
    }

}
