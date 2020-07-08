package bobby.irawan.newsapp.presentation.source.viewmodel

import android.content.Intent
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import bobby.irawan.newsapp.TestCoroutineRule
import bobby.irawan.newsapp.domain.source.SourceRepository
import bobby.irawan.newsapp.presentation.model.SourceModelView
import bobby.irawan.newsapp.utils.Constants.EXTRA_CATEGORY_NAME
import bobby.irawan.newsapp.utils.Constants.Response
import bobby.irawan.newsapp.utils.Constants.Response.Error
import bobby.irawan.newsapp.utils.Constants.Response.Success
import com.nhaarman.mockitokotlin2.*
import junit.framework.Assert.assertEquals
import junit.framework.Assert.fail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.powermock.reflect.Whitebox
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


/**
 * Created by bobbyirawan09 on 07/07/20.
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SourceViewModelTest {

    @get:Rule
    var exceptionRule = ExpectedException.none()

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var sourceRepository: SourceRepository

    private lateinit var sourceViewModel: SourceViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        sourceViewModel = SourceViewModel(sourceRepository)
    }

    @Test
    fun givenInternetConnection_whenGetData_shouldDoTheSearch() {
        testCoroutineRule.runBlockingTest {
            //Given
            val mockResponse = mock<Response>()
            val isInternetConnected = true
            whenever(sourceRepository.getSource(anyString())).thenReturn(Success(listOf(mockResponse)))

            //When
            sourceViewModel.getSourceData(isInternetConnected)

            //Then
            verify(sourceRepository, times(1)).getSource(anyString())
            verifyNoMoreInteractions(sourceRepository)
        }
    }

    @Test
    fun givenNoInternetConnection_whenGetData_shouldDoNothing() {
        //Given
        val isInternetConnected = false

        //When
        sourceViewModel.getSourceData(isInternetConnected)

        //Then
        assertEquals(
            "No Internet Connection Available",
            sourceViewModel.messageLiveData.getOrAwaitValue()
        )
    }

    @Test
    fun givenServerResponse200_whenGetData_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            //Given
            val isInternetConnected = true
            val mockResponse = mock<SourceModelView>()
            val successReponse = Success(listOf(mockResponse))
            whenever(sourceRepository.getSource(anyString())).thenReturn(successReponse)

            //When
            sourceViewModel.getSourceData(isInternetConnected)

            //Then
            verify(sourceRepository, times(1)).getSource(anyString())
            verifyNoMoreInteractions(sourceRepository)
            assertEquals(listOf(mockResponse), sourceViewModel.sourcesLiveData.getOrAwaitValue())
        }
    }

    @Test
    fun givenServerResponseError_whenGetData_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            val isInternetConnected = true
            val responseMessage = "Can't get data"
            val errorResponse = Error(responseMessage)
            whenever(sourceRepository.getSource(anyString())).thenReturn(errorResponse)

            //When
            sourceViewModel.getSourceData(isInternetConnected)

            //Then
            verify(sourceRepository, times(1)).getSource(anyString())
            verifyNoMoreInteractions(sourceRepository)
            assertEquals(responseMessage, sourceViewModel.messageLiveData.getOrAwaitValue())
        }
    }

    @Test
    fun givenEmptyKeyword_whenSearchSource_shouldReturnEmpty() {
        //Given
        val keyword = ""
        val result = listOf<SourceModelView>()
        Whitebox.setInternalState(sourceViewModel, "sources", result)

        //When
        sourceViewModel.onFilterSourceData(keyword)

        //Then
        assertEquals(
            result,
            sourceViewModel.filterSourcesLiveData.getOrAwaitValue()
        )
    }

    @Test
    fun givenNonEmptyKeyword_whenSearchSource_shouldReturnRightResult() {
        //Given
        val keyword = "Test keyword"
        val filterResult = listOf(SourceModelView(name = keyword), SourceModelView(name = keyword))
        Whitebox.setInternalState(sourceViewModel, "sources", filterResult)

        //When
        sourceViewModel.onFilterSourceData(keyword)

        //Then
        assertEquals(filterResult, sourceViewModel.filterSourcesLiveData.getOrAwaitValue())
        assertEquals(filterResult.first().name, keyword)
        assert(filterResult.size == 2)
    }

    @Test
    fun givenNullIntent_whenGetDataFromIntent_shouldDoNothing() {
        //Given
        val intent = null
        exceptionRule.expect(TimeoutException::class.java)
        exceptionRule.expectMessage("LiveData value was never set.")

        //When
        sourceViewModel.getBundle(intent)

        //Then
        fail(sourceViewModel.titleLiveData.getOrAwaitValue())
    }

    @Test
    fun givenNonNullIntent_whenGetDataFromIntent_shouldDoSearchSource() {
//Given
        val intent = mock<Intent>()
        val expectedTitle = "Business"
        whenever(intent.getStringExtra(EXTRA_CATEGORY_NAME)).thenReturn(expectedTitle)

        //When
        sourceViewModel.getBundle(intent)

        //Then
        assertEquals(expectedTitle, sourceViewModel.titleLiveData.getOrAwaitValue())
    }

    fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(o: T?) {
                data = o
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }

        this.observeForever(observer)

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }

        @Suppress("UNCHECKED_CAST")
        return data as T
    }

}