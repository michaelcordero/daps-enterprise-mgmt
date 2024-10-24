package presenters

import application.Theme
import application.cache
import model.User
import security.DAPSSecurity

abstract class AbstractPresenter {
    val theme: Theme = application.theme
    val host: String = application.host
    val port: String = application.port
    fun user(email: String): User? {
        return cache.users_map().filter { map -> map.value.email == email }.values.firstOrNull()
    }

    fun hashPassword(password: String): String {
        return DAPSSecurity.hash(password)
    }

}
