package com.dicoding.habitapp.data

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

//TODO 1 : Define a local database table using the schema in app/schema/habits.json
@Parcelize
@Entity(tableName = "habits")
data class Habit(
    @field:PrimaryKey(autoGenerate = true)
    @NonNull
    @field:ColumnInfo(name = "id")
    val id: Int = 0,

    @NonNull
    @field:ColumnInfo(name = "title")
    val title: String,

    @NonNull
    @field:ColumnInfo(name = "minutesFocus")
    val minutesFocus: Long,

    @NonNull
    @field:ColumnInfo(name = "startTime")
    val startTime: String,

    @NonNull
    @field:ColumnInfo(name = "priorityLevel")
    val priorityLevel: String
): Parcelable
