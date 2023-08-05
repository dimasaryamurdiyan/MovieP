package com.singaludra.moviep.data.source.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.singaludra.moviep.data.source.remote.NETWORK_PAGE_SIZE
import com.singaludra.moviep.data.source.remote.network.ApiService
import com.singaludra.moviep.data.source.remote.response.MovieResponse
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val TMDB_STARTING_PAGE_INDEX = 1

class MoviesPagingSource @Inject constructor(
    private val service: ApiService
): PagingSource<Int, MovieResponse>() {
    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        val pageIndex = params.key ?: TMDB_STARTING_PAGE_INDEX
        return try {
            val response = service.getTopRatedMovies(
                language = "en-US",
                page = pageIndex
            )
            val movies = response.results
            val nextKey =
                if (movies.isEmpty()) {
                    null
                } else {
                    // By default, initial load size = 3 * NETWORK PAGE SIZE
                    // ensure we're not requesting duplicating items at the 2nd request
                    pageIndex + (params.loadSize / NETWORK_PAGE_SIZE)
                }
            LoadResult.Page(
                data = movies,
                prevKey = if (pageIndex == TMDB_STARTING_PAGE_INDEX) null else pageIndex,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

}