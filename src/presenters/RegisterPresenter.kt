package presenters

import application.cache
import model.User
import security.DAPSRole
import security.DAPSSession
import kotlin.random.Random

open class RegisterPresenter : AbstractPresenter() {

    fun createUser(
        first_name: String,
        last_name: String,
        email: String,
        password: String,
        session: DAPSSession,
        registration_key: String
    ) {
        val error: String = validate(first_name, last_name, email, password,registration_key)
        if (error.isNotEmpty()) {
            throw Exception(error)
        } else {
            val new_password: String = hashPassword(password)
            val new_id: Long = Random.nextLong(from = 1000000, until = 1999999)
            val role: DAPSRole = DAPSRole.values().first { role -> role.key == registration_key }
            val newUser = User(new_id, email, first_name, last_name, new_password, role)
            cache.add(newUser, session)
        }
    }

    fun validate(first_name: String, last_name: String, email: String, password: String, registration_key: String): String {
        val message: StringBuilder = StringBuilder()
        if (!validFirstName(first_name)) {
            message.append("invalid first name\n")
        }
        if (!validLastName(last_name)) {
            message.append("invalid last name")
        }
        if (!validPassword(password)) {
            message.append(
                "password must be 6-20 characters in length and" +
                        " must include at least one special character: @#$%\n"
            )
        }
        if (!validEmail(email)) {
            message.append("email must be of the form: username@domain.com\n")
        }
        if (!validRegistrationKey(registration_key)) {
            message.append("Invalid Registration Key")
        }
        return message.toString()
    }

    companion object Validator {
        // https://www.mkyong.com/regular-expressions/10-java-regular-expression-examples-you-should-know/
        val user_name_pattern: Regex = "[a-zA-Z0-9_.]+".toRegex()
        val password_pattern: Regex = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%]).{6,20})".toRegex()
        val email_pattern: Regex = ("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*+(\\+[_A-Za-z0-9-]+)*@[A-Za-z0-9.-]" +
                "+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})\$").toRegex()
        val registration_key_pattern: Regex = "^[a-zA-Z0-9]{4}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{4}\$".toRegex()

        fun validEmail(email: String): Boolean {
            return email.matches(email_pattern)
        }

        fun validPassword(password: String): Boolean {
            return password.matches(password_pattern)
        }

        fun validFirstName(first_name: String): Boolean {
            return first_name.matches(user_name_pattern)
        }

        fun validLastName(last_name: String): Boolean {
            return last_name.matches(user_name_pattern)
        }

        fun validRegistrationKey(registration_key: String): Boolean {
            return registration_key.matches(registration_key_pattern) &&
                    DAPSRole.values().toList().stream().anyMatch { DAPSKey -> DAPSKey.key == registration_key }
        }

        fun password_validation_message(): String {
            return "Password must be between 6-20 characters.At least one number.At least one lower case letter." +
                    "At least upper case letter.At least one of the following symbols: @#$%"
        }
    }

}
