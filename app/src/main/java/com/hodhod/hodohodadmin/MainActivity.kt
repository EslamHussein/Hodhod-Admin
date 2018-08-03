package com.hodhod.hodohodadmin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.firebase.database.*
import com.google.maps.android.clustering.ClusterManager
import com.hodhod.hodohodadmin.dto.Issue
import com.hodhod.hodohodadmin.dto.Reporter
import com.hodhod.hodohodadmin.dto.SampleClusterItem
import com.hodhod.hodohodadmin.dto.ServiceProviderItem
import kotlinx.android.synthetic.main.activity_maps.*
import com.google.android.gms.maps.CameraUpdateFactory
import android.text.method.TextKeyListener.clear
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), OnMapReadyCallback, ValueEventListener {


    private lateinit var mMap: GoogleMap


    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var database: FirebaseDatabase
    private lateinit var reportersDB: DatabaseReference
    private lateinit var reportsDB: DatabaseReference
    private var reporterList: List<SampleClusterItem> = emptyList()

    private var issuesMarker = mutableMapOf<String, Marker>()
    private lateinit var mClusterManager: ClusterManager<SampleClusterItem>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        viewManager = LinearLayoutManager(this)
        database = FirebaseDatabase.getInstance()
        reportersDB = database.getReference("reporters")
        reportsDB = database.getReference("reports")

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.mapFramgent) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mClusterManager = ClusterManager(this@MainActivity, mMap)

        googleMap.setOnCameraIdleListener(mClusterManager);


        val renderer = CustomClusterRenderer(this, mMap, mClusterManager)

        mClusterManager.renderer = renderer


        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID





        reportersDB.addValueEventListener(this)


        reportsDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {


            }

            override fun onDataChange(data: DataSnapshot) {


                val list = mutableListOf<Issue>()

                data.children.forEach {


                    val issue = it.getValue(Issue::class.java)

                    list.add(issue!!)


                    val markerOptions = MarkerOptions()

                    // Setting the position for the marker
                    markerOptions.position(LatLng(issue.lat, issue.lon))


                    // Clears the previously touched position
                    // Placing a marker on the touched position
                    issuesMarker[it.key.toString()] = googleMap.addMarker(markerOptions)

                }


            }
        })

        reportsDB.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {


            }

            override fun onChildAdded(data: DataSnapshot, p1: String?) {

                val issue = data.getValue(Issue::class.java)
                val markerOptions = MarkerOptions()

                // Setting the position for the marker
                markerOptions.position(LatLng(issue!!.lat, issue.lon))

                issuesMarker[data.key.toString()] = googleMap.addMarker(markerOptions)


            }

            override fun onChildRemoved(data: DataSnapshot) {
                issuesMarker[data.key.toString()]?.remove()
                issuesMarker.remove(data.key.toString())

            }
        })

    }


    override fun onCancelled(p0: DatabaseError) {

    }

    override fun onDataChange(data: DataSnapshot) {
        val list = mutableListOf<SampleClusterItem>()

        data.children.forEach {

            val reporter = it.getValue(Reporter::class.java)
            list.add(SampleClusterItem(reporter!!))
        }

        reporterList = list

        updateReporters(reporterList)
    }


    private fun updateReporters(reportersList: List<SampleClusterItem>) {
        var serviceProviderItems = reportersList.groupBy { it.reporter.speciality }.entries.map {

            ServiceProviderItem(it.key, it.value.size)
        }.filter {
            !it.type.isEmpty()
        }


        serviceProviderTotalNumberTextView.text = " اجمالي العدد ${reportersList.size} متطوع"


        mClusterManager.clearItems()
        mClusterManager.addItems(reportersList)

        mClusterManager.cluster()

        val layout = LinearLayoutManager(this)
        serviceProviderRecyclerView.layoutManager = layout
        serviceProviderRecyclerView.adapter = ServiceProvidersAdapter(serviceProviderItems)


    }
}
