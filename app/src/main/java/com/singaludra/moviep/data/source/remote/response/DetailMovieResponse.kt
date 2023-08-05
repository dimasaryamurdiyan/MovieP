package com.singaludra.moviep.data.source.remote.response
import com.google.gson.annotations.SerializedName
import com.singaludra.moviep.domain.model.Movie


data class DetailMovieResponse(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("budget")
    val budget: Int,
    @SerializedName("genres")
    val genres: List<Genre>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("imdb_id")
    val imdbId: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("revenue")
    val revenue: Int,
    @SerializedName("runtime")
    val runtime: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("tagline")
    val tagline: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("video")
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
) {
    data class Genre(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
    )

    data class ProductionCompany(
        @SerializedName("id")
        val id: Int,
        @SerializedName("logo_path")
        val logoPath: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("origin_country")
        val originCountry: String
    )
}

fun DetailMovieResponse.mapToDomain(): Movie{
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
        originalLanguage = this.originalLanguage,
        genres = this.genres.map {
            Movie.Genre(
                id = it.id,
                name = it.name
            )
        }
    )
}