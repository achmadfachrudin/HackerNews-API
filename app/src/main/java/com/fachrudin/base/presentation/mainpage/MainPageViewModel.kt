package com.fachrudin.base.presentation.mainpage

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fachrudin.base.core.BaseViewModel
import com.fachrudin.base.entity.NewsItem
import com.fachrudin.base.network.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @author achmad.fachrudin
 * @date 10-Mar-2019
 */
class MainPageViewModel : BaseViewModel() {

    private val service = RetrofitFactory.makeRetrofitService()

    var bTextError = ObservableField<String>("Test")
    val bShowErrorView = ObservableField<Boolean>(false)
    val bShowLoadingView = ObservableField<Boolean>(true)

    var idList = listOf<String>()
    private val list = mutableListOf<NewsItem>()
    private var newsList: MutableLiveData<List<NewsItem>>? = null

    private var error: MutableLiveData<Exception>? = null

    fun getError(): LiveData<Exception> {
        if (error == null)
            error = MutableLiveData()
        return error as LiveData<Exception>
    }

    fun getNewsList(): LiveData<List<NewsItem>> {
        if (newsList == null)
            newsList = MutableLiveData()
        return newsList as LiveData<List<NewsItem>>
    }

    fun getIdNewsListFromApi() {
        bShowLoadingView.set(true)
        GlobalScope.launch(Dispatchers.Main)
        {
            val request = service.getNewsListAsync()
            try {
                val response = request.await()
                // Do something with the response.body()
                idList = response.body()!!

                list.clear()
                idList.subList(0, 30).forEach { id ->
                    getNewsItemFromApi(id)
                }
            } catch (e: Exception) {
                error?.value = e
                bShowLoadingView.set(false)
            }
        }
    }

    private fun getNewsItemFromApi(idNews: String) {
        GlobalScope.launch(Dispatchers.Main)
        {
            val request = service.getNewsItemAsync(idNews)
            try {
                val response = request.await()
                // Do something with the response.body()
                list.add(response.body()!!)
                list.sortBy { it.time }
                newsList?.value = list
            } catch (e: Exception) {
                error?.value = e
                bShowLoadingView.set(false)
            }
        }
    }
}