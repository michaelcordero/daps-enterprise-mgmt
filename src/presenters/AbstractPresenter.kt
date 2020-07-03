package presenters

import application.Theme
import application.cache
import io.ktor.util.KtorExperimentalAPI
import model.User
import security.DAPSSecurity

abstract class AbstractPresenter {
    val theme: Theme = application.theme
    fun user(email: String): User? {
        return cache.allUsers().find { user -> user.email == email }
    }

    @KtorExperimentalAPI
    fun hashPassword(password: String): String {
        return DAPSSecurity.hash(password)
    }

}
