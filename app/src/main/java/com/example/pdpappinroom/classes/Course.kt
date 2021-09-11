package com.example.pdpappinroom.classes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity
class Course(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "course_id")
    val id: Int = 0,
    @ColumnInfo(name = "course_name")
    val name: String,
    @ColumnInfo(name = "course_desc")
    val data: String

) : Serializable