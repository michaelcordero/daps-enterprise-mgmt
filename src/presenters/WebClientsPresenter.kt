package presenters

import cache.InMemoryCache

class WebClientsPresenter(val cache: InMemoryCache) : AbstractPresenter(cache.dq)
