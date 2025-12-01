package com.example.mobilkalaba1

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("results") val results: List<PokemonDto>
)

data class PokemonDto(
    val name: String,
    val url: String
)