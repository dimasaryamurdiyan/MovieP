package com.singaludra.moviep.domain.model

data class Movie(
    val id: Int,
    val isAdultOnly: Boolean,
    val popularity: Double,
    val voteAverage: Double,
    val voteCount: Int,
    val image: String,
    val backdropImage: String? = null,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val originalLanguage: String,
    val genres: List<Movie.Genre>? = null
) {
    class Genre(
        val id: Int,
        val name: String
    )
}