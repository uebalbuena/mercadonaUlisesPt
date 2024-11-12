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

    private val viewModel : CharactersViewModel by activityViewModels()
    private lateinit var singleCharacterBinding: FragmentSingleCharacterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        singleCharacterBinding = FragmentSingleCharacterBinding.inflate(inflater, container, false)
        return singleCharacterBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        singleCharacterBinding.characterName.text = viewModel.name.value
        viewModel.image.value?.let { singleCharacterBinding.imageDetail.setImageUrl(it) }
        getSingleCharacter()
    }

    override fun onResume() {
        super.onResume()
        getSingleCharacter()
    }

    private fun ImageView.setImageUrl(url:String){
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.ic_loading)
            .into(this)
    }

    private fun getSingleCharacter(){
        viewModel.id.value?.let { id ->
            viewModel.getSingleCharacter(id).observe(viewLifecycleOwner) {
                singleCharacterBinding.characterStatus.text = it.status
                singleCharacterBinding.characterSpecies.text = it.species
                singleCharacterBinding.characterGender.text = it.gender
            }
        }
    }
}