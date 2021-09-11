package com.example.pdpappinroom.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.pdpappinroom.databinding.ItemSpinnerBinding

class Spin_Time_Adapter (var list: List<String>): BaseAdapter(){

    override fun getCount(): Int  = list.size

    override fun getItem(position: Int): String = list[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ItemSpinnerBinding = if (convertView == null) {
            ItemSpinnerBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        } else {
            ItemSpinnerBinding.bind(convertView)
        }

        binding.txt.text = list[position]

        return  binding.root
    }
}