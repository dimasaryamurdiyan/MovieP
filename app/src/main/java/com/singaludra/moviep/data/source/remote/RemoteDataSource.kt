package com.singaludra.moviep.data.source.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.singaludra.moviep.data.source.remote.network.ApiResponse
import com.singaludra.moviep.data.source.remote.network.ApiService
import com.singaludra.moviep.data.source.remote.paging.MoviesPagingSource
import com.singaludra.moviep.data.source.remote.response.DetailMovieResponse
import com.singaludra.moviep.data.source.remote.response.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
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

    override fun getDetailMovie(id: Int): Flow<ApiResponse<DetailMovieResponse>> {
        return flow {
            try {
                val response = movieService.getDetailMovie(
                    id
                )
                if (response != null) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.parse()))
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun Exception.parse(): String {
        return when(this){
            is IOException -> {
                "You have no connection!"
            }

            is HttpException -> {
                if(this.code() == 422){
                    "Invalid Data"
                } else {
                    "Invalid request"
                }
            }

            else -> {
                this.message.toString()
            }
        }
    }

}