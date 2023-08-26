package com.utad.utadonline_pmdm_ud3_gestiondedatos.room.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.utad.utadonline_pmdm_ud3_gestiondedatos.databinding.ItemClockInBinding
import com.utad.utadonline_pmdm_ud3_gestiondedatos.room.entities.ClockIn

class ClockInRelationAdapter :
    ListAdapter<ClockIn, ClockInRelationAdapter.ClockInViewHolder>(ClockInCallBack) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClockInViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemClockInBinding.inflate(inflater, parent, false)
        return ClockInViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClockInViewHolder, position: Int) {
        val clockIn = getItem(position)
        holder.binding.tvClockIn.text = clockIn.time
    }


    inner class ClockInViewHolder(val binding: ItemClockInBinding) :
        RecyclerView.ViewHolder(binding.root)

}

object ClockInCallBack : DiffUtil.ItemCallback<ClockIn>() {
    override fun areItemsTheSame(oldItem: ClockIn, newItem: ClockIn): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ClockIn, newItem: ClockIn): Boolean {
        return oldItem == newItem
    }

}