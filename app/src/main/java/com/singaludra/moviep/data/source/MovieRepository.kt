package com.singaludra.moviep.data.source

import androidx.paging.PagingData
import androidx.paging.map
import com.singaludra.moviep.data.source.remote.RemoteDataSource
import com.singaludra.moviep.data.source.remote.response.mapToDomain
import com.singaludra.moviep.domain.model.Movie
import com.singaludra.moviep.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

internal class MoviesRepository(
    private val remoteDataSource: RemoteDataSource,
) : IMovieRepository {

    override fun getMovies(): Flow<PagingData<Movie>> {
        return remoteDataSource.getMovies()
            .map { pagingData ->
                pagingData.map { remoteMovie ->
                    remoteMovie.mapToDomain()
                }
            }
    }
}