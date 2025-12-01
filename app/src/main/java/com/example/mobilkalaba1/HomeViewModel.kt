package com.example.mobilkalaba1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>> = _pokemonList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun loadPokemon() {
        viewModelScope.launch {
            try {
                val response = ApiClient.instance.getPokemonList(limit = 50, offset = 300)

                val mappedList = response.results.map { dto ->
                    val id = dto.url.trimEnd('/').substringAfterLast('/').toInt()

                    Pokemon(
                        id = id,
                        name = dto.name.replaceFirstChar { it.uppercase() },
                        //вытаскиваю спрайсы с оф стайта
                        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
                    )
                }

                _pokemonList.value = mappedList

            } catch (e: Exception) {
                _error.value = "err loadPokemon: ${e.localizedMessage}"
                e.printStackTrace()
            }
        }
    }
}
