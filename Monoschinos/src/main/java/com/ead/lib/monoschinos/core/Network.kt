package com.ead.lib.monoschinos.core

import com.ead.lib.monoschinos.core.system.extensions.suspend
import com.ead.lib.monoschinos.models.Home
import com.ead.lib.monoschinos.models.detail.AnimeDetail
import com.ead.lib.monoschinos.models.detail.Episode
import com.ead.lib.monoschinos.models.directory.Anime
import com.ead.lib.monoschinos.models.home.HomeAnime
import com.ead.lib.monoschinos.models.home.HomeChapter
import com.ead.lib.monoschinos.models.player.Player
import org.jsoup.Jsoup

object Network {

    suspend fun home() : Home? {

        val homePage = Jsoup
            .connect(Properties.HOME_PAGE)
            .get()
            .suspend() ?: return null

        return Home(
            lastChapters = homePage.select("li.col.mb-3:not(.ficha_efecto)").map { element ->
                HomeChapter(
                    title = element.select("article h2").text(),
                    number = element.select("article a div span.episode").text().toInt(),
                    image = element.select("article a div img").attr("data-src"),
                    url = element.select("article a").attr("href")
                )
            },
            recentSeries = homePage.select("li.col.mb-3.ficha_efecto").map { element ->
                HomeAnime(
                    title = element.select("article a h3").text(),
                    type = element.select("article a div div span").text(),
                    image = element.select("article a div img").attr("data-src"),
                    url = element.select("article a").attr("href")
                )
            }
        )
    }

    suspend fun animeDetail(seo : String) : AnimeDetail? {

        val detailPage = Jsoup
            .connect(Properties.HOME_PAGE + Properties.ANIME_QUERY + seo)
            .get()
            .suspend() ?: return null

        val title = detailPage
            .select("div.bg-transparent.p-3.h-100.scroll-x-md dl > :nth-child(6)").text()

        return AnimeDetail(
            title = title,
            alternativeTitle = detailPage
                .select("div.bg-transparent.p-3.h-100.scroll-x-md dl > :nth-child(8)").text().ifEmpty { null },
            status = detailPage.select("div.ms-2:nth-of-type(1) > :nth-child(2)").text(),
            coverImage = detailPage.select("div.d-sm-none.tarjeta.position-relative.p-0 div.col-12 img").attr("data-src"),
            profileImage = detailPage.select("div.mt-5.mb-2.d-flex.d-none.d-sm-flex.gap-3 div img").attr("data-src"),
            release = detailPage.select("div.bg-transparent.p-3.h-100.scroll-x-md dl > :nth-child(4)").text(),
            synopsis = detailPage.select("div.mb-3 p").text(),
            genres = detailPage.select("span.badge.bg-secondary").map { element -> element.text() },
            episodes = detailPage.select("li.col.mb-3").map { element ->
                Episode(
                    title = title,
                    number = element.select("article a h2").text().split(" ").last().toInt(),
                    image = element.select("article a div.position-relative.overflow-hidden img").attr("data-src"),
                    url = element.select("article a").attr("href")
                )
            },
            url = detailPage.select("article a").attr("href")
        )
    }

    suspend fun pageQuery(query: Int) : List<Anime> {

        val queryPage = Jsoup
            .connect(Properties.HOME_PAGE + Properties.QUERY_PAGE + query)
            .get()
            .suspend() ?: return emptyList()

        return queryPage.select("li.col.mb-3.ficha_efecto").map { element ->
            val detailType = element.select("a span").text().split("·")
            val detailYear = element.select("a span").text().split("·")

            Anime(
                title = element.select("a h3").text(),
                type = detailType[0].trim(),
                year = detailYear[1].trim().toInt(),
                image = element.select("a div img").attr("data-src"),
                url = element.select("a").attr("href")
            )
        }
    }

    suspend fun searchQuery(query: String) : List<Anime> {

        val queryPage = Jsoup
            .connect(Properties.HOME_PAGE + Properties.QUERY_SEARCH + query)
            .get()
            .suspend() ?: return emptyList()

        return queryPage.select("li.col.mb-3.ficha_efecto").map { element ->
            Anime(
                title = element.select("a h3").text(),
                type = element.select("a div div span").text(),
                year = element.select("a span.text-muted").text().toInt(),
                image = element.select("a div img").attr("data-src"),
                url = element.select("a").attr("href")
            )
        }
    }

    suspend fun player(seo : String) : Player? {

        val playerPage = Jsoup
            .connect(Properties.HOME_PAGE + Properties.PLAY_PAGE + seo)
            .get()
            .suspend() ?: return null

        return Player(
            options = playerPage.select("button.play-video").map { element ->
                (element.attr("data-player"))
            },
            downloads = playerPage.select("a.btn.btn-warning").map { element ->
                element.attr("href")
            }
        )
    }
}