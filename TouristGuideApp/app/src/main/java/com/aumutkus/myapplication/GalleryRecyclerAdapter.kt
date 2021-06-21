package com.aumutkus.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class GalleryRecyclerAdapter(private val hostelNameArray: ArrayList<String>, private val hostelInformationArray : ArrayList<String>, private val hostelImageArray : ArrayList<String>) : RecyclerView.Adapter<GalleryRecyclerAdapter.PostHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_view_gallery,parent,false)
        return PostHolder(view)
    }

    override fun getItemCount(): Int {
        return hostelNameArray.size

    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.recyclerNameText?.text = hostelNameArray[position]
        holder.recyclerInformationText?.text = hostelInformationArray[position]
        Picasso.get().load(hostelImageArray[position]).into(holder.recyclerImageView)

    }


    class PostHolder(view : View) : RecyclerView.ViewHolder(view) {

        //View Holder class

        var recyclerNameText : TextView? = null
        var recyclerInformationText : TextView? = null
        var recyclerImageView : ImageView? = null


        init {
            recyclerNameText = view.findViewById(R.id.recyclerNameText)
            recyclerInformationText = view.findViewById(R.id.recyclerInformationText)
            recyclerImageView = view.findViewById(R.id.recyclerImageView)

        }

    }


}