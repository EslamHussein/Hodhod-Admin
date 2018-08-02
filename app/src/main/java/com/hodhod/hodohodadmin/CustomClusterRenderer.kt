package com.hodhod.hodohodadmin

import android.content.Context
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.hodhod.hodohodadmin.dto.SampleClusterItem
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory


/**
 * Created by Eslam Hussein on 8/3/18.
 */
class CustomClusterRenderer(private val mContext: Context, map: GoogleMap,
                            clusterManager: ClusterManager<SampleClusterItem>) : DefaultClusterRenderer<SampleClusterItem>(mContext, map, clusterManager) {

    override fun onBeforeClusterItemRendered(item: SampleClusterItem?,
                                             markerOptions: MarkerOptions?) {


        when (item!!.reporter.speciality) {
            "food" -> {
                markerOptions?.icon(BitmapDescriptorFactory.fromResource(R.drawable.food))

            }

            "health" -> {
                markerOptions?.icon(BitmapDescriptorFactory.fromResource(R.drawable.health))

            }
            else -> {
            }
        }


    }
}
