package com.ead.lib.monoschinos.core.system.extensions

import com.ead.lib.monoschinos.core.Properties
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

fun Elements.getSrcAttr(): String {
    val result = attr("src")

    if (result == Properties.CAP_BLANK_MC2 || result == Properties.CAP_BLANK_ANIME_MC2  || result.isEmpty()) {
        return "data-src"
    }
    return "src"
}

fun Element.getSrcAttr(selectorQuery : String) : String {
    val result = select(selectorQuery).attr("src")

    if (result == Properties.CAP_BLANK_MC2 || result == Properties.CAP_BLANK_ANIME_MC2  || result.isEmpty()) {
        return "data-src"
    }
    return "src"
}