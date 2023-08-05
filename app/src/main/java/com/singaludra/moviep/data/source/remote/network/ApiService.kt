package com.singaludra.moviep.data.source.remote.network

import com.singaludra.moviep.data.source.remote.response.DetailMovieResponse
import com.singaludra.moviep.data.source.remote.response.MovieResponse
import com.singaludra.moviep.data.source.remote.response.ResponseItems
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String,
        @Query("page") page: Int
    ): ResponseItems<MovieResponse>

    @GET("movie/{id}")
    suspend fun getDetailMovie(
        @Path("movie_id") id: Int
    ): DetailMovieResponse
}