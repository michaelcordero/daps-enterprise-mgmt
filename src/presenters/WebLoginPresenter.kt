package presenters

import cache.DataCache
import security.DAPSJWT

data class WebLoginPresenter(val cache: DataCache, val dapsjwt: DAPSJWT) : AbstractPresenter()
