package com.adilashraf.orderkroadminapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adilashraf.orderkroadminapp.databinding.OrderDispatchLayoutBinding

class OrderDispatchAdapter(
    val userNames: MutableList<String>,
    val moneyStatus: MutableList<Boolean>,
    val context: Context
) :
    RecyclerView.Adapter<OrderDispatchAdapter.OrderDispatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDispatchViewHolder {
        return OrderDispatchViewHolder(
            OrderDispatchLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = userNames.size


    override fun onBindViewHolder(holder: OrderDispatchViewHolder, position: Int) {

        holder.bind(position)
    }

    inner class OrderDispatchViewHolder(val binding: OrderDispatchLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(pos: Int) {
            binding.apply {
                customerName.text = userNames[pos]
                if (moneyStatus[pos]){
                    status.text =  "Received"
                }else{
                    status.text =  "Not Received"
                }
                val colorMap = mapOf(
                    true to Color.GREEN, false to Color.RED,
                )

                status.setTextColor(colorMap[moneyStatus[pos]]!!)
                btn.backgroundTintList = ColorStateList.valueOf(colorMap[moneyStatus[pos]]!!)


            }

        }


    }


}