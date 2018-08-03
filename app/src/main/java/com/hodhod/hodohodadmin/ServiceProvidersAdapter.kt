package com.hodhod.hodohodadmin

import android.widget.CheckBox
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.hodhod.hodohodadmin.dto.ServiceProviderItem


/**
 * Created by Eslam Hussein on 8/2/18.
 */
class ServiceProvidersAdapter(private val items: List<ServiceProviderItem> = emptyList()) : RecyclerView.Adapter<ServiceProvidersAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {


        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.service_provider_item, parent, false)
        return ViewHolder(view)


    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items.get(position)

        holder.serviceNameTextView.text = item.getProviderType()

        holder.serviceNumberTextView.text = item.getProviderNumber()

        holder.serviceProviderImageView.setImageResource(item.getIcon())
    }


    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var serviceNameTextView: TextView

        var serviceNumberTextView: TextView
        var serviceProviderImageView: ImageView

        init {
            serviceNameTextView = v.findViewById(R.id.serviceNameTextView)
            serviceNumberTextView = v.findViewById(R.id.serviceNumberTextView)
            serviceProviderImageView = v.findViewById(R.id.serviceProviderImageView)
        }
    }
}