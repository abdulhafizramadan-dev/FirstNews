package com.firstnews.app.util

import com.firstnews.app.domain.model.News

object DataDummy {

    fun generateDummyNews(): List<News> =
        (1..10).map {
            News(
                id = 0,
                title = "The Trial Over Bitcoin’s True Creator Is in Session",
                source = "Wired",
                author = "Joel Khalili"
            )
        }

}