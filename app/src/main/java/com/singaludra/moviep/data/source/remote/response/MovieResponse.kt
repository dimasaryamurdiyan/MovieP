package com.singaludra.moviep.data.source.remote.response

import com.google.gson.annotations.SerializedName
import com.singaludra.moviep.domain.model.Movie

data class MovieResponse(
    @SerializedName("adult")
    var adult: Boolean,
    @SerializedName("backdrop_path")
    var backdropPath: String,
    @SerializedName("genre_ids")
    var genreIds: List<Int>,
    @SerializedName("id")
    var id: Int,
    @SerializedName("original_language")
    var originalLanguage: String,
    @SerializedName("original_title")
    var originalTitle: String,
    @SerializedName("overview")
    var overview: String,
    @SerializedName("popularity")
    var popularity: Double,
    @SerializedName("poster_path")
    var posterPath: String,
    @SerializedName("release_date")
    var releaseDate: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("video")
    var video: Boolean,
    @SerializedName("vote_average")
    var voteAverage: Double,
    @SerializedName("vote_count")
    var voteCount: Int
)

fun MovieResponse.mapToDomain(): Movie{
    return Movie(
        id = this.id,
        isAdultOnly = this.adult,
        popularity = this.popularity,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        image = this.posterPath ?: this.backdropPath ?: "",
        title = this.title ?: this.originalTitle ?: "No title found",
        overview = this.overview,
        releaseDate = this.releaseDate ?: "No date found",
        originalLanguage = this.originalLanguage
    )
}