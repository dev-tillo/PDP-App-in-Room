package com.example.pdpappinroom.groupfragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.example.pdpappinroom.R
import com.example.pdpappinroom.adapters.ViewPagerAdapter
import com.example.pdpappinroom.classes.Course
import com.example.pdpappinroom.database.My_DbHelper
import com.example.pdpappinroom.databinding.FragmentGroupBinding

class GroupFragment : Fragment() {

    lateinit var fraging: FragmentGroupBinding
    lateinit var course: Course
    lateinit var dbHelper: My_DbHelper
    lateinit var pagerAdapter: ViewPagerAdapter
    lateinit var list: List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            course = it.getSerializable("course_group") as Course
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fraging = FragmentGroupBinding.inflate(inflater, container, false)
        dbHelper = My_DbHelper.getInstance(requireContext())
        list = listOf(1, -1)
        pagerAdapter =
            ViewPagerAdapter(childFragmentManager, list, course.id)

        fraging.apply {
            viewPager.adapter = pagerAdapter
            backBtn.setOnClickListener { findNavController().popBackStack() }
            tabLayout.setupWithViewPager(viewPager)
            tabLayout.setTabTextColors(Color.WHITE, Color.RED)
            backBtn.setOnClickListener {
                findNavController().popBackStack()
            }
            viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    if (position == 0) {
                        plus.visibility = View.INVISIBLE
                    } else {
                        plus.visibility = View.VISIBLE
                    }
                }

                override fun onPageScrollStateChanged(state: Int) {
                }
            })

            plus.setOnClickListener {
                if (dbHelper.roomdao().getListMentorById(course.id).isNotEmpty()) {
                    val bundle = Bundle()
                    bundle.putSerializable("course_group", course)
                    findNavController().navigate(R.id.groupAddFragment, bundle)
                } else {
                    Toast.makeText(requireContext(), "Mentor qo`shilmagan", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        return fraging.root
    }

    override fun onResume() {
        super.onResume()
        if (fraging.viewPager.currentItem == 1)
            fraging.plus.visibility = View.VISIBLE
    }
}