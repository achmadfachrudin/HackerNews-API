package com.fachrudin.base.entity

/**
 * Created by achmad.fachrudin on 04-Mar-19
 */
data class NewsItem(
    val id: Int,
    val title: String,
    val by: String,
    val time: Long
) {
    companion object {
        const val TABLE_NEWS_FAVORITE: String = "TABLE_NEWS_FAVORITE"
        const val NEWS_ID: String = "NEWS_ID"
        const val NEWS_TITLE: String = "NEWS_TITLE"
        const val NEWS_AUTHOR: String = "NEWS_AUTHOR"
        const val NEWS_DATE: String = "NEWS_DATE"
    }
}