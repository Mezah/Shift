package com.hazem.homebase.shiftapp.listscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hazem.homebase.shiftapp.databinding.ItemShiftListBinding
import com.hazem.homebase.shifts.models.ShiftViewModel

class ShiftsListAdapter : RecyclerView.Adapter<ShiftsListViewHolder>() {

    private val shifts: MutableList<ShiftViewModel> = mutableListOf()

    fun addShiftList(list: List<ShiftViewModel>) {
        shifts.clear()
        shifts.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShiftsListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemShiftListBinding.inflate(inflater, parent, false)
        return ShiftsListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShiftsListViewHolder, position: Int) {
        val item = shifts[position]
        holder.bindData(item)
    }

    override fun getItemCount(): Int = shifts.size

}

class ShiftsListViewHolder(private val binding: ItemShiftListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindData(shiftViewModel: ShiftViewModel) {

    }
}