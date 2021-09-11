package com.example.pdpappinroom.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pdpappinroom.classes.Course
import com.example.pdpappinroom.classes.Groups
import com.example.pdpappinroom.classes.Mentor
import com.example.pdpappinroom.classes.Student

@Database(entities = [Course::class, Groups::class, Mentor::class, Student::class], version = 1)
abstract class My_DbHelper : RoomDatabase() {

    abstract fun roomdao(): RoomAppDao

    companion object {
        private var appDatabase: My_DbHelper? = null

        @Synchronized
        fun getInstance(context: Context): My_DbHelper {
            if (appDatabase == null) {
                appDatabase =
                    Room.databaseBuilder(context, My_DbHelper::class.java, "pdp_db")
                        .allowMainThreadQueries()
                        .build()
            }
            return appDatabase!!
        }
    }
}