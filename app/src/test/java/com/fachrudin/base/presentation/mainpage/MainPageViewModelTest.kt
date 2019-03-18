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

        // expected
        val expectedRespone = runBlocking {
            service.getNewsListAsync().await()
        }
        val expectedResult = expectedRespone.body()

        // actual
        viewModel.getIdNewsListFromApi()
        delay()

        Assert.assertEquals(expectedResult?.size, viewModel.idList?.size)
    }

    private fun delay() {
        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}