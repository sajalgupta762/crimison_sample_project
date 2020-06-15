package com.example.crimison_sample_project.DataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchXDB (

  @PrimaryKey val imdbID: String,
  @ColumnInfo(name = "Type") val Type: String?,
  @ColumnInfo(name = "year") val Year: String?,
  @ColumnInfo(name = "pageId") val pageId: String?,
  @ColumnInfo(name = "Poster") val Poster: String?


)





