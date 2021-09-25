package com.example.colors.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "colors_table")
data class Element(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "element_name")
    var elementName: String,

    @ColumnInfo(name = "element_color")
    var elementColor: Color
)