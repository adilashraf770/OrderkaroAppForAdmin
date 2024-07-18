package com.adilashraf.orderkroadminapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adilashraf.orderkroadminapp.MenuItemActivity
import com.adilashraf.orderkroadminapp.databinding.AllItemMenuLayoutBinding
import com.adilashraf.orderkroadminapp.model.AddItemsModel
import com.bumptech.glide.Glide

class AllItemsMenuAdapter(
    private val itemsList: List<AddItemsModel>,
    val context: Context,
) :
    RecyclerView.Adapter<AllItemsMenuAdapter.AllItemMnuViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): AllItemMnuViewHolder {
        val av =
            AllItemMenuLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllItemMnuViewHolder(av)
    }

    override fun onBindViewHolder(holder: AllItemMnuViewHolder, position: Int) {

        holder.bind(position, context)
        val menuItem = itemsList[position]
        holder.binding.allItemsMenuCard.setOnClickListener {
            val i = Intent(context, MenuItemActivity::class.java ).apply {
                putExtra("itemName", menuItem.itemName)
                putExtra("itemPrice", menuItem.itemPrice)
                putExtra("itemIngredients", menuItem.itemIngredients)
                putExtra("itemImage", menuItem.itemImage)
                putExtra("itemDescription", menuItem.itemDescription)
            }
            context.startActivity(i)
        }

    }

    override fun getItemCount(): Int = itemsList.size

    inner class AllItemMnuViewHolder(val binding: AllItemMenuLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("NotifyDataSetChanged")
        fun bind(position: Int, context: Context) {
            val pos = itemsList[position]
            val uriString = pos.itemImage.toString()
            val uri = Uri.parse(uriString)
            binding.apply {
                foodName.text = pos.itemName
                foodPrice.text = pos.itemPrice.toString()
                Glide.with(context).load(uri).into(cartImg)


            }

        }
    }
}