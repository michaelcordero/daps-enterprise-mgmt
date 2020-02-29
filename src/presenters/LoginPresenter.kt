package presenters

import database.facades.DataService

class LoginPresenter(dao: DataService) : AbstractPresenter(dao)
