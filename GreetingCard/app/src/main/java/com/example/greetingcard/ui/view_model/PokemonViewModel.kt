package com.example.greetingcard.ui.view_model

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greetingcard.data.models.PokemonDetail
import com.example.greetingcard.data.models.PokemonListItem
import com.example.greetingcard.repository.PokemonRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PokemonViewModel(private val repository: PokemonRepository) : ViewModel() {
    private val _pokemonList = MutableLiveData<List<PokemonListItem>>(emptyList())
    val pokemonList: LiveData<List<PokemonListItem>> = _pokemonList

    private val _pokemonDetail = MutableLiveData<PokemonDetail?>()
    val pokemonDetail: LiveData<PokemonDetail?> = _pokemonDetail

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    var offset = 0
    var errorMessage = mutableStateOf<String?>(null)

    fun fetchPokemonList() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getPokemonList(offset)
                delay(500)
                _pokemonList.value = (_pokemonList.value ?: emptyList()) + response.results
                Log.d("PokemonViewModel", "New list size: ${pokemonList.value?.size}")
                offset += 20
                errorMessage.value = null
            } catch (e: Exception) {
                errorMessage.value = "Failed to load Pokémon: ${e.message}" aaaaa
            }
            _isLoading.value = false
        }
    }

    fun fetchPokemonDetail(name: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _pokemonDetail.value = repository.getPokemonDetail(name)
            } catch (e: Exception) {
                // Handle error
            }
            _isLoading.value = false
        }
    }
}