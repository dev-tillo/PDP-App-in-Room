package com.example.pdpappinroom.mentorfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pdpappinroom.R
import com.example.pdpappinroom.adapters.ViewAdapter
import com.example.pdpappinroom.classes.Course
import com.example.pdpappinroom.database.My_DbHelper
import com.example.pdpappinroom.databinding.FragmentMentorListBinding

class MentorListFragment : Fragment() {

    lateinit var fraging: FragmentMentorListBinding
    lateinit var list: ArrayList<Course>
    lateinit var databace: My_DbHelper
    lateinit var recAdapter: ViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fraging = FragmentMentorListBinding.inflate(inflater, container, false)
        databace = My_DbHelper.getInstance(requireContext())
        list = ArrayList(databace.roomdao().getAllCourses())
        recAdapter = ViewAdapter(list, object : ViewAdapter.OnClickMyListener {
            override fun onClick(course: Course) {
                val bundle = Bundle()
                bundle.putSerializable("course_mentor", course)
                findNavController().navigate(R.id.mentorFragment, bundle)
            }
        })
        fraging.apply {
            recycle.adapter = recAdapter
        }
        fraging.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        return fraging.root
    }
}