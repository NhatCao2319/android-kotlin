package com.example.greetingcard.di

import com.example.greetingcard.data.services.PokemonApi
import com.example.greetingcard.repository.PokemonRepository
import com.example.greetingcard.ui.view_model.PokemonViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonApi::class.java)
    }
    single { PokemonRepository(get()) }
    viewModel { PokemonViewModel(get()) }
}