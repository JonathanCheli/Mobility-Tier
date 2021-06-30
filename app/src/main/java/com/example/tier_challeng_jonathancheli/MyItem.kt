package com.example.tier_challeng_jonathancheli

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem


class MyItem(
    lat: Double,
    lng: Double,
    title: String,
    snippet: String,
    scooter: Scooter
) : ClusterItem {
    private val position: LatLng
    private val title: String
    private val snippet: String
    private val scooter: Scooter




    override fun getPosition(): LatLng {
        return position
    }

    override fun getTitle(): String? {
        return title
    }

    override fun getSnippet(): String? {
        return snippet
    }


    fun getScooter(): Scooter? {
        return scooter
    }





    init {
        position = LatLng(lat, lng)
        this.title = title
        this.snippet = snippet
        this.scooter = scooter




    }
}