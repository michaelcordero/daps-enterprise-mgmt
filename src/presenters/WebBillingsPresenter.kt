package presenters

import cache.InMemoryCache

class WebBillingsPresenter(val cache: InMemoryCache) : AbstractPresenter(cache.dq)
