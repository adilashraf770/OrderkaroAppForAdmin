package com.adilashraf.orderkroadminapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.adilashraf.orderkroadminapp.databinding.PendingOrderLayoutBinding
import com.bumptech.glide.Glide

class PendingOrderAdapter(
    private val userNames: MutableList<String>,
    private val foodPrices: MutableList<String>,
    private val foodImages: MutableList<String>,
    private val itemClicked: OnItemClicked,
    private val context: Context,
) :
    RecyclerView.Adapter<PendingOrderAdapter.PendingOrderViewHolder>() {

    interface OnItemClicked {
        fun onItemClickListener(position: Int)
        fun onItemAcceptClickListener(position: Int)
//        fun onItemDispatchClickListener(position: Int)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PendingOrderAdapter.PendingOrderViewHolder {
        return PendingOrderViewHolder(
            PendingOrderLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(
        holder: PendingOrderAdapter.PendingOrderViewHolder,
        position: Int,
    ) {
        val uriString = foodImages[position]
        val uri = Uri.parse(uriString)
        holder.binding.foodName.text = userNames[position]
        holder.binding.foodPrice.text = foodPrices[position]
        Glide.with(context).load(uri).into(holder.binding.img)

        holder.bind(position)
    }

    override fun getItemCount(): Int = userNames.size

    inner class PendingOrderViewHolder(val binding: PendingOrderLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(position: Int) {
            binding.apply {

                btnAccept.apply {

                    setOnClickListener {
                         Toast.makeText(context, "Order is Accepted", Toast.LENGTH_SHORT).show()
                        itemClicked.onItemAcceptClickListener(position)
                        userNames.removeAt(adapterPosition)
                        notifyItemRemoved(adapterPosition)
                        Toast.makeText(context, "Order is Dispatched", Toast.LENGTH_SHORT)
                            .show()
                    }

                }

                itemView.setOnClickListener {
                    itemClicked.onItemClickListener(position)
                }

            }

        }

    }
}