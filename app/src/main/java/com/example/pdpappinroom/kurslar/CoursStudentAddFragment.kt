package com.example.pdpappinroom.kurslar

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.pdpappinroom.R
import com.example.pdpappinroom.adapters.Spin_Adapter_Group
import com.example.pdpappinroom.classes.Course
import com.example.pdpappinroom.classes.Groups
import com.example.pdpappinroom.classes.Student
import com.example.pdpappinroom.database.My_DbHelper
import com.example.pdpappinroom.databinding.FragmentCoursAddBinding

class CoursAddFragment : Fragment() {

    lateinit var fraging: FragmentCoursAddBinding
    lateinit var course: Course
    lateinit var student: Student
    lateinit var dbHelper: My_DbHelper
    lateinit var grouplist: List<Groups>
    lateinit var spinnerAdapterGroup: Spin_Adapter_Group
    private var boolean: Boolean? = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            course = it.getSerializable("course_add") as Course
            student = it.getSerializable("student") as Student
            boolean = it.getBoolean("boolean")
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fraging = FragmentCoursAddBinding.inflate(inflater, container, false)
        dbHelper = My_DbHelper.getInstance(requireContext())
        grouplist = ArrayList(dbHelper.roomdao().getListIsOpenGroup_courseID(-1, course.id))

        if (boolean!!) {
            spinnerAdapterGroup = Spin_Adapter_Group(grouplist)
            fraging.spinGroupEdit.adapter = spinnerAdapterGroup
        }

        fraging.apply {

            layoutDate.setOnClickListener {
                val dialog = DatePickerDialog(
                    requireContext(),
                    { _, year, month, dayOfMonth -> date.text = "$dayOfMonth/$month/$year" },
                    2021,
                    8,
                    19
                )

                dialog.show()
            }

            backBtn.setOnClickListener { findNavController().popBackStack() }

            if (!boolean!!) {
                spinCourse.visibility = View.GONE
                if (student.id != -1) {
                    lastname.setText(student.surname)
                    name.setText(student.name)
                    fatherName.setText(student.lastname)
                    date.text = student.time
                }
            }

            save.setOnClickListener {

                if (!boolean!! && student.id != -1) {
                    val surname = lastname.text.toString()
                    val name = name.text.toString()
                    val lastname = fatherName.text.toString()
                    val date = date.text.toString()

                    if (surname.isNotEmpty() && name.isNotEmpty() && lastname.isNotEmpty() && date.isNotEmpty()) {
                        val student1 = Student(
                            id = student.id,
                            name = name,
                            surname = surname,
                            lastname = lastname,
                            time = date,
                            group_id = student.group_id,
                            mentor_id_student = student.mentor_id_student
                        )
                        dbHelper.roomdao().editStudent(student1)
                        findNavController().popBackStack()
                    } else Toast.makeText(
                        requireContext(),
                        "Maydon bo`sh",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (!boolean!!) {
                    val surname = lastname.text.toString()
                    val name = name.text.toString()
                    val lastname = fatherName.text.toString()
                    val date = date.text.toString()

                    if (surname.isNotEmpty() && name.isNotEmpty() && lastname.isNotEmpty() && date.isNotEmpty()) {
                        val student = Student(
                            name = name,
                            surname = surname,
                            lastname = lastname,
                            time = date,
                            group_id = student.group_id,
                            mentor_id_student = student.mentor_id_student
                        )
                        dbHelper.roomdao().addStudent(student)
                        findNavController().popBackStack()
                    } else Toast.makeText(
                        requireContext(),
                        "Maydon bo`sh",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {

                    if (grouplist.isEmpty()) {
                        Toast.makeText(requireContext(), "Guruh qo`shilmagan", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        val surname = lastname.text.toString()
                        val name = name.text.toString()
                        val lastname = fatherName.text.toString()
                        val date = date.text.toString()
                        val group = grouplist[spinGroupEdit.selectedItemPosition]

                        if (surname.isNotEmpty() && name.isNotEmpty() && lastname.isNotEmpty() && date.isNotEmpty() && group.name.isNotEmpty()) {
                            val mentor = group.mentor
                            val student = Student(
                                name = name,
                                surname = surname,
                                lastname = lastname,
                                time = date,
                                group_id = group.id,
                                mentor_id_student = mentor
                            )
                            dbHelper.roomdao().addStudent(student)
                            findNavController().popBackStack(R.id.coursFragment, false)
                        } else Toast.makeText(
                            requireContext(),
                            "Maydon bo`sh",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            return fraging.root
        }
    }
}