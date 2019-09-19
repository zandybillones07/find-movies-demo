package com.zandybillones.findmovies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zandybillones.findmovies.model.Movie
import com.zandybillones.findmovies.repo.MovieListRepo

class MoviesViewModel : ViewModel() {

    private lateinit var movies:MutableLiveData<ArrayList<Movie>>

    fun init() {
        movies = MovieListRepo.getMovieList()
    }

    fun getMovieList() : LiveData<ArrayList<Movie>> {
        return movies
    }

}