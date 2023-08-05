package com.singaludra.moviep.data.source.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.singaludra.moviep.data.source.remote.network.ApiService
import com.singaludra.moviep.data.source.remote.paging.MoviesPagingSource
import com.singaludra.moviep.data.source.remote.response.MovieResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

const val NETWORK_PAGE_SIZE = 25
class RemoteDataSource @Inject constructor(
    private val movieService: ApiService
): IRemoteDataSource{
    override fun getMovies(): Flow<PagingData<MovieResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviesPagingSource(service = movieService)
            }
        ).flow
    }

}