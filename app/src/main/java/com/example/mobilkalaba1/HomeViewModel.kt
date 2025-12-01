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
                // Твой вариант №7: 301-350 (50 штук).
                // Offset = 300 (пропускаем первые 300), Limit = 50
                val response = ApiClient.instance.getPokemonList(limit = 50, offset = 300)

                val mappedList = response.results.map { dto ->
                    // Вытаскиваем ID из URL: ".../pokemon/301/" -> "301"
                    val id = dto.url.trimEnd('/').substringAfterLast('/').toInt()

                    Pokemon(
                        id = id,
                        name = dto.name.replaceFirstChar { it.uppercase() },
                        // Официальные спрайты
                        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
                    )
                }

                _pokemonList.value = mappedList

            } catch (e: Exception) {
                _error.value = "Ошибка загрузки: ${e.localizedMessage}"
                e.printStackTrace()
            }
        }
    }
}
