package com.example.greetingcard.repository

import com.example.greetingcard.data.models.PokemonDetail
import com.example.greetingcard.data.models.PokemonListResponse
import com.example.greetingcard.data.services.PokemonApi

class PokemonRepository(private val api: PokemonApi) {
    suspend fun getPokemonList(offset: Int): PokemonListResponse {
        return api.getPokemonList(offset = offset)
    }

    suspend fun getPokemonDetail(name: String): PokemonDetail {
        return api.getPokemonDetail(name)
    }
}