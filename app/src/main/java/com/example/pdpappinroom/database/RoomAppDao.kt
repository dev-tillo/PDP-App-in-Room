package com.example.pdpappinroom.database

import androidx.room.*
import com.example.pdpappinroom.classes.Course
import com.example.pdpappinroom.classes.Groups
import com.example.pdpappinroom.classes.Mentor
import com.example.pdpappinroom.classes.Student

@Dao
interface RoomAppDao {

    @Insert
    fun addCourse(course: Course)

    @Insert
    fun addGroup(groups: Groups)

    @Insert
    fun addMentor(mentor: Mentor)

    @Insert
    fun addStudent(student: Student)

    @Update
    fun editGroup(groups: Groups)

    @Update
    fun editMentor(mentor: Mentor)

    @Update
    fun editStudent(student: Student)

    @Delete
    fun deleteGroup(groups: Groups)

    @Delete
    fun deleteMentor(mentor: Mentor)

    @Delete
    fun deleteStudent(student: Student)

    @Query("select * from course")
    fun getAllCourses(): List<Course>

    @Query("select * from groups")
    fun getGroups(): List<Groups>

    @Query("select *from mentor")
    fun getMentors(): List<Mentor>

    @Query("select *from student")
    fun getAllStudents(): List<Student>

    @Query("select *from course where course_id=:id")
    fun getCourceById(id: Int): Course

    @Query("select *from mentor where mentor_id=:id")
    fun getMentorById(id: Int): Mentor

    @Query("select * from groups where group_id=:id")
    fun getGroupById(id: Int): Groups

    @Query("select *from student where student_id=:id")
    fun getStudentById(id: Int): Student

    @Query("select group_id, group_name, group_isopen, group_data, group_time, group_mentor from groups INNER JOIN mentor on group_mentor = mentor_id where mentor_course_id =:c and group_isopen =:n")
    fun getListIsOpenGroup_courseID(n: Int, c: Int): List<Groups>

    @Query("select * from mentor where mentor_course_id=:id")
    fun getListMentorById(id: Int): List<Mentor>

    @Query("select *from student where studnet_group_id=:n")
    fun getListStudentById(n: Int): List<Student>

    @Query("select *from course where course_id=:course_id")
    fun getByIdCourse(course_id: Int): Course

    @Query("select *from mentor where mentor_id=:n")
    fun getByIdMentor(n: Int): Mentor

    @Query("select *from groups where group_isopen=:n")
    fun getLisIsOpenGroup(n: Int): List<Groups>

}