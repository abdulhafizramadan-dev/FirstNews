package com.firstnews.app.presentation.recommendation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.firstnews.app.domain.model.News
import com.firstnews.app.domain.model.Resource
import com.firstnews.app.domain.usecase.NewsUseCase
import com.firstnews.app.presentation.home.HomeViewModel
import com.firstnews.app.util.DataDummy
import com.firstnews.app.util.MainDispatcherRule
import com.firstnews.app.util.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RecommendationViewModelTest {

    @Mock
    private lateinit var newsUseCase: NewsUseCase
    private lateinit var viewModel: RecommendationViewModel

    private val dummyNews = DataDummy.generateDummyNews()
    private val dummyApiKey = "apiKey"

    private val notValidApiKey = ""
    private val messageApiKeyNotValid = "Api Key not valid!"

    @get:Rule
    val executorRule = InstantTaskExecutorRule()
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        viewModel = RecommendationViewModel(newsUseCase)
    }

    @Test
    fun getHeadlineFailed_ApiKeyNotValid() {
        val expectedNews = MutableLiveData<Resource<List<News>>>()
        expectedNews.value = Resource.Error(Throwable(messageApiKeyNotValid))
        Mockito.lenient(). `when`(newsUseCase.getHeadlineNews(country = "us", page = 1, apiKey = notValidApiKey)).thenReturn(expectedNews)
        viewModel.getHeadlineNews()
        val actualNews = viewModel.getHeadlineNews(country = "us", page = 1, apiKey = notValidApiKey).getOrAwaitValue()
        assertNotNull(actualNews)
        assertTrue(actualNews is Resource.Error)
        assertEquals((actualNews as Resource.Error).error.message, messageApiKeyNotValid)
    }

    @Test
    fun getHeadlineSuccess() {
        val expectedNews = MutableLiveData<Resource<List<News>>>()
        expectedNews.value = Resource.Success(dummyNews)
        Mockito.lenient(). `when`(newsUseCase.getHeadlineNews(country = "us", page = 1, apiKey = dummyApiKey)).thenReturn(expectedNews)
        viewModel.getHeadlineNews()
        val actualNews = viewModel.getHeadlineNews(country = "us", page = 1, apiKey = dummyApiKey).getOrAwaitValue()
        assertNotNull(actualNews)
        assertTrue(actualNews is Resource.Success)
        assertEquals((actualNews as Resource.Success).data, dummyNews)
    }

}