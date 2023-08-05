package com.singaludra.moviep.data.source

import androidx.paging.PagingData
import androidx.paging.map
import com.singaludra.moviep.data.source.remote.IRemoteDataSource
import com.singaludra.moviep.data.source.remote.RemoteDataSource
import com.singaludra.moviep.data.source.remote.response.mapToDomain
import com.singaludra.moviep.domain.model.Movie
import com.singaludra.moviep.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
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
}