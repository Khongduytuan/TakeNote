package com.eagletech.takenote.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

//@Entity(tableName = "notes")
//class Note(
//    val noteTitle: String = "",
//    val noteBody: String = ""
//) : Serializable {
//    @PrimaryKey(autoGenerate = true)
//    var id: Int = 0
//}

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val noteTitle: String,
    val noteBody: String
) : Serializable
