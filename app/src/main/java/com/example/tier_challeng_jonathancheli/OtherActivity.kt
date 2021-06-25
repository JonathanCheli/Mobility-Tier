package com.example.tier_challeng_jonathancheli



import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.tier_challeng_jonathancheli.OA_Adapter.ImageAdapter
import com.example.tier_challeng_jonathancheli.OA_Model.ImageItem
import java.util.*


class OtherActivity : AppCompatActivity() {


    var recyclerView: RecyclerView? = null

    // Array list for recycler view data source
    var source: MutableList<ImageItem>? = null

    // Layout Manager
    var RecyclerViewLayoutManager: LayoutManager? = null

    // adapter class object
    var adapter: ImageAdapter? = null

    // Linear Layout Manager
    var HorizontalLayout: LinearLayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.other_activity)

        // initialisation with id's
        // initialisation with id's
        recyclerView = findViewById<View>(
            R.id.recyclerView
        ) as RecyclerView
        RecyclerViewLayoutManager = LinearLayoutManager(
            applicationContext
        )

        recyclerView = findViewById<View>(
            R.id.recyclerView
        ) as RecyclerView
        RecyclerViewLayoutManager = LinearLayoutManager(
            applicationContext
        )

        // Set LayoutManager on Recycler View

        // Set LayoutManager on Recycler View
        recyclerView!!.layoutManager = RecyclerViewLayoutManager

        // Adding items to RecyclerView.

        // Adding items to RecyclerView.
        AddItemsToRecyclerViewArrayList()

        // calling constructor of adapter
        // with source list as a parameter

        // calling constructor of adapter
        // with source list as a parameter
        adapter = ImageAdapter(source!!,this)

        // Set Horizontal Layout Manager
        // for Recycler view

        // Set Horizontal Layout Manager
        // for Recycler view
        HorizontalLayout = LinearLayoutManager(
            this@OtherActivity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerView!!.layoutManager = HorizontalLayout

        // Set adapter on recycler view

        // Set adapter on recycler view
        recyclerView!!.adapter = adapter



    }



    private fun AddItemsToRecyclerViewArrayList() {
        source = ArrayList()
        source!!.add(ImageItem(R.drawable.tierimage))
        source!!.add(ImageItem(R.drawable.tier1))
        source!!.add(ImageItem(R.drawable.tier2))
        source!!.add(ImageItem(R.drawable.tier3))
        source!!.add(ImageItem(R.drawable.tier4))
        source!!.add(ImageItem(R.drawable.tier5))
        source!!.add(ImageItem(R.drawable.tier6))
        source!!.add(ImageItem(R.drawable.tier7))
        source!!.add(ImageItem(R.drawable.tier8))

    }



}