package com.example.pdpappinroom.classes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(
    foreignKeys = [ForeignKey(
        parentColumns = ["course_id"],
        childColumns = ["mentor_course_id"],
        onDelete = CASCADE,
        entity = Course::class
    )]
)
class Mentor(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "mentor_id")
    val id: Int = 0,
    @ColumnInfo(name = "mentor_course_id")
    var course_id: Int,
    @ColumnInfo(name = "mentor_name")
    var name: String,
    @ColumnInfo(name = "mentor_surname")
    var surname: String,
    @ColumnInfo(name = "mentor_lastname")
    var lastname: String

) : Serializable