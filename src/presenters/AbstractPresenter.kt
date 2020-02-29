package presenters

import application.dao
import database.facades.DataService
import io.ktor.util.KtorExperimentalAPI
import model.User
import security.DAPSSecurity

abstract class AbstractPresenter(dao: DataService) {
    fun user(email: String): User? {
        return dao.userByEmail(email)
    }

    @KtorExperimentalAPI
    fun hashPassword(password: String): String {
        return DAPSSecurity.hash(password)
    }

}
