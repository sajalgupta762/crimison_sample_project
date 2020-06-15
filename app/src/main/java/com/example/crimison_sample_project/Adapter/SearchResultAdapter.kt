package com.example.crimison_sample_project.Adapter

import android.os.Handler
import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.crimison_sample_project.R
import com.example.crimison_sample_project.Utils.ConstantsApp
import com.example.crimison_sample_project.Utils.OnItemClickListener
import com.example.crimison_sample_project.models.Search
import com.example.crimison_sample_project.models.SearchX
import kotlinx.android.synthetic.main.search_item_layout.view.*
import java.util.Collections.addAll



class SearchResultAdapter(private var items:ArrayList<SearchX>, private val itemClickListener: OnItemClickListener): RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(search: SearchX,clickListener: OnItemClickListener) {
            itemView.setOnClickListener {

                clickListener.onItemClicked(adapterPosition)

            }
            itemView.apply {
                imdbID_Tv.text = search.imdbID
                yearTv.text = search.Year
                titleTv.text = search.Title
                typeTv.text = search.Type
                Glide.with(imageViewPoster.context)
                    .load(search.Poster)
                    .into(imageViewPoster)
            }
        }
    }

    override fun getItemCount(): Int=items.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.search_item_layout, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

       // holder.bind(items[position])
        holder.bind(items.get(position),itemClickListener)

    }

    fun addLoadingView() {
        //Add loading item
        Handler().post {
          //  items.add(null)
            notifyItemInserted(items.size - 1)
        }
    }


fun addUsers(search: List<SearchX>) {

    items.apply {
        //items.clear()
      items.addAll(search)
       notifyDataSetChanged()
    }

}

    override fun getItemViewType(position: Int): Int {
        return if (items[position] == null) {
            ConstantsApp.VIEW_TYPE_LOADING
        } else {
            ConstantsApp.VIEW_TYPE_ITEM
        }
    }





}