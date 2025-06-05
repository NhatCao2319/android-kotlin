package com.example.greetingcard.data.models

data class PokemonListResponse(
    val results: List<PokemonListItem>
)

data class PokemonListItem(
    val name: String,
    val url: String
)

data class PokemonDetail(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<PokemonType>,
    val sprites: PokemonSprites
)

data class PokemonType(
    val type: TypeDetail
)

data class TypeDetail(
    val name: String
)

data class PokemonSprites(
    val front_default: String
)