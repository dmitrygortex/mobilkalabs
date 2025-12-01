package com.example.mobilkalaba1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels // Если красное - нужна зависимость fragment-ktx
import androidx.lifecycle.ViewModelProvider // Или старый способ
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobilkalaba1.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Если не работает "by viewModels()", используй старый способ в onViewCreated
    private lateinit var viewModel: HomeViewModel
    private val adapter = CharacterAdapter() // Или PokemonAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация ViewModel
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        // Настройка списка
        binding.rvTasks.layoutManager = LinearLayoutManager(requireContext())
        // (Убедись, что в fragment_home.xml RecyclerView имеет id rvTasks или исправь тут)
        binding.rvTasks.adapter = adapter

        // Подписка на данные
        viewModel.pokemonList.observe(viewLifecycleOwner) { list ->
            adapter.updateData(list)
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_LONG).show()
        }

        // Загружаем покемонов при старте
        viewModel.loadPokemon()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
