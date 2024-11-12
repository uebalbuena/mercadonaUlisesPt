package com.example.mercadonaulisespt.views.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mercadonaulisespt.R
import com.example.mercadonaulisespt.databinding.FragmentAllCharactersBinding
import com.example.mercadonaulisespt.model.ResultsCharacters
import com.example.mercadonaulisespt.viewModel.CharactersViewModel
import com.example.mercadonaulisespt.views.adapter.AllCharactersAdapter

class AllCharactersFragment : Fragment(), AllCharactersAdapter.OnCharacterClickListener {

    private val allCharactersViewModel: CharactersViewModel by activityViewModels()
    private lateinit var charactersBinding: FragmentAllCharactersBinding
    private var charactersAdapter: AllCharactersAdapter?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getCharacters()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        charactersBinding = FragmentAllCharactersBinding.inflate(inflater, container, false)
        return charactersBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCharacters()
    }

    override fun onResume() {
        super.onResume()
        getCharacters()
    }

    private fun getCharacters() {
        if (view != null){
            allCharactersViewModel.getCharacters().observe(viewLifecycleOwner) { allCharacters ->
                prepareRecyclerView(allCharacters.characterResults)
                charactersBinding.progressList.visibility = View.GONE
            }
        }
    }

    private fun prepareRecyclerView(charactersList: List<ResultsCharacters>){
        charactersAdapter = AllCharactersAdapter(charactersList, this)
        charactersBinding.recyclerAllCharacters.layoutManager = LinearLayoutManager(context)
        charactersBinding.recyclerAllCharacters.itemAnimator = DefaultItemAnimator()
        charactersBinding.recyclerAllCharacters.adapter = charactersAdapter
    }

    override fun onCharacterClick(
        image: String,
        name: String,
        id: Int
    ) {
        allCharactersViewModel.saveStrings(image, name, id)
        findNavController().navigate(R.id.action_allCharactersFragment_to_singleCharacterFragment)
    }
}