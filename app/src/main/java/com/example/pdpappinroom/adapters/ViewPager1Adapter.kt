package com.example.pdpappinroom.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pdpappinroom.classes.Mentor
import com.example.pdpappinroom.databinding.ItemGroupPagerBinding

class ViewPager1Adapter(
    var boolean: Boolean,
    var list: List<Mentor>,
    var listener: OnClickMyListener
) :
    RecyclerView.Adapter<ViewPager1Adapter.VH>() {

    inner class VH(var binding: ItemGroupPagerBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnClickMyListener {
        fun onClickDelete(mentor: Mentor, position: Int)
        fun onClickEdit(mentor: Mentor, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemGroupPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.apply {
            courseName.text = "${list[position].name} ${list[position].surname}"
            lastname.text = list[position].lastname

            if (!boolean) {
                show.visibility = View.GONE
            }

            edit.setOnClickListener {
                listener.onClickEdit(list[position], position)
            }

            delete.setOnClickListener {
                listener.onClickDelete(list[position], position)
            }
        }
    }

    override fun getItemCount(): Int = list.size
}