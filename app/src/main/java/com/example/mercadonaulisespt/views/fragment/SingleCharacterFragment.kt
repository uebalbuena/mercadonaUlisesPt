package com.example.mercadonaulisespt.views.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.mercadonaulisespt.R
import com.example.mercadonaulisespt.data.Character
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

        singleCharacterBinding.characterName.setText(characterName)
        imageUrl?.let { singleCharacterBinding.imageDetail.setImageUrl(it) }

        getSingleCharacter()

        singleCharacterBinding.saveButton.setOnClickListener {
            saveCharacterChanges()
        }
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
                singleCharacterBinding.characterStatus.setText(character.status)
                singleCharacterBinding.characterSpecies.setText(character.species)
                singleCharacterBinding.characterGender.setText(character.gender)
            }
        }
    }

    private fun saveCharacterChanges() {
        val updatedName = singleCharacterBinding.characterName.text.toString()

        if (updatedName.isNotBlank()) {
            // Create an updated Character object with the new name, but leave the other fields as is
            val updatedCharacter = Character(
                id = characterId,
                name = updatedName,
                imageUrl = imageUrl.orEmpty(), // Image remains unchanged
                status = singleCharacterBinding.characterStatus.text.toString(), // Keep existing status
                species = singleCharacterBinding.characterSpecies.text.toString(), // Keep existing species
                gender = singleCharacterBinding.characterGender.text.toString() // Keep existing gender
            )

            // Save the updated name to Room
            viewModel.updateCharacterInRoom(updatedCharacter)

            // Re-fetch the updated character from Room and update the UI
            viewModel.getSingleCharacter(characterId).observe(viewLifecycleOwner) { character ->
                singleCharacterBinding.characterStatus.setText(character.status)
                singleCharacterBinding.characterSpecies.setText(character.species)
                singleCharacterBinding.characterGender.setText(character.gender)
            }

            // Show a success message
            Toast.makeText(context, "Character name updated!", Toast.LENGTH_SHORT).show()
        } else {
            // Handle empty name field
            Toast.makeText(context, "Please fill in the name.", Toast.LENGTH_SHORT).show()
        }
    }
}