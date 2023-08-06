package com.singaludra.moviep.domain.model

import com.google.gson.annotations.SerializedName

data class Video(
    val id: String,
    val key: String,
    val name: String,
    val official: Boolean,
    val publishedAt: String,
    val site: String,
    val size: Int,
    val type: String
)
