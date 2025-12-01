package com.example.mobilkalaba1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load // Если красное - добавь зависимость Coil
import com.example.mobilkalaba1.databinding.ItemCharacterBinding // Или ItemPokemonBinding

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.PokemonViewHolder>() {

    private var pokemonList: List<Pokemon> = emptyList()

    fun updateData(newList: List<Pokemon>) {
        pokemonList = newList
        notifyDataSetChanged()
    }

    class PokemonViewHolder(val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]

        with(holder.binding) {
            tvName.text = pokemon.name
            tvId.text = "#${pokemon.id}"

            // Загрузка картинки через Coil
            ivPokemon.load(pokemon.imageUrl) {
                crossfade(true)
                placeholder(android.R.drawable.ic_menu_gallery)
                error(android.R.drawable.ic_menu_close_clear_cancel)
            }
        }
    }

    override fun getItemCount() = pokemonList.size
}
