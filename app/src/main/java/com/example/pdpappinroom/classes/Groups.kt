package com.example.pdpappinroom.classes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(
    foreignKeys = [ForeignKey(
        parentColumns = ["mentor_id"],
        childColumns = ["group_mentor"],
        onDelete = ForeignKey.CASCADE,
        entity = Mentor::class
    )]
)
class Groups(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "group_id")
    val id: Int = 0,
    @ColumnInfo(name = "group_mentor")
    var mentor: Int,
    @ColumnInfo(name = "group_isopen")
    var isOpen: Int,
    @ColumnInfo(name = "group_name")
    var name: String,
    @ColumnInfo(name = "group_data")
    var date: String,
    @ColumnInfo(name = "group_time")
    var time: String

) : Serializable