package com.example.mercadonaulisespt.views.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import com.example.mercadonaulisespt.R
import com.example.mercadonaulisespt.databinding.FragmentSingleCharacterBinding
import com.example.mercadonaulisespt.viewModel.CharactersViewModel
import com.squareup.picasso.Picasso
class SingleCharacterFragment : Fragment() {

    private lateinit var singleCharacterBinding: FragmentSingleCharacterBinding
    private val viewModel: CharactersViewModel by activityViewModels()

    private var characterName: String? = null
    private var imageUrl: String? = null
    private var characterId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        singleCharacterBinding = FragmentSingleCharacterBinding.inflate(inflater, container, false)
        return singleCharacterBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            characterName = it.getString("characterName")
            imageUrl = it.getString("imageUrl")
            characterId = it.getInt("characterId")
        }

        singleCharacterBinding.characterName.text = characterName
        imageUrl?.let { singleCharacterBinding.imageDetail.setImageUrl(it) }

        getSingleCharacter()
    }

    override fun onResume() {
        super.onResume()
        getSingleCharacter()
    }

    private fun ImageView.setImageUrl(url: String) {
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.ic_loading)
            .into(this)
    }

    private fun getSingleCharacter() {
        if (characterId != 0) {
            viewModel.getSingleCharacter(characterId).observe(viewLifecycleOwner) { character ->
                singleCharacterBinding.characterStatus.text = character.status
                singleCharacterBinding.characterSpecies.text = character.species
                singleCharacterBinding.characterGender.text = character.gender
            }
        }
    }
}