package com.example.composecharactersbase

import com.example.composecharactersbase.CharacterResponse
import retrofit2.http.GET

interface ApiService {
    @GET("people")
    suspend fun getCharacters(): CharacterResponse
}