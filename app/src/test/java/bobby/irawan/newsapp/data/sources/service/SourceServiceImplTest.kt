package bobby.irawan.newsapp.data.sources.service

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import bobby.irawan.newsapp.TestCoroutineRule
import bobby.irawan.newsapp.data.sources.model.SourcesResponse
import bobby.irawan.newsapp.utils.Constants.Response
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.ResponseBody
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.anyString
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import retrofit2.Response.error
import retrofit2.Response.success

/**
 * Created by bobbyirawan09 on 07/07/20.
 */
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class SourceServiceImplTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var api: SourcesApi

    private lateinit var sourceServiceImpl: SourceServiceImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        sourceServiceImpl = SourceServiceImpl(api)
    }

    @Test
    fun givenSuccessResponse_whenGetData_shouldReturnResponseSuccess() {
        testCoroutineRule.runBlockingTest {
            //Given
            val mockResponseBody = mock<SourcesResponse>()
            val categoryName = "dummy category"
            whenever(api.getSources(anyString(), anyString())).thenReturn(success(mockResponseBody))

            //When
            val result = sourceServiceImpl.getSources(categoryName)

            //Then
            verify(api).getSources(anyString(), anyString())
            verifyNoMoreInteractions(api)
            assertTrue(result is Response.Success<*>)

        }
    }

    @Test
    fun givenErrorResponse_whenGetData_shouldReturnResponseError() {
        testCoroutineRule.runBlockingTest {
            //Given
            val mockResponseBody = mock<ResponseBody>()
            val categoryName = "dummy category"
            val errorResponse = error<SourcesResponse>(400, mockResponseBody)
            whenever(api.getSources(anyString(), anyString())).thenReturn(errorResponse)

            //When
            val result = sourceServiceImpl.getSources(categoryName)

            //Then
            verify(api).getSources(anyString(), anyString())
            verifyNoMoreInteractions(api)
            assertTrue(result is Response.Error)

        }
    }
}