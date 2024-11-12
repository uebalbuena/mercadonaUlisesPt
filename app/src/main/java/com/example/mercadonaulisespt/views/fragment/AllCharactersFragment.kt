package com.example.mercadonaulisespt.views.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mercadonaulisespt.R
import com.example.mercadonaulisespt.databinding.FragmentAllCharactersBinding
import com.example.mercadonaulisespt.model.ResultsCharacters
import com.example.mercadonaulisespt.viewModel.CharactersViewModel
import com.example.mercadonaulisespt.views.adapter.AllCharactersAdapter

class AllCharactersFragment : Fragment(), AllCharactersAdapter.OnCharacterClickListener {

    private lateinit var binding: FragmentAllCharactersBinding
    private val viewModel: CharactersViewModel by activityViewModels()
    private lateinit var adapter: AllCharactersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = AllCharactersAdapter(mutableListOf(), this)
        binding.recyclerAllCharacters.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = this@AllCharactersFragment.adapter
        }

        viewModel.getAllCharactersFromRoom().observe(viewLifecycleOwner, Observer { characters ->
            if (characters.isNotEmpty()) {
                adapter.updateData(characters)
                binding.progressList.visibility = View.GONE
            } else {
                binding.progressList.visibility = View.VISIBLE
            }
        })

        viewModel.getCharacters().observe(viewLifecycleOwner) { characters ->
            characters?.characterResults?.let { results ->
                adapter.updateData(results)
                binding.progressList.visibility = View.GONE
            }
        }
    }

    override fun onCharacterClick(characterName: String, imageUrl: String, characterId: Int) {
        val bundle = Bundle().apply {
            putString("characterName", characterName)
            putString("imageUrl", imageUrl)
            putInt("characterId", characterId)
        }

        findNavController().navigate(R.id.action_allCharactersFragment_to_singleCharacterFragment, bundle)
    }
}

