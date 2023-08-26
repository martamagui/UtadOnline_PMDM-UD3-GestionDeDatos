package com.utad.utadonline_pmdm_ud3_gestiondedatos.room.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.utad.utadonline_pmdm_ud3_gestiondedatos.databinding.ItemEmployeeBinding
import com.utad.utadonline_pmdm_ud3_gestiondedatos.room.entities.Employee

class EmployeeListAdapter: ListAdapter<Employee, EmployeeListAdapter.EmployeeViewHolder> (EmployeeItemCallBack) {
    inner class EmployeeViewHolder(val binding: ItemEmployeeBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemEmployeeBinding.inflate(inflater, parent, false)
        return EmployeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val item: Employee = getItem(position)
        holder.binding.tvEmployeeName.text = item.name
        holder.binding.tvEmployeeJob.text =item.job
        holder.binding.tvVacationInfo.text = "Ha gastado ${item.vacationInfo.daysAlreadyTaken} y le quedan ${item.vacationInfo.daysLeft} días de vacaciones."
        holder.binding.ivEmployeePhoto.setImageBitmap(item.image)
    }

}

object EmployeeItemCallBack: DiffUtil.ItemCallback<Employee>(){
    override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
        return oldItem.name == newItem.name
    }
}