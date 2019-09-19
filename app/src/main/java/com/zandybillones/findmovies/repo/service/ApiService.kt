package com.zandybillones.findmovies.repo.service

import com.zandybillones.findmovies.model.Movie
import com.zandybillones.findmovies.model.MoviesResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("https://itunes.apple.com/search?term=star&country=au&media=movie")
    fun getMovies():Call<MoviesResult>
}