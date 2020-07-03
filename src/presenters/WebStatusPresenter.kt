package presenters

import io.ktor.features.StatusPages

class WebStatusPresenter(val configuration: StatusPages.Configuration) : AbstractPresenter()
