package com.example.crimison_sample_project.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Search(
    @SerializedName("Response")
    @Expose
    val Response: String,
    @SerializedName("Search")
    @Expose
    val Search: List<SearchX>,
    @SerializedName("totalResults")
    @Expose
    val totalResults: String
)

data class SearchX(
    @SerializedName("Poster")
    @Expose
    val Poster: String,
    @SerializedName("Title")
    @Expose
    val Title: String,
    @SerializedName("Type")
    @Expose
    val Type: String,
    @SerializedName("Year")
    @Expose
    val Year: String,
    @SerializedName("imdbID")
    @Expose
    val imdbID: String
)