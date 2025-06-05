package com.example.greetingcard.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.CircularProgressIndicator
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.greetingcard.ui.view_model.PokemonViewModel
import coil.compose.rememberAsyncImagePainter
import org.koin.androidx.compose.koinViewModel

@Composable
fun PokemonDetailScreen(
    pokemonName: String,
    viewModel: PokemonViewModel = koinViewModel()
) {
    val pokemonDetail by viewModel.pokemonDetail.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(false)

    if (pokemonDetail == null && !isLoading) {
        viewModel.fetchPokemonDetail(pokemonName)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            pokemonDetail?.let { pokemon ->
                Image(
                    painter = rememberAsyncImagePainter(pokemon.sprites.front_default),
                    contentDescription = pokemon.name,
                    modifier = Modifier.size(200.dp)
                )
                Text(text = pokemon.name.capitalize(), style = MaterialTheme.typography.h4)
                Text(text = "ID: ${pokemon.id}")
                Text(text = "Height: ${pokemon.height / 10.0}m")
                Text(text = "Weight: ${pokemon.weight / 10.0}kg")
                Text(text = "Types: ${pokemon.types.joinToString { it.type.name.capitalize() }}")
            }
        }
    }
}