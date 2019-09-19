package com.zandybillones.findmovies.repo

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.MutableLiveData
import com.zandybillones.findmovies.MainApplication
import com.zandybillones.findmovies.model.Movie
import com.zandybillones.findmovies.model.MoviesResult
import com.zandybillones.findmovies.repo.service.ApiService
import com.zandybillones.findmovies.utils.Constants
import com.zandybillones.traceutil.Trace
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieListRepo {



    var list = ArrayList<Movie>()

    private const val cacheSize = (5 * 1024 * 1024).toLong()
    private val context = MainApplication.applicationContext()
    private val myCache = Cache(context.cacheDir, cacheSize)
    private val okHttpClient = OkHttpClient.Builder()
        .cache(myCache)
        .addInterceptor { chain ->
            var request = chain.request()
            request = if (hasNetwork(context)!!) {
                request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
            } else {
                request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7)
                    .build()
            }
            chain.proceed(request)
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    private val service = retrofit.create(ApiService::class.java)


    fun getMovieList():MutableLiveData<ArrayList<Movie>> {
        val mutData = MutableLiveData<ArrayList<Movie>>()
        val call = service.getMovies()
        call.enqueue(object : Callback<MoviesResult> {
            override fun onResponse(call: Call<MoviesResult>, response: Response<MoviesResult>) {
                if (response.code() == 200) {
                    Trace.show("Retro result: ${response.body()}")
                    mutData.postValue(response.body().movies as ArrayList<Movie>?)
                }
            }
            override fun onFailure(call: Call<MoviesResult>, t: Throwable) {
                Trace.show("FAILED")
            }
        })
        return mutData
    }

    private fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }

}