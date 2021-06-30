package com.example.tier_challeng_jonathancheli


import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer


class MarkerClusterRenderer(
    var context: Context?, map: GoogleMap?,
    clusterManager: ClusterManager<MyItem>
) :
    DefaultClusterRenderer<MyItem>(context, map, clusterManager) {

    override fun onBeforeClusterItemRendered(item: MyItem, markerOptions: MarkerOptions) {
        markerOptions.icon(bitmapDescriptorFromVector(this.context!!,R.drawable.ic_location_blue))
    }



    private fun bitmapDescriptorFromVector(
        context: Context,
        @DrawableRes vectorResId: Int
    ): BitmapDescriptor? {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    /*
    override fun onClusterItemRendered(clusterItem: MyItem, marker: Marker) {
        super.onClusterItemRendered(clusterItem, marker)
        getMarker(clusterItem).showInfoWindow()
    }

     */


}