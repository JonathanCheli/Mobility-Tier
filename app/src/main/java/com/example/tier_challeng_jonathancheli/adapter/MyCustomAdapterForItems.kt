package com.example.tier_challeng_jonathancheli.adapter


import android.app.Activity
import android.content.Context

import android.view.View

import android.widget.TextView
import android.widget.ImageView


import com.example.tier_challeng_jonathancheli.R

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker


class MyCustomAdapterForItems(context: Context) : GoogleMap.InfoWindowAdapter {

    var mContext = context
    var mWindow = (context as Activity).layoutInflater.inflate(R.layout.info_window, null)

    private fun rendowWindowText(marker: Marker, view: View) {

        val title = view.findViewById<TextView>(R.id.txtTitle)
        val snippet = view.findViewById<TextView>(R.id.txtSnippet)

        val image = view.findViewById<ImageView>(R.id.image_infoWindow2)



        title.text = marker!!.title
        snippet.text = marker!!.snippet
        image.setImageResource(R.drawable.vehicleimage2)



    }

    override fun getInfoContents(marker: Marker): View {
        rendowWindowText(marker, mWindow)
        return mWindow
    }

    override fun getInfoWindow(marker: Marker): View? {
        rendowWindowText(marker, mWindow)
        return mWindow
    }


}