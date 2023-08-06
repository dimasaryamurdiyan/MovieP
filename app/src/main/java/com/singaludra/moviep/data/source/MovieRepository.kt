package com.singaludra.moviep.data.source

import androidx.paging.PagingData
import androidx.paging.map
import com.singaludra.moviep.data.source.remote.IRemoteDataSource
import com.singaludra.moviep.data.source.remote.network.ApiResponse
import com.singaludra.moviep.data.source.remote.response.DetailMovieResponse
import com.singaludra.moviep.data.source.remote.response.ReviewResponse
import com.singaludra.moviep.data.source.remote.response.VideosResponse
import com.singaludra.moviep.data.source.remote.response.mapToDomain
import com.singaludra.moviep.domain.model.Movie
import com.singaludra.moviep.domain.model.Review
import com.singaludra.moviep.domain.model.Video
import com.singaludra.moviep.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val remoteDataSource: IRemoteDataSource,
) : IMovieRepository {

    override fun getMovies(): Flow<PagingData<Movie>> {
        return remoteDataSource.getMovies()
            .map { pagingData ->
                pagingData.map { remoteMovie ->
                    remoteMovie.mapToDomain()
                }
            }
    }

    override fun getDetailMovie(id: Int): Flow<Resource<Movie>> {
        return object : NetworkBoundResource<DetailMovieResponse, Movie>(){
            override suspend fun createCall(): Flow<ApiResponse<DetailMovieResponse>> {
                return remoteDataSource.getDetailMovie(id)
            }

            override fun mapApiResponseToDomain(data: DetailMovieResponse): Movie {
                return data.mapToDomain()
            }

        }.asFlow()
    }

    override fun getMovieReviews(id: Int): Flow<Resource<List<Review>>> {
        return object :NetworkBoundResource<ReviewResponse, List<Review>>(){
            override suspend fun createCall(): Flow<ApiResponse<ReviewResponse>> {
                return remoteDataSource.getMovieReviews(id)
            }

            override fun mapApiResponseToDomain(data: ReviewResponse):  List<Review> {
                return data.results.map {
                    it.mapToDomain()
                }
            }

        }.asFlow()
    }

    override fun getMovieVideos(id: Int): Flow<Resource<List<Video>>> {
        return object : NetworkBoundResource<VideosResponse, List<Video>>(){
            override suspend fun createCall(): Flow<ApiResponse<VideosResponse>> {
                return remoteDataSource.getMovieVideos(id)
            }

            override fun mapApiResponseToDomain(data: VideosResponse): List<Video> {
                return data.results.map {
                    it.mapToDomain()
                }
            }

        }.asFlow()
    }
}