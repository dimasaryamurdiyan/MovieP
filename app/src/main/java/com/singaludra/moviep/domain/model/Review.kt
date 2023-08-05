package com.singaludra.moviep.domain.model

import com.singaludra.moviep.data.source.remote.response.ReviewResponse

data class Review(
    val author: String,
    val content: String,
    val createdAt: String,
    val id: String,
    val updatedAt: String,
    val url: String
)
