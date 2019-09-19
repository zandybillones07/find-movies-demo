package com.zandybillones.findmovies

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.zandybillones.findmovies.dummy.DummyContent
import com.zandybillones.findmovies.model.Movie
import com.zandybillones.traceutil.Trace
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.item_detail.view.*

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ItemListActivity]
 * in two-pane mode (on tablets) or a [ItemDetailActivity]
 * on handsets.
 */
class ItemDetailFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private var item: Movie? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

            if (it.containsKey("item")) {
                item = it.getSerializable("item") as Movie
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        // Show the dummy content as text in a TextView.
        item?.let {
            rootView.item_title.text = it.trackName
            rootView.item_detail.text = it.longDescription
            rootView.item_genre.text = it.primaryGenreName
            rootView.item_price.text = "${it.currency} ${it.collectionPrice}"

            Picasso.get().load(it.artworkUrl100).into(rootView.item_thumb, object : Callback {
                override fun onSuccess() {
                    Trace.show("success")
                }

                override fun onError(e: Exception?) {
                    Trace.show("failed")
                }
            })

        }

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}
