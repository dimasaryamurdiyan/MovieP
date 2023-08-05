package com.singaludra.moviep.domain.repository

import androidx.paging.PagingData
import com.singaludra.moviep.data.source.Resource
import com.singaludra.moviep.data.source.remote.network.ApiResponse
import com.singaludra.moviep.data.source.remote.response.DetailMovieResponse
import com.singaludra.moviep.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getMovies(): Flow<PagingData<Movie>>

    fun getDetailMovie(id: Int): Flow<Resource<Movie>>
}