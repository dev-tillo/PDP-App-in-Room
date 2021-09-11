package com.example.pdpappinroom.groupfragment

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
import com.example.pdpappinroom.adapters.Spin_Mentor_Adapter
import com.example.pdpappinroom.adapters.Spin_Time_Adapter
import com.example.pdpappinroom.adapters.ViewPager2Adapter
import com.example.pdpappinroom.classes.Groups
import com.example.pdpappinroom.database.My_DbHelper
import com.example.pdpappinroom.databinding.FragmentGroupItemPagerBinding
import com.example.pdpappinroom.databinding.GroupItemBinding
import com.example.pdpappinroom.databinding.ItemDeletBinding

private const val ARG_PARAM1 = "param1"

class GroupItemPagerFragment : Fragment() {

    lateinit var fraging: FragmentGroupItemPagerBinding
    lateinit var dbHelper: My_DbHelper
    lateinit var list: ArrayList<Groups>
    lateinit var recAdapter2: ViewPager2Adapter
    lateinit var spinnerAdapterMentor: Spin_Mentor_Adapter
    lateinit var spinnerAdapterTime: Spin_Time_Adapter

    private var n: Int? = null
    private var c: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            n = it.getInt(ARG_PARAM1)
            c = it.getInt("id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fraging = FragmentGroupItemPagerBinding.inflate(inflater, container, false)
        dbHelper = My_DbHelper.getInstance(requireContext())
        list = ArrayList(dbHelper.roomdao().getListIsOpenGroup_courseID(n!!, c!!))
        fraging.apply {

            recAdapter2 =
                ViewPager2Adapter(
                    true,
                    dbHelper,
                    list,
                    object : ViewPager2Adapter.OnClickMyListener {
                        override fun onclickShow(group: Groups, position: Int) {
                            val bundle = Bundle()
                            bundle.putSerializable("group", group)
                            bundle.putInt("size", position)
                            findNavController().navigate(R.id.groupAboutFragment, bundle)
                        }

                        override fun onClickDelete(mentor: Groups, position: Int) {
                            val dialog = AlertDialog.Builder(requireContext())
                            val bindingDialog = ItemDeletBinding.inflate(layoutInflater)
                            val create = dialog.create()
                            create.setView(bindingDialog.root)
                            bindingDialog.apply {
                                accept.setOnClickListener {
                                    dbHelper.roomdao().deleteGroup(mentor)
                                    list.removeAt(position)
                                    recAdapter2.notifyItemRemoved(position)
                                    recAdapter2.notifyItemChanged(position)
                                    create.dismiss()
                                }
                                close.setOnClickListener { create.dismiss() }
                            }
                            create.show()
                        }

                        override fun onClickEdit(mentor: Groups, position: Int) {
                            val dialog = AlertDialog.Builder(requireContext())
                            val binding_dialog = GroupItemBinding.inflate(layoutInflater)
                            val create = dialog.create()
                            create.setView(binding_dialog.root)

                            create.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                            val times =
                                listOf("9:00-11:00", "14:00-16:00", "16:00-18:00", "19:00-21:00")
                            val mentors = dbHelper.roomdao().getListMentorById(c!!)
                            var index = -1
                            mentors.forEachIndexed { i, m ->
                                if (mentor.mentor.equals(m)) {
                                    index = i
                                }
                            }

                            var index_time = -1
                            times.forEachIndexed { i, t ->
                                if (mentor.time == t) {
                                    index_time = i
                                }
                            }

                            spinnerAdapterMentor = Spin_Mentor_Adapter(mentors)
                            spinnerAdapterTime = Spin_Time_Adapter(times)
                            binding_dialog.apply {

                                spinMentorEdit.adapter = spinnerAdapterMentor
                                spinMentorEdit.setSelection(index)
                                spinGroupTime.adapter = spinnerAdapterTime
                                spinGroupTime.setSelection(index_time)
                                lastname.setText(mentor.name)

                                accept.setOnClickListener {
                                    val mentor_name =
                                        mentors[spinMentorEdit.selectedItemPosition].name
                                    val grup_time = times[spinGroupTime.selectedItemPosition]
                                    if (lastname.text.isNotEmpty() && mentor_name.isNotEmpty() && grup_time.isNotEmpty()) {
                                        val course1 = Groups(
                                            mentor.id,
                                            name = lastname.text.toString(),
                                            isOpen = mentor.isOpen,
                                            date = mentor.date,
                                            time = grup_time,
                                            mentor = mentor.mentor
                                        )
                                        dbHelper.roomdao().editGroup(course1)
                                        list[position] = course1
                                        recAdapter2.notifyItemChanged(position)
                                    }
                                    create.dismiss()
                                }
                                close.setOnClickListener { create.dismiss() }
                            }
                            create.show()
                        }

                    })
            fraging.apply {
                recycle.adapter = recAdapter2
                recycle.setDivider(R.drawable.bottom_line)
            }

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

    companion object {
        fun newInstance(n: Int, c: Int) =
            GroupItemPagerFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, n)
                    putInt("id", c)
                }
            }
    }
}