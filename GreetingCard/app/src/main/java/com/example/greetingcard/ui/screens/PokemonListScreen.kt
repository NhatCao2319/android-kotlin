package com.example.greetingcard.ui.screens

//noinspection UsingMaterialAndMaterial3Libraries
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.CircularProgressIndicator
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.greetingcard.data.models.PokemonListItem
import com.example.greetingcard.ui.view_model.PokemonViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun PokemonListScreen(
    navController: NavController,
    viewModel: PokemonViewModel = koinViewModel()
) {
    val pokemonList by viewModel.pokemonList.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val listState = rememberLazyListState()

    // Detect when the user scrolls to the end of the list
    val reachedEnd by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem != null && lastVisibleItem.index >= pokemonList.size - 2
        }
    }

    // Trigger fetchPokemonList when reaching the end and not loading
    LaunchedEffect(reachedEnd) {
        if (reachedEnd) {
            Log.d("PokemonListScreen", "Reached end of list")
            if (!isLoading) {
                viewModel.fetchPokemonList()
            }
        }
    }

    Column(modifier = Modifier.padding(32.dp)) {
        LazyColumn(state = listState) {
            items(pokemonList) { pokemon ->
                PokemonListItem(pokemon = pokemon) {
                    navController.navigate("pokemon_detail/${pokemon.name}")
                }
            }
            item {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }

    if (pokemonList.isEmpty() && !isLoading) {
        viewModel.fetchPokemonList()
    }
}

@Composable
fun PokemonListItem(pokemon: PokemonListItem, onClick: () -> Unit) {
    Text(
        text = pokemon.name.capitalize(),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp)
    )
}