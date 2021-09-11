package com.example.pdpappinroom.kurslar

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import com.example.pdpappinroom.databinding.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CoursFragment : Fragment() {


    lateinit var list: ArrayList<Course>
    lateinit var databace: My_DbHelper
    lateinit var fraging: FragmentCoursBinding
    lateinit var recAdapter: ViewAdapter


    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fraging = FragmentCoursBinding.inflate(inflater, container, false)
        databace = My_DbHelper.getInstance(requireContext())
        list = ArrayList(databace.roomdao().getAllCourses())
        recAdapter = ViewAdapter(list, object : ViewAdapter.OnClickMyListener {
            override fun onClick(course: Course) {
                val bundle = Bundle()
                bundle.putSerializable("course", course)
                findNavController().navigate(R.id.courseAboutFragment, bundle)
            }
        })
        fraging.apply {
            recycle.adapter = recAdapter
        }

        fraging.plus.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext())
            val binding_dialog = DialogAddcoursBinding.inflate(layoutInflater)
            val create = dialog.create() as AlertDialog
            create.setView(binding_dialog.root)

            create.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            binding_dialog.apply {
                accept.setOnClickListener {
                    if (courseName.text.isNotEmpty() && courseAbout.text.isNotEmpty()) {
                        val course = Course(
                            name = courseName.text.toString(),
                            data = courseAbout.text.toString()
                        )
                        databace.roomdao().addCourse(course)
                        val list1 = ArrayList<Course>(databace.roomdao().getAllCourses())
                        list.add(list1[list1.size - 1])
                        recAdapter.notifyItemInserted(list.size)
                    }
                    create.dismiss()
                }

                close.setOnClickListener { create.dismiss() }
            }
            create.show()
        }
        fraging.apply {
            backBtn.setOnClickListener {
                findNavController().popBackStack()
            }
            return fraging.root
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CoursFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}