package com.fachrudin.base.presentation.mainpage

import com.fachrudin.base.network.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Created by achmad.fachrudin on 18-Mar-19
 */
class MainPageViewModelTest {
    @Mock
    lateinit var viewModel: MainPageViewModel

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
        val respone = runBlocking {
            service.getNewsListAsync().await()
        }
        val idList = respone.body()

        Assert.assertEquals(500, idList?.size)
//        viewModel.getIdNewsListFromApi()
    }
}