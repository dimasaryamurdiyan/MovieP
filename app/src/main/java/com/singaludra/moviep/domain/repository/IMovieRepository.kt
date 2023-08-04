package com.singaludra.moviep.domain.repository

import androidx.paging.PagingData
import com.singaludra.moviep.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getMovies(): Flow<PagingData<Movie>>
}