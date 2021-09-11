package com.example.pdpappinroom.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pdpappinroom.classes.Course
import com.example.pdpappinroom.databinding.ItemCoursBinding

class ViewAdapter(var list: List<Course>, var listener: OnClickMyListener) :
    RecyclerView.Adapter<ViewAdapter.VH>() {

    inner class VH(var binding: ItemCoursBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnClickMyListener {
        fun onClick(course: Course)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemCoursBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.apply {
            courseName.text = list[position].name

            root.setOnClickListener {
                listener.onClick(list[position])
            }
        }
    }

    override fun getItemCount(): Int = list.size

}