package com.example.mercadonaulisespt.model

import com.google.gson.annotations.SerializedName

class AllCharacters {

    @SerializedName("results")
    lateinit var characterResults: List<ResultsCharacters>
}

data class ResultsCharacters(
    val id: Int,
    val name: String,
    val image: String,
    val status: String,
    val species: String,
    val gender: String
)