package com.singaludra.moviep.domain.repository

import androidx.paging.PagingData
import com.singaludra.moviep.data.source.Resource
import com.singaludra.moviep.domain.model.Movie
import com.singaludra.moviep.domain.model.Review
import com.singaludra.moviep.domain.model.Video
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getMovies(): Flow<PagingData<Movie>>

    fun getDetailMovie(id: Int): Flow<Resource<Movie>>

    fun getMovieReviews(id: Int): Flow<Resource<List<Review>>>
    fun getMovieVideos(id: Int): Flow<Resource<List<Video>>>
}