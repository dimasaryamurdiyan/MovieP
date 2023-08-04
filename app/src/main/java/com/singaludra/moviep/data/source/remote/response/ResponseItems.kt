package com.singaludra.moviep.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseItems<T>(
    @SerializedName("results")
    val results: List<T>
)
