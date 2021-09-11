package com.example.pdpappinroom.groupfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pdpappinroom.R
import com.example.pdpappinroom.adapters.ViewAdapter
import com.example.pdpappinroom.classes.Course
import com.example.pdpappinroom.classes.Student
import com.example.pdpappinroom.database.My_DbHelper
import com.example.pdpappinroom.databinding.FragmentGroupsListBinding

class GroupsListFragment : Fragment() {

    lateinit var fraging: FragmentGroupsListBinding
    lateinit var dbHelper: My_DbHelper
    lateinit var list: List<Course>
    lateinit var course: Course
    lateinit var recAdapter: ViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fraging = FragmentGroupsListBinding.inflate(inflater, container, false)
        dbHelper = My_DbHelper.getInstance(requireContext())
        list = ArrayList(dbHelper.roomdao().getAllCourses())

        recAdapter = ViewAdapter(list, object : ViewAdapter.OnClickMyListener {
            override fun onClick(course: Course) {
                val bundle = Bundle()
                bundle.putSerializable("course_group", course)
                findNavController().navigate(R.id.groupFragment, bundle)
            }
        })

        fraging.apply {
            recycle.adapter = recAdapter
            backBtn.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        return fraging.root
    }
}