package com.example.pdpappinroom.classes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(
    foreignKeys = [ForeignKey(
        parentColumns = ["mentor_id"], childColumns = ["studnet_mentor_id"], onDelete = CASCADE,
        entity = Mentor::class
    ), ForeignKey(
        parentColumns = ["group_id"], childColumns = ["studnet_group_id"], onDelete = CASCADE,
        entity = Groups::class
    )]
)
class Student(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "student_id")
    var id: Int = 0,
    @ColumnInfo(name = "stuent_name")
    var name: String,
    @ColumnInfo(name = "student_surnam")
    var surname: String,
    @ColumnInfo(name = "student_last")
    var lastname: String,
    @ColumnInfo(name = "student_time")
    var time: String,
    @ColumnInfo(name = "studnet_group_id")
    var group_id: Int,
    @ColumnInfo(name = "studnet_mentor_id")
    var mentor_id_student: Int

) : Serializable