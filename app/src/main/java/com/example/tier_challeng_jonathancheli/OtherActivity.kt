package com.example.tier_challeng_jonathancheli



import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.tier_challeng_jonathancheli.N_Adapter.NewsAdapter
import com.example.tier_challeng_jonathancheli.N_Model.NewsItem
import com.example.tier_challeng_jonathancheli.OA_Adapter.ImageAdapter
import com.example.tier_challeng_jonathancheli.OA_Model.ImageItem
import com.example.tier_challeng_jonathancheli.R_Adapter.RecommendationAdapter
import com.example.tier_challeng_jonathancheli.R_Model.RecommendationItem
import java.util.*
import android.widget.TextView


class OtherActivity : AppCompatActivity() {

    var recyclerView: RecyclerView? = null
    var recyclerView2: RecyclerView? = null
    var recyclerView3: RecyclerView? = null

    // Array list for recycler view data source
    var source: MutableList<ImageItem>? = null
    var source2: MutableList<RecommendationItem>? = null
    var source3: MutableList<NewsItem>? = null

    // Layout Manager
    var RecyclerViewLayoutManager: LayoutManager? = null
    var RecyclerViewLayoutManager2: LayoutManager? = null
    var RecyclerViewLayoutManager3: LayoutManager? = null

    // adapter class object
    var adapter: ImageAdapter? = null
    var adapter2: RecommendationAdapter? = null
    var adapter3: NewsAdapter? = null

    // Linear Layout Manager
    var HorizontalLayout: LinearLayoutManager? = null
    var HorizontalLayout2: LinearLayoutManager? = null
    var HorizontalLayout3: LinearLayoutManager? = null


    var textZoneId : TextView? = null
    var textResolution : TextView? = null
    var textBattery : TextView? = null
    var textState : TextView? = null
    var textModel : TextView? = null
    var textFleetbirdId_: TextView? = null
    var textNewlocationName_ : TextView?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.other_activity)

        textZoneId = findViewById(R.id.id_zoneId)
        textResolution = findViewById(R.id.id_resolution)
        textBattery = findViewById(R.id.id_battery)
        textState = findViewById(R.id.id_state)
        textModel = findViewById(R.id.id_model)
        textFleetbirdId_= findViewById(R.id.id_FleetbirdId)
        textNewlocationName_ = findViewById(R.id.id_Address)



        receiveData()

        recycler1()
        recycler2()
        recycler3()
    }

    private fun receiveData() {
        val zoneId = intent.getStringExtra("zoneId")
        val resolution = intent.getStringExtra("resolution")
        val battery = intent.getIntExtra("battery", 0)
        val state = intent.getStringExtra("state")
        val model = intent.getStringExtra("model")
        val fleetbirdId_ = intent.getIntExtra("fleetbirdId_", 0)
        val newlocationName_ = intent.getStringExtra("newlocationName_")


        textZoneId!!.text = "ZoneId: " + zoneId
        textResolution!!.text = "Resolution: " + resolution
        textBattery!!.text = "Battery: " + battery.toString()
        textState!!.text = "State: " + state
        textModel!!.text = "Model: " + model
        textFleetbirdId_!!.text = "FleetbirdId: " + fleetbirdId_.toString()
        textNewlocationName_!!.text = "Street Address: " + newlocationName_


    }


    private fun recycler1() {
        // initialisation with id's
        recyclerView = findViewById<View>(
            R.id.recyclerView
        ) as RecyclerView
        RecyclerViewLayoutManager = LinearLayoutManager(
            applicationContext
        )

        // Set LayoutManager on Recycler View
        recyclerView!!.layoutManager = RecyclerViewLayoutManager

        // Adding items to RecyclerView.
        AddItemsToRecyclerViewArrayList()

        // calling constructor of adapter
        // with source list as a parameter

        adapter = ImageAdapter(source!!,this)

        // Set Horizontal Layout Manager
        // for Recycler view
        HorizontalLayout = LinearLayoutManager(
            this@OtherActivity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerView!!.layoutManager = HorizontalLayout

        // Set adapter on recycler view
        recyclerView!!.adapter = adapter
    }

    private fun recycler2() {
        // initialisation with id's 2
        recyclerView2 = findViewById<View>(
            R.id.recommendation_recyclerView
        ) as RecyclerView
        RecyclerViewLayoutManager2 = LinearLayoutManager(
            applicationContext
        )

        // Set LayoutManager on Recycler View2
        recyclerView2!!.layoutManager = RecyclerViewLayoutManager2
        // Adding items to RecyclerView2.
        AddItemsToRecyclerViewArrayList2()

        // calling constructor of adapter2
        // with source2 list as a parameter
        adapter2 = RecommendationAdapter(source2!!,this)

        // Set Horizontal Layout2 Manager
        // for Recycler view
        HorizontalLayout2 = LinearLayoutManager(
            this@OtherActivity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerView2!!.layoutManager = HorizontalLayout2

        // Set adapter2 on recycler view2
        recyclerView2!!.adapter = adapter2

    }

    private fun recycler3() {
        // initialisation with id's 3
        recyclerView3 = findViewById<View>(
            R.id.news_recyclerView
        ) as RecyclerView
        RecyclerViewLayoutManager3 = LinearLayoutManager(
            applicationContext
        )

        // Set LayoutManager on Recycler View3
        recyclerView3!!.layoutManager = RecyclerViewLayoutManager3
        // Adding items to RecyclerView3.
        AddItemsToRecyclerViewArrayList3()

        // calling constructor of adapter3
        // with source2 list as a parameter
        adapter3 = NewsAdapter(source3!!,this)

        // Set Horizontal Layout3 Manager
        // for Recycler view
        HorizontalLayout3 = LinearLayoutManager(
            this@OtherActivity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerView3!!.layoutManager = HorizontalLayout3

        // Set adapter3 on recycler view3
        recyclerView3!!.adapter = adapter3


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



    private fun AddItemsToRecyclerViewArrayList2() {
        source2 = ArrayList()
        source2!!.add(RecommendationItem(R.drawable.tierimage,"",""))
        source2!!.add(RecommendationItem(R.drawable.tier1,"",""))
        source2!!.add(RecommendationItem(R.drawable.tier2,"",""))
        source2!!.add(RecommendationItem(R.drawable.tier3,"",""))
        source2!!.add(RecommendationItem(R.drawable.tier4,"",""))
        source2!!.add(RecommendationItem(R.drawable.tier5,"",""))
        source2!!.add(RecommendationItem(R.drawable.tier6,"",""))
        source2!!.add(RecommendationItem(R.drawable.tier7,"",""))
        source2!!.add(RecommendationItem(R.drawable.tier8,"",""))
    }


    private fun AddItemsToRecyclerViewArrayList3() {
        source3 = ArrayList()
        source3!!.add(NewsItem(R.drawable.tierimage,"",""))
        source3!!.add(NewsItem(R.drawable.tier1,"",""))
        source3!!.add(NewsItem(R.drawable.tier2,"",""))
        source3!!.add(NewsItem(R.drawable.tier3,"",""))
        source3!!.add(NewsItem(R.drawable.tier4,"",""))
        source3!!.add(NewsItem(R.drawable.tier5,"",""))
        source3!!.add(NewsItem(R.drawable.tier6,"",""))
        source3!!.add(NewsItem(R.drawable.tier7,"",""))
        source3!!.add(NewsItem(R.drawable.tier8,"",""))


    }

}