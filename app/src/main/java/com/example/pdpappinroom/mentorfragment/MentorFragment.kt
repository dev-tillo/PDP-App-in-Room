package com.example.pdpappinroom.mentorfragment

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.pdpappinroom.R
import com.example.pdpappinroom.adapters.ViewPager1Adapter
import com.example.pdpappinroom.classes.Course
import com.example.pdpappinroom.classes.Mentor
import com.example.pdpappinroom.database.My_DbHelper
import com.example.pdpappinroom.databinding.DialogMentorBinding
import com.example.pdpappinroom.databinding.FragmentMentorBinding


class MentorFragment : Fragment() {

    lateinit var fraging: FragmentMentorBinding

    lateinit var dbHelper: My_DbHelper
    lateinit var list: ArrayList<Mentor>
    lateinit var course: Course
    lateinit var recAdapter1: ViewPager1Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            course = it.getSerializable("course_mentor") as Course
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fraging = FragmentMentorBinding.inflate(inflater, container, false)
        dbHelper = My_DbHelper.getInstance(requireContext())
        list = ArrayList(dbHelper.roomdao().getMentors())

        val filter = list.filter {
            it.course_id == course.id
        }
        list = ArrayList(filter)

        fraging.apply {
            plus.setOnClickListener {
                val bundle = Bundle()
                bundle.putSerializable("course_add_mentor", course)
                findNavController().navigate(R.id.mentorAddFragment, bundle)
            }
            backBtn.setOnClickListener {
                findNavController().popBackStack()
            }
            recAdapter1 =
                ViewPager1Adapter(false, list, object : ViewPager1Adapter.OnClickMyListener {
                    override fun onClickDelete(mentor: Mentor, position: Int) {
                        dbHelper.roomdao().deleteMentor(list[position])
                        list.removeAt(position)
                        recAdapter1.notifyItemRemoved(position)
                        if (list.size > 0) {
                            recAdapter1.notifyItemRangeChanged(position, list.size)
                        }
                    }

                    override fun onClickEdit(mentor: Mentor, position: Int) {
                        val dialog = AlertDialog.Builder(requireContext())
                        val binding_dialog = DialogMentorBinding.inflate(layoutInflater)
                        val create = dialog.create()
                        create.setView(binding_dialog.root)

                        create.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                        binding_dialog.apply {

                            lastname.setText(mentor.surname)
                            nameEdit.setText(mentor.name)
                            name.setText(mentor.lastname)

                            accept.setOnClickListener {
                                if (lastname.text.isNotEmpty() && nameEdit.text.isNotEmpty() && name.text.isNotEmpty()) {
                                    val mentor1 = Mentor(
                                        course_id = mentor.id,
                                        name = nameEdit.text.toString(),
                                        surname = lastname.text.toString(),
                                        lastname = name.text.toString())
                                    dbHelper.roomdao().editMentor(mentor1)
                                    list[position] = mentor1
                                    recAdapter1.notifyItemChanged(position)
                                }
                                create.dismiss()
                            }
                            close.setOnClickListener { create.dismiss() }
                        }
                        create.show()
                    }
                })
            recycle.adapter = recAdapter1
            recycle.setDivider(R.drawable.bottom_line)
        }
        return fraging.root
    }

    fun RecyclerView.setDivider(@DrawableRes drawableRes: Int) {
        val divider = DividerItemDecoration(
            this.context,
            DividerItemDecoration.VERTICAL
        )
        val drawable = ContextCompat.getDrawable(
            this.context,
            drawableRes
        )
        drawable?.let {
            divider.setDrawable(it)
            addItemDecoration(divider)
        }
    }
}