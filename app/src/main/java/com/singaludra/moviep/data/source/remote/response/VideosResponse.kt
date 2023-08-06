package com.singaludra.moviep.data.source.remote.response
import com.google.gson.annotations.SerializedName
import com.singaludra.moviep.domain.model.Video


data class VideosResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val results: List<Result>
) {
    data class Result(
        @SerializedName("id")
        val id: String,
        @SerializedName("key")
        val key: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("official")
        val official: Boolean,
        @SerializedName("published_at")
        val publishedAt: String,
        @SerializedName("site")
        val site: String,
        @SerializedName("size")
        val size: Int,
        @SerializedName("type")
        val type: String
    )
}

fun VideosResponse.Result.mapToDomain(): Video{
    return Video(
        id, key, name, official, publishedAt, site, size, type
    )
}