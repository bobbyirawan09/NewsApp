package bobby.irawan.newsapp.domain.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import bobby.irawan.newsapp.TestCoroutineRule
import bobby.irawan.newsapp.data.sources.model.SourcesResponse
import bobby.irawan.newsapp.data.sources.service.SourceService
import bobby.irawan.newsapp.utils.Constants
import bobby.irawan.newsapp.utils.Constants.Response.Success
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by bobbyirawan09 on 07/07/20.
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SourceRepositoryImplTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var sourceService: SourceService

    private lateinit var sourceRepositoryImpl: SourceRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        sourceRepositoryImpl = SourceRepositoryImpl(sourceService)
    }

    @Test
    fun givenSuccessResponse_whenGetData_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            //Given
            val mockSourcesResponse = mock<SourcesResponse>()
            val mockResponse = Success(mockSourcesResponse)
            whenever(sourceService.getSources(anyString())).thenReturn(
                mockResponse
            )

            //When
            val result = sourceRepositoryImpl.getSource(anyString())

            //Then
            verify(sourceService).getSources(anyString())
            assertTrue(result is Success<*>)
            assertTrue(((result as Success<*>).data is List<*>))
        }
    }

    @Test
    fun givenErrorResponse_whenGetData_shouldReturnErrorWithMessage() {
        testCoroutineRule.runBlockingTest {
            //Given
            val expectedResult = "Error message"
            whenever(sourceService.getSources(anyString())).thenReturn(
                Constants.Response.Error(
                    expectedResult
                )
            )

            //When
            val result = sourceRepositoryImpl.getSource(anyString())

            //Then
            verify(sourceService).getSources(anyString())
            assertTrue(result is Constants.Response.Error)
            val resultErrorMessage = (result as Constants.Response.Error).errorMessage
            assert(resultErrorMessage.equals(expectedResult))
        }
    }
}