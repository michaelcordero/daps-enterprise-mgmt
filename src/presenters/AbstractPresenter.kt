package presenters

import application.dq
import database.queries.DataQuery
import io.ktor.util.KtorExperimentalAPI
import model.User
import security.DAPSSecurity

abstract class AbstractPresenter(dao: DataQuery) {
    fun user(email: String): User? {
        return dq.userByEmail(email)
    }

    @KtorExperimentalAPI
    fun hashPassword(password: String): String {
        return DAPSSecurity.hash(password)
    }

}
