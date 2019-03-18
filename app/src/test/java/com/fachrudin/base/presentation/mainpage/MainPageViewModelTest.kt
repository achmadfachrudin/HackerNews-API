package com.fachrudin.base.presentation.mainpage

import com.fachrudin.base.network.RetrofitFactory
import com.fachrudin.base.network.SampleService
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

/**
 * Created by achmad.fachrudin on 18-Mar-19
 */
class MainPageViewModelTest {
    @Mock
    lateinit var viewModel: MainPageViewModel

    @Mock
    private lateinit var sampleService: SampleService

    private val service = RetrofitFactory.makeRetrofitService()

    @Before
    fun setUp() {
        Dispatchers.resetMain()
        MockitoAnnotations.initMocks(this)
        viewModel = MainPageViewModel()
    }

    @Test
    fun getIdNewsListFromApi() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        lateinit var respone : Deferred<Response<List<String>>>

//        Mockito.`when`(service.getNewsListAsync())
//            .thenReturn(respone)
        delay()
//        var idList = respone.body()!!

        viewModel.getIdNewsListFromApi()
    }

    private fun delay() {
        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}