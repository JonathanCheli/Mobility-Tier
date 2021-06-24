package com.example.tier_challeng_jonathancheli

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tier_challeng_jonathancheli.Helper.DirectionFinder
import com.example.tier_challeng_jonathancheli.Helper.DirectionFinderListener
import com.example.tier_challeng_jonathancheli.Helper.Route
import com.example.tier_challeng_jonathancheli.adapter.MyCustomAdapterForItems
import com.example.tier_challeng_jonathancheli.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.clustering.ClusterManager
import java.util.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback,  DirectionFinderListener , ClusterManager.OnClusterItemInfoWindowClickListener<MyItem> {

    private var mMap: GoogleMap? = null
    private lateinit var binding: ActivityMapsBinding
    private var dataViewModel: DataViewModel? = null
    private lateinit var fusedLocation: FusedLocationProviderClient
    private var currentLocation: LatLng? = null
    private var latLng : LatLng ? = null
    private var nearestLocation : LatLng? = null
    private var nearestLocationBattery : Int?= null
    private var fleetbirdId : Int? = null
    var clusterManager : ClusterManager<MyItem>? =null
    var items : MutableList<MyItem>? = null

    private var clickedClusterItem :MyItem? = null


    private var scooterData : Scooter? = null



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

    private var originMarkers: MutableList<Marker>? = ArrayList()
    private var destinationMarker: MutableList<Marker>? = ArrayList()
    private var polyLinePaths: MutableList<Polyline>? = ArrayList()
    private var progressDialog: ProgressDialog? = null


    private var markerPosition : LatLng? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        fusedLocation = LocationServices.getFusedLocationProviderClient(this)

    }

    @SuppressLint("SetTextI18n")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap




        clusterManager = ClusterManager<MyItem>(this, mMap)
        clusterManager!!.renderer = MarkerClusterRenderer(this, mMap, clusterManager!!)
        mMap!!.setOnCameraIdleListener(clusterManager)
        mMap !!. setOnMarkerClickListener(clusterManager)
        mMap !!. setOnInfoWindowClickListener (clusterManager)

        mMap!!.setInfoWindowAdapter(clusterManager!!.markerManager)
        clusterManager!!.setOnClusterItemInfoWindowClickListener(this)



        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1

            )
            return
        }

        mMap!!.uiSettings.isZoomControlsEnabled = true
        mMap!!.uiSettings.isCompassEnabled = true
        mMap!!.isMyLocationEnabled = true
        fusedLocation.lastLocation.addOnSuccessListener { location ->


            dataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)
            dataViewModel!!.init()
            dataViewModel!!.getScooterData()!!.observe(this, Observer {

            it.forEach { latLonModel ->
                latLng = LatLng(latLonModel.latitude.toDouble(), latLonModel.longitude.toDouble())


                scooterData = latLonModel

                clusterManager!!.setOnClusterItemClickListener(object :
                    ClusterManager.OnClusterItemClickListener<MyItem?> {
                    override fun onClusterItemClick(item: MyItem?): Boolean {
                        clickedClusterItem = item!!

                        markerPosition = item.position


                        return false


                    }


                })





                addPersonItems(latLonModel)

                clusterManager!!.markerCollection.setInfoWindowAdapter(MyCustomAdapterForItems(this))



                clusterManager!!.cluster()



                items = mutableListOf()


                var distance = 0.0
                var nearestLocationIndex = 0
                for (i in it.indices) {

                    val currentDistance = getDistanceInMeter(
                        LatLng(it.get(i).latitude.toDouble(), it.get(i).longitude.toDouble()),
                        currentLocation!!
                    )


                    if (i == 0) {
                        distance = currentDistance
                        nearestLocationIndex = i

                    } else {
                        if (currentDistance < distance) {
                            distance = currentDistance
                            nearestLocationIndex = i

                            nearestLocation = LatLng(
                                it[nearestLocationIndex].latitude.toDouble(),
                                it[nearestLocationIndex].longitude.toDouble()
                            )


                            nearestLocationBattery = it[nearestLocationIndex].battery
                            fleetbirdId = it[nearestLocationIndex].fleetbirdId


                        }

                    }

                }

            }


        })



            // i will add a Marker with this currentLocation in onDirectionFinderSuccess Method//
            if (location != null) {
                currentLocation = LatLng(location.latitude, location.longitude)
                mMap!!.moveCamera(CameraUpdateFactory.newLatLng(currentLocation!!))
                mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation!!, 15f))


            }



        }
        




        binding.closestLocationButton.setOnClickListener{

            sendRequest()

            binding.idLinear.visibility = View.VISIBLE
            binding.batteryId.text = nearestLocationBattery.toString() + " %"
            binding.batteryId.setCompoundDrawablesWithIntrinsicBounds(R.drawable.battery, 0, 0, 0)


            binding.idTierMobility.text = getString(R.string.tier_number) + fleetbirdId.toString()
        }


    }

    private fun addPersonItems(latLonModel: Scooter) {
        val newlocationName = obtainAddress(latLng!!)
        clusterManager!!.addItem(MyItem(latLonModel.latitude.toDouble(), latLonModel.longitude.toDouble(), newlocationName,snippet = ""))


    }


    private fun cameraConfigurations(latLng: LatLng) {
        mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latLng.latitude,latLng.longitude),15f))


    }

    private fun sendRequest() {
        try {
            DirectionFinder(this, currentLocation!!, nearestLocation!!).execute()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getDistanceInMeter(start: LatLng, end: LatLng): Double {
        val startPoint = Location("locationA")
        startPoint.latitude = start.latitude
        startPoint.longitude = start.longitude
        val endPoint = Location("locationB")
        endPoint.latitude = end.latitude
        endPoint.longitude = end.longitude
        return startPoint.distanceTo(endPoint).toDouble()

    }

    fun obtainAddress(latLng: LatLng): String {
        // Geocoder retuns a list of Address, from which i am going to pick the first//
        val geocoder = Geocoder(this)
        val addresses: List<Address>?
        val firsAddress: Address
        //when im getting the first address from the list of address, ill get it in String format//
        var textAddress = ""


        //so i am getting lat and lng in a list, and iam getting the 1st of that list//
        //IN CASE YOU ARE NOT FINDING THE ADDRESS, LETS DO TRY CATCH//
        try {

            addresses = geocoder.getFromLocation(
                latLng.latitude, latLng.longitude, 1
            )
            //we check now if that address exist or if it does not//
            if (addresses != null && addresses.isNotEmpty()) {
                firsAddress = addresses[0]
                // BUT THE ADDRESS COULD COME IN VARIOUS FORMATS, IT COULD CONTAIN SEVEAL LINES//
                //SO WE CHECK THE IF THE FIRST LIEN CONTAINS THOSE LINES//
                if (firsAddress.maxAddressLineIndex > 0) {
                    // we go through the whole list//
                    for (i in 0..firsAddress.maxAddressLineIndex) {
                        // from the list ..again we are interested in the first address and we add it to the textAddress//
                        textAddress += firsAddress.getAddressLine(i) + "\n"
                    }
                }
                // this is in the case we dont have verious lines//
                else {
                    textAddress += firsAddress.thoroughfare + ", " +
                            firsAddress.subThoroughfare + "\n"
                }

            }
        } catch (e: Exception) {
            textAddress = "Address not found"
        }

        // we return the text of the address//
        return textAddress
    }



    fun onDirectionFinderStart(route: List<Route?>?) {

        progressDialog = ProgressDialog.show(this, "Please wait", "Finding direction", true)
        if (originMarkers != null) {
            for (marker in originMarkers!!) {
                marker.remove()
            }
        }
        if (destinationMarker != null) {
            for (marker in destinationMarker!!) {
                marker.remove()
            }
        }


        if (polyLinePaths != null) {
            for (polylinePath in polyLinePaths!!) {
                polylinePath.remove()
            }
        }
    }

    override fun onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait", "Finding direction", true)
        if (originMarkers != null) {
            for (marker in originMarkers!!) {
                marker.remove()
            }
        }
        if (destinationMarker != null) {
            for (marker in destinationMarker!!) {
                marker.remove()
            }
        }

        if (polyLinePaths != null) {
            for (polylinePath in polyLinePaths!!) {
                polylinePath.remove()
            }
        }
    }

    override fun onDirectionFinderSuccess(route: MutableList<Route>) {
        progressDialog!!.dismiss()
        polyLinePaths = ArrayList()
        originMarkers = ArrayList()
        destinationMarker = ArrayList()
        for (route in route) {

            (findViewById<View>(R.id.textViewDistance) as TextView).text = route.distance!!.text
            (findViewById<View>(R.id.textViewTime) as TextView).text = route.duration!!.text


            originMarkers!!.add(
                mMap!!.addMarker(
                    MarkerOptions().position(currentLocation!!).title("Current location in Berlin")
                )!!

            )

            destinationMarker!!.add(
                mMap!!.addMarker(
                    MarkerOptions()
                        .position(nearestLocation!!)
                        .icon(bitmapDescriptorFromVector(this,R.drawable.ic_baseline_radio_button_checked_24))

                )!!
            )
            mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(nearestLocation!!, 15f))



            val polylineOptions = PolylineOptions()
                .geodesic(true)
                .color(Color.BLUE)
                .width(10f)
            for (i in 0 until route.points!!.size) {
                 polylineOptions.add(route.points!![i])
            }

             polyLinePaths!!.add(mMap!!.addPolyline(polylineOptions))
            
        }
        mMap!!.setOnMapLongClickListener {

            binding.idLinear.visibility = View.GONE
            binding.idTierMobility.text = getString(R.string.tier_mobility)
            for (destMarker in destinationMarker!!) {
                destMarker.remove()
            }

            if (polyLinePaths != null) {
                for (polylinePath in polyLinePaths!!) {
                    polylinePath.remove()
                }
            }

        }
    }

    override fun onClusterItemInfoWindowClick(item: MyItem?) {

        val intent = Intent(this, OtherActivity::class.java)


        startActivity(intent)

        //You may want to do different things for each InfoWindow:

        //You may want to do different things for each InfoWindow:
        if (item!!.getTitle().equals("some title")) {

            //do something specific to this InfoWindow....
        }

    }
}



