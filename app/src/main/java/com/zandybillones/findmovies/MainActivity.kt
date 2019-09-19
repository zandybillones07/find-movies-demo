package com.zandybillones.findmovies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.zandybillones.findmovies.model.Movie
import com.zandybillones.findmovies.viewmodel.MoviesViewModel
import com.zandybillones.traceutil.Trace
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var moviesViewModel:MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({
            val intent = Intent(this, ItemListActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)


    }
}
