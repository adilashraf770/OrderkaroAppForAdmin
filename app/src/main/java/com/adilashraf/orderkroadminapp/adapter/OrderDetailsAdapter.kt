package com.adilashraf.orderkroadminapp.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adilashraf.orderkroadminapp.databinding.OrderDetailsLayoutBinding
import com.bumptech.glide.Glide

class OrderDetailsAdapter(
    private val foodNames: ArrayList<String>,
    private val foodImages: ArrayList<String>,
    private val foodPrices: ArrayList<String>,
    val context: Context,
) : RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsViewHolder>() {



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): OrderDetailsAdapter.OrderDetailsViewHolder {
        return OrderDetailsViewHolder(
            OrderDetailsLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: OrderDetailsAdapter.OrderDetailsViewHolder,
        position: Int,
    ) {
        val uriString = foodImages[position]
        val uri = Uri.parse(uriString)
        holder.binding.foodName.text = foodNames[position]
        holder.binding.foodPrice.text = foodPrices[position]
        Glide.with(context).load(uri).into(holder.binding.foodImage)

    }

    override fun getItemCount(): Int = foodNames.size

    inner class OrderDetailsViewHolder(val binding: OrderDetailsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

}