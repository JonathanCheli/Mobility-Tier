package com.example.tier_challeng_jonathancheli.R_Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.tier_challeng_jonathancheli.R
import com.example.tier_challeng_jonathancheli.R_Model.RecommendationItem
import com.example.tier_challeng_jonathancheli.SingleImage

class RecommendationAdapter     // Constructor for adapter class
// which takes a list of Images type
    (  // List with ImageItem type
    private val list: MutableList<RecommendationItem> = ArrayList(),

    val context : Context
) :
    RecyclerView.Adapter<RecommendationAdapter.MyView>() {
    // View Holder class which
    // extends RecyclerView.ViewHolder
    inner class MyView(view: View) : RecyclerView.ViewHolder(view) {
        // Text View
        var imageView: ImageView

        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        init {

            // initialise ImageView with id
            imageView = view
                .findViewById<View>(R.id.id_recommendation_recycler_image) as ImageView
        }
    }

    // Override onCreateViewHolder which deals
    // with the inflation of the card layout
    // as an item for the RecyclerView.
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyView {

        // Inflate item.xml using LayoutInflator
        val itemView: View = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recommendation_recycler_item,
                parent,
                false
            )

        // return itemView
        return MyView(itemView)
    }

    // Override onBindViewHolder which deals
    // with the setting of different data
    // and methods related to clicks on
    // particular items of the RecyclerView.
    override fun onBindViewHolder(holder: MyView, position: Int) {
        val imageP = list[position]


        // Set the image of each item of
        // Recycler view with the list items
        holder.imageView.setImageResource(imageP.image)





    }

    // Override getItemCount which Returns
    // the length of the RecyclerView.
    override fun getItemCount(): Int {
        return list.size
    }
}