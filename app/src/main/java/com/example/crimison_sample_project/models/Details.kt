package com.example.crimison_sample_project.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Details(
    @SerializedName("Actors")
    @Expose
    val Actors: String,
    @SerializedName("Awards")
    @Expose
    val Awards: String,
    @SerializedName("BoxOffice")
    @Expose
    val BoxOffice: String,
    @SerializedName("Country")
    @Expose
    val Country: String,
    @SerializedName("DVD")
    @Expose
    val DVD: String,
    @SerializedName("Director")
    @Expose
    val Director: String,
    @SerializedName("Genre")
    @Expose
    val Genre: String,
    @SerializedName("Language")
    @Expose
    val Language: String,
    @SerializedName("Metascore")
    @Expose
    val Metascore: String,
    @SerializedName("Plot")
    @Expose
    val Plot: String,
    @SerializedName("Poster")
    @Expose
    val Poster: String,
    @SerializedName("Production")
    @Expose
    val Production: String,
    @SerializedName("Rated")
    @Expose
    val Rated: String,
    @SerializedName("Ratings")
    @Expose
    val Ratings: List<Rating>,
    @SerializedName("Released")
    @Expose
    val Released: String,
    @SerializedName("Response")
    @Expose
    val Response: String,
    @SerializedName("Runtime")
    @Expose
    val Runtime: String,
    @SerializedName("Title")
    @Expose
    val Title: String,
    @SerializedName("Type")
    @Expose
    val Type: String,
    @SerializedName("Website")
    @Expose
    val Website: String,
    @SerializedName("Writer")
    @Expose
    val Writer: String,
    @SerializedName("Year")
    @Expose
    val Year: String,
    @SerializedName("imdbID")
    @Expose
    val imdbID: String,
    @SerializedName("imdbRating")
    @Expose
    val imdbRating: String,
    @SerializedName("imdbVotes")
    @Expose
    val imdbVotes: String
)

data class Rating(
    @SerializedName("Source")
    @Expose
    val Source: String,
    @SerializedName("Value")
    @Expose
    val Value: String
)