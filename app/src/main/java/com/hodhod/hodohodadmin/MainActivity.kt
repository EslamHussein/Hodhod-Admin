package com.hodhod.hodohodadmin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*
import com.google.maps.android.clustering.ClusterManager
import com.hodhod.hodohodadmin.dto.Reporter
import com.hodhod.hodohodadmin.dto.SampleClusterItem
import kotlinx.android.synthetic.main.activity_maps.*


const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), OnMapReadyCallback, ValueEventListener {


    private lateinit var mMap: GoogleMap


    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var database: FirebaseDatabase
    private lateinit var reportersDB: DatabaseReference


    private lateinit var mClusterManager: ClusterManager<SampleClusterItem>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        viewManager = LinearLayoutManager(this)
        viewAdapter = ServiceProvidersAdapter()

        database = FirebaseDatabase.getInstance()
        reportersDB = database.getReference("reporters")


        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mClusterManager = ClusterManager(this@MainActivity, mMap)

        googleMap.setOnCameraIdleListener(mClusterManager);


        val renderer = CustomClusterRenderer(this, mMap, mClusterManager)

        mClusterManager.renderer = renderer


        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID


//        serviceProviderRecyclerView.apply {
//            setHasFixedSize(true)
//
//            // use a linear layout manager
//            layoutManager = viewManager
//
//            // specify an viewAdapter (see also next example)
//            adapter = viewAdapter
//
//        }


        reportersDB.addValueEventListener(this)
    }


    override fun onCancelled(p0: DatabaseError) {

    }

    override fun onDataChange(data: DataSnapshot) {
        val list = mutableListOf<SampleClusterItem>()

        data.children.forEach {

            val reporter = it.getValue(Reporter::class.java)
            list.add(SampleClusterItem(reporter!!))
            list.add(SampleClusterItem(reporter))

        }


        mClusterManager.addItems(list)

        mClusterManager.cluster()

    }
}
