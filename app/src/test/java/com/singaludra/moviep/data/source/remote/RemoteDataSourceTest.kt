package com.singaludra.moviep.data.source.remote

import com.google.gson.GsonBuilder
import com.singaludra.moviep.data.source.remote.network.ApiResponse
import com.singaludra.moviep.data.source.remote.network.ApiService
import com.singaludra.moviep.data.source.remote.response.DetailMovieResponse
import com.singaludra.moviep.data.source.remote.response.ReviewResponse
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
class RemoteDataSourceTest {
    private lateinit var mockWebServer: MockWebServer

    lateinit var apiService: ApiService

    private lateinit var okHttpClient: OkHttpClient

    private lateinit var loggingInterceptor: HttpLoggingInterceptor

    private lateinit var remoteDataSource: RemoteDataSource
    @Before
    fun setup(){
        mockWebServer = MockWebServer()

        mockWebServer.start()

        loggingInterceptor = HttpLoggingInterceptor().apply{
            level = HttpLoggingInterceptor.Level.BODY
        }
        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(ApiService::class.java)

        remoteDataSource = RemoteDataSource(apiService)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `test getDetailMovie return success`() = runBlocking {
        // Given
        val movieId = 1
        // Enqueue a mock response from the mock web server
        val mockResponse = MockResponse().setBody(
            """
            {
                "id": $movieId,
                "title": "Movie 1 Detail"
            }
            """
        )
        mockWebServer.enqueue(mockResponse)

        // When
        val result = remoteDataSource.getDetailMovie(movieId).toList().single()

        // Then
        assertEquals("Movie 1 Detail", (result as ApiResponse.Success<DetailMovieResponse>).data.title)
    }

    @Test
    fun `test getDetailMovie with error 500`() = runBlocking {
        // Given
        val movieId = 123
        val errorMessage = "Error occurred"
        // Enqueue a mock error response from the mock web server
        val mockResponse = MockResponse().setResponseCode(500).setBody(errorMessage)
        mockWebServer.enqueue(mockResponse)

        // When
        val result = remoteDataSource.getDetailMovie(movieId).toList().single()

        // Then
        assertTrue(result is ApiResponse.Error)
    }

    @Test
    fun `test getMovieReviews return success`() = runBlocking {
        // Given
        val movieId = 123
        val page = 1
        val results = listOf<ReviewResponse.Result>(
            ReviewResponse.Result(
                author = "asdad",
                content ="asdasd",
                createdAt = "asdad",
                id ="1",
                updatedAt = "asdad",
                url ="www.com",
            )
        )
        // Enqueue a mock response from the mock web server
        val mockResponse = MockResponse().setBody(
            """
            {
                "id": $movieId,
                "page":$page
            }
            """
        )
        mockWebServer.enqueue(mockResponse)

        // When
        val result = remoteDataSource.getMovieReviews(movieId).toList().single()

        // Then
        assertTrue(result is ApiResponse.Success)
    }

    @Test
    fun `test getMovieReviews with error 500`() = runBlocking {
        // Given
        val movieId = 123
        val errorMessage = "Error occurred"
        // Enqueue a mock error response from the mock web server
        val mockResponse = MockResponse().setResponseCode(500).setBody(errorMessage)
        mockWebServer.enqueue(mockResponse)

        // When
        val result = remoteDataSource.getMovieReviews(movieId).toList().single()

        // Then
        assertTrue(result is ApiResponse.Error)
    }

    @Test
    fun `test getMovieVideos return success`() = runBlocking {
        // Given
        val movieId = 123

        // Enqueue a mock response from the mock web server
        val mockResponse = MockResponse().setBody(
            """
            {
                "id": $movieId
            }
            """
        )
        mockWebServer.enqueue(mockResponse)

        // When
        val result = remoteDataSource.getMovieVideos(movieId).toList().last()

        // Then
        assertTrue(result is ApiResponse.Success)
    }

    @Test
    fun `test getMovieVideos with error 500`() = runBlocking {
        // Given
        val movieId = 123
        val errorMessage = "Error occurred"
        // Enqueue a mock error response from the mock web server
        val mockResponse = MockResponse().setResponseCode(500).setBody(errorMessage)
        mockWebServer.enqueue(mockResponse)

        // When
        val result = remoteDataSource.getMovieVideos(movieId).toList().single()

        // Then
        assertTrue(result is ApiResponse.Error)
    }

}