package com.singaludra.moviep.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.map
import com.singaludra.moviep.TestCoroutineRule
import com.singaludra.moviep.data.source.remote.IRemoteDataSource
import com.singaludra.moviep.data.source.remote.network.ApiResponse
import com.singaludra.moviep.data.source.remote.paging.MoviesPagingSource
import com.singaludra.moviep.data.source.remote.response.DetailMovieResponse
import com.singaludra.moviep.data.source.remote.response.MovieResponse
import com.singaludra.moviep.data.source.remote.response.ReviewResponse
import com.singaludra.moviep.data.source.remote.response.VideosResponse
import com.singaludra.moviep.data.source.remote.response.mapToDomain
import com.singaludra.moviep.domain.model.Movie
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MoviesRepositoryTest: TestCase() {

    // Add a rule to execute tasks synchronously in tests
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    // Mock the IRemoteDataSource
    @Mock
    lateinit var remoteDataSource: IRemoteDataSource

    // Create an instance of the MoviesRepository
    private lateinit var moviesRepository: MoviesRepository

    @Before
    public override fun setUp() {
        MockitoAnnotations.openMocks(this)
        moviesRepository = MoviesRepository(remoteDataSource)
    }

    @Test
    fun `test getMovies should return success resource when remoteDataSource returns success response`() = runBlocking {
        // Given
        val expectedPagingData = PagingData.from(moviesList)

        // When
        `when`(remoteDataSource.getMovies()).thenReturn(flowOf(expectedPagingData))
        val result = moviesRepository.getMovies().toList()

        // Then
        assertTrue(result[0]!= null)
    }

    @Test
    fun `test getDetailMovie should return success resource when remoteDataSource returns success response`() = runBlockingTest {
        // Given
        val movieId = 1
        val expectedMovie = movieResponse.mapToDomain()
        val apiResponse = ApiResponse.Success(movieResponse)

        `when`(remoteDataSource.getDetailMovie(movieId)).thenReturn(flowOf(apiResponse))

        // When
        val result = moviesRepository.getDetailMovie(movieId).toList()

        // Then
        assertTrue(result[1].data != null)
        assertEquals(Resource.Success(expectedMovie).data?.title, result[1].data?.title)
        assertEquals(2, result.size)
    }

    @Test
    fun `test getMovieReviews should return success resource when remoteDataSource returns success response`() = runBlockingTest {
        // Given
        val movieId = 1
        val expectedReviews = reviewsList.map { it.mapToDomain() }
        val expectedData = Resource.Success(expectedReviews)
        val apiResponse = ApiResponse.Success(ReviewResponse(results = reviewsList))

        `when`(remoteDataSource.getMovieReviews(movieId)).thenReturn(flowOf(apiResponse))

        // When
        val result = moviesRepository.getMovieReviews(movieId).toList()

        // Then
        assertEquals(expectedData.data, result[1].data)
        assertEquals(2, result.size)
    }

    @Test
    fun `test getMovieVideos should return success resource when remoteDataSource returns success response`() = runBlockingTest {
        // Given
        val movieId = 1
        val expectedVideos = videosList.map { it.mapToDomain() }
        val apiResponse = ApiResponse.Success(VideosResponse(id = movieId,results = videosList))

        `when`(remoteDataSource.getMovieVideos(movieId)).thenReturn(flowOf(apiResponse))

        // When
        val result = moviesRepository.getMovieVideos(movieId).toList()

        // Then
        assertEquals(Resource.Success(expectedVideos).data, result[1].data)
        assertEquals(2, result.size)
    }

    companion object{
         val moviesList = listOf(
            MovieResponse(
                id = 1, title = "Movie 1", adult = true, backdropPath = "assd", originalTitle = "Movie 1", originalLanguage = "asdasda", voteCount = 2, voteAverage = 20.0, genreIds = listOf(1), overview = "asdad", popularity = 20.0, posterPath = "asdasd", video = false, releaseDate = "asd"
            )
        )
        val movieResponse =
            DetailMovieResponse(
                id = 1,
                title = "Movie 1",
                adult = true,
                backdropPath = "assd",
                originalTitle = "Movie 1",
                originalLanguage = "asdasda",
                voteCount = 2,
                voteAverage = 20.0,
                 overview = "asdad",
                popularity = 20.0,
                posterPath = "asdasd",
                video = false,
                releaseDate = "asd",
                budget = 2,
                imdbId = "2",
                genres = listOf(DetailMovieResponse.Genre(id = 1, name = "ass")),
                revenue = 20,
                runtime = 20,
                status = "a",
                tagline = "asd",
                productionCompanies = listOf(
                    DetailMovieResponse.ProductionCompany(
                        id = 2, logoPath = "asdad", name = "asdasda", originCountry = "asdasd"
                    )
                )

            )
        val reviewsList = listOf(
            ReviewResponse.Result(
                author = "John",
                content = "Great movie!",
                id = "asd",
                updatedAt = "asdad",
                createdAt = "asdad",
                url = "asd"
            )
        )
        val videosList = listOf(
            VideosResponse.Result(id = "123", name = "Trailer 1", key = "asdsda", official = true, publishedAt = "asdasd", site = "asadads", size = 22, type = "asdad"),
        )
    }
}