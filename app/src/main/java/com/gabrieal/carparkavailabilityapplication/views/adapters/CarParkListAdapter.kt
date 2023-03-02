package com.gabrieal.carparkavailabilityapplication.views.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gabrieal.carparkavailabilityapplication.R
import com.gabrieal.carparkavailabilityapplication.models.dataModels.CarParkCategoryModel
import com.google.gson.Gson

class CarParkListAdapter(private val mList: ArrayList<CarParkCategoryModel>?) :
    RecyclerView.Adapter<CarParkListAdapter.ViewHolder>() {

    var highest: String? = ""
    var lowest: String? = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_carpark_list_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        highest = ""
        lowest = ""

        val itemsViewModel = mList?.get(position)
        holder.tvCategory.text = itemsViewModel?.category

        itemsViewModel?.categoryItems?.forEach {
            if (it.lotsAvailable == itemsViewModel.categoryItems?.first()?.lotsAvailable) {
                lowest += if (lowest.isNullOrEmpty()) it.carParkNumber
                else holder.itemView.context.getString(R.string.comma) + it.carParkNumber
            }
            if (it.lotsAvailable == itemsViewModel.categoryItems?.last()?.lotsAvailable) {
                highest += if (highest.isNullOrEmpty()) it.carParkNumber
                else holder.itemView.context.getString(R.string.comma) + it.carParkNumber
            }
        }

        holder.tvHighestTitle.text =
            holder.itemView.context.getString(
                R.string.highest_lot_available,
                itemsViewModel?.categoryItems?.last()?.lotsAvailable
            )

        holder.tvHighestDesc.text = highest

        holder.tvLowestTitle.text = holder.itemView.context.getString(
            R.string.lowest_lot_available,
            itemsViewModel?.categoryItems?.first()?.lotsAvailable
        )

        holder.tvLowestDesc.text = lowest
    }

    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        val tvHighestTitle: TextView = itemView.findViewById(R.id.tvHighestTitle)
        val tvHighestDesc: TextView = itemView.findViewById(R.id.tvHighestDesc)
        val tvLowestTitle: TextView = itemView.findViewById(R.id.tvLowestTitle)
        val tvLowestDesc: TextView = itemView.findViewById(R.id.tvLowestDesc)
    }
}