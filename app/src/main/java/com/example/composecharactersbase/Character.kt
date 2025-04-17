package com.example.composecharactersbase

data class CharacterModel(
    val id: Int,
    val name: String,
    val gender: String,
    val age: Int
)

data class CharacterResponse(
    val results: List<CharacterModel>
)