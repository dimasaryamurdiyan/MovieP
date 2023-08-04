package com.singaludra.moviep.data.source.remote

import androidx.paging.PagingData
import com.singaludra.moviep.data.source.remote.response.MovieResponse
import kotlinx.coroutines.flow.Flow

interface IRemoteDataSource {
    fun getMovies(): Flow<PagingData<MovieResponse>>
}