package com.zandybillones.findmovies.model

import com.google.gson.annotations.SerializedName

data class MoviesResult(
    @SerializedName("resultCount") val resultCount:Int,
    @SerializedName("results") val movies:List<Movie>
)