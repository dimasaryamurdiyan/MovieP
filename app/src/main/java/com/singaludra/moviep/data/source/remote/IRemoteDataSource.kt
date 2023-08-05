package com.singaludra.moviep.data.source.remote

import androidx.paging.PagingData
import com.singaludra.moviep.data.source.remote.network.ApiResponse
import com.singaludra.moviep.data.source.remote.response.DetailMovieResponse
import com.singaludra.moviep.data.source.remote.response.MovieResponse
import com.singaludra.moviep.data.source.remote.response.ReviewResponse
import kotlinx.coroutines.flow.Flow

interface IRemoteDataSource {
    fun getMovies(): Flow<PagingData<MovieResponse>>

    fun getDetailMovie(id: Int): Flow<ApiResponse<DetailMovieResponse>>

    fun getMovieReviews(id: Int): Flow<ApiResponse<ReviewResponse>>
}