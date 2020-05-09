package presenters

import database.queries.DataQuery
import security.DAPSJWT

data class WebLoginPresenter(val dao: DataQuery, val dapsjwt: DAPSJWT) : AbstractPresenter(dao)
