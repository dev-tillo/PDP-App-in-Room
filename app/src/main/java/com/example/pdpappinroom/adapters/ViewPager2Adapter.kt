package com.example.pdpappinroom.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pdpappinroom.classes.Groups
import com.example.pdpappinroom.database.My_DbHelper
import com.example.pdpappinroom.databinding.ItemGroupPagerBinding

class ViewPager2Adapter(
    var boolean: Boolean,
    var dbHelper: My_DbHelper,
    var list: List<Groups>,
    var listener: OnClickMyListener
) :
    RecyclerView.Adapter<ViewPager2Adapter.VH>() {


    inner class VH(var binding: ItemGroupPagerBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnClickMyListener {
        fun onclickShow(group: Groups, position: Int)
        fun onClickDelete(mentor: Groups, position: Int)
        fun onClickEdit(mentor: Groups, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemGroupPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.apply {
            courseName.text = list[position].name
            lastname.text = "Talabalar soni: ${dbHelper.roomdao().getListStudentById(list[position].id).size}"

            if (!boolean) {
                show.visibility = View.GONE
            }

            edit.setOnClickListener {
                listener.onClickEdit(list[position], position)
            }

            delete.setOnClickListener {
                listener.onClickDelete(list[position], position)
            }

            show.setOnClickListener {
                listener.onclickShow(
                    list[position], dbHelper.roomdao().getListStudentById(list[position].id).size
                )
            }
        }
    }

    override fun getItemCount(): Int = list.size

}