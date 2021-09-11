package com.example.pdpappinroom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pdpappinroom.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var fraging: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fraging = FragmentHomeBinding.inflate(inflater, container, false)
        fraging.apply {
            course.setOnClickListener {
                findNavController().navigate(R.id.coursFragment)
            }
            group.setOnClickListener {
                findNavController().navigate(R.id.groupsListFragment)
            }
            mentor.setOnClickListener {
                findNavController().navigate(R.id.mentorListFragment)
            }
        }
        return fraging.root
    }
}