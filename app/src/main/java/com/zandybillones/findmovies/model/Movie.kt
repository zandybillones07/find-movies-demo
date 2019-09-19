package com.zandybillones.findmovies.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Movie(
    @SerializedName("trackId") val trackId:Int,
    @SerializedName("collectionName") val collectionName:String,
    @SerializedName("trackName") val trackName:String,
    @SerializedName("primaryGenreName") val primaryGenreName:String,
    @SerializedName("currency") val currency:String,
    @SerializedName("collectionPrice") val collectionPrice:String,
    @SerializedName("artworkUrl100") val artworkUrl100:String,
    @SerializedName("longDescription") val longDescription:String,
    @SerializedName("previewUrl") val previewUrl:String
) : Serializable