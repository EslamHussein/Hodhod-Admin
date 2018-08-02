package com.hodhod.hodohodadmin

import android.widget.CheckBox
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * Created by Eslam Hussein on 8/2/18.
 */
class ServiceProvidersAdapter : RecyclerView.Adapter<ServiceProvidersAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {


        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.service_provider_item, parent, false)
        return ViewHolder(view)


    }

    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

    }


    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var serviceNameTextView: TextView
//        var cbSelect: CheckBox

        init {
            serviceNameTextView = v.findViewById(R.id.serviceNameTextView)
//            cbSelect = v.findViewById(R.id.cbSelect)
        }
    }
}