package com.fachrudin.base.network

import com.fachrudin.base.entity.NewsItem
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author achmad.fachrudin
 * @date 3-Mar-19
 */
interface SampleService {
    @GET("topstories.json?print=pretty")
    fun getNewsListAsync(): Deferred<Response<List<String>>>

    @GET("item/{id}.json?print=pretty")
    fun getNewsItemAsync(@Path("id") id: String): Deferred<Response<NewsItem>>
}