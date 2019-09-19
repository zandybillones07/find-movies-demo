package com.zandybillones.findmovies

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

import com.zandybillones.findmovies.model.Movie
import com.zandybillones.findmovies.viewmodel.MoviesViewModel
import com.zandybillones.traceutil.Trace
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list_content.view.*
import kotlinx.android.synthetic.main.item_list.*

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ItemListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false
    lateinit var moviesViewModel: MoviesViewModel
    var list = ArrayList<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        setSupportActionBar(toolbar)
        toolbar.title = "Find Movies"


        if (item_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel::class.java)
        moviesViewModel.init()
        moviesViewModel.getMovieList().observe(this, object : Observer<ArrayList<Movie>> {
            override fun onChanged(t: ArrayList<Movie>?) {
                list = t!!
                setupRecyclerView(item_list)
                for (m in t!!) {
                    Trace.show("trackID: ${m.trackId} name: ${m.collectionName}")
                }
            }
        })



    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {

        recyclerView.adapter = SimpleItemRecyclerViewAdapter(this, list, twoPane)
    }

    class SimpleItemRecyclerViewAdapter(
        private val parentActivity: ItemListActivity,
        private val values: List<Movie>,
        private val twoPane: Boolean) : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as Movie
                if (twoPane) {
                    val fragment = ItemDetailFragment().apply {
                        arguments = Bundle().apply {
                            putString(ItemDetailFragment.ARG_ITEM_ID, item.collectionName)
                            putSerializable("item", item)
                        }
                    }
                    parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit()
                } else {
                    val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                        putExtra(ItemDetailFragment.ARG_ITEM_ID, item.collectionName)
                        putExtra("item", item)
                    }
                    v.context.startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.id_collection_name.text = "" + item.trackName
            holder.id_price.text = "${item.currency} ${item.collectionPrice}"
            holder.id_genre.text = "" + item.primaryGenreName

            Picasso.get().load(item.artworkUrl100).placeholder(R.drawable.ic_movie_black_24dp).into(holder.id_thumb, object : Callback {
                override fun onSuccess() {
                    Trace.show("success")
                }

                override fun onError(e: Exception?) {
                    Trace.show("failed")
                }
            })

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val id_price: TextView = view.id_price
            val id_genre: TextView = view.id_genre
            val id_collection_name : TextView = view.id_collection_name
            val id_thumb : ImageView = view.id_thumb

        }
    }
}
