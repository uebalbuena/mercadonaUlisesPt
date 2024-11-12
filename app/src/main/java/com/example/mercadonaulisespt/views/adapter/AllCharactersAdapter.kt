package com.example.mercadonaulisespt.views.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mercadonaulisespt.R
import com.example.mercadonaulisespt.data.Character
import com.example.mercadonaulisespt.databinding.ItemAllCharactersBinding
import com.example.mercadonaulisespt.model.ResultsCharacters
import com.squareup.picasso.Picasso

class AllCharactersAdapter(
    private var list: MutableList<Any>, // Use Any to handle both Character and ResultsCharacters
    private val clickListener: OnCharacterClickListener
) : RecyclerView.Adapter<AllCharactersAdapter.AllCharactersViewHolder>() {

    private lateinit var characterItemBinding: ItemAllCharactersBinding

    interface OnCharacterClickListener {
        fun onCharacterClick(characterName: String, imageUrl: String, characterId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllCharactersViewHolder {
        characterItemBinding = ItemAllCharactersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllCharactersViewHolder(characterItemBinding.root)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: AllCharactersViewHolder, position: Int) {
        val character = list[position]
        holder.bind(character, clickListener)
    }

    inner class AllCharactersViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var name: TextView = characterItemBinding.characterName
        private var image: ImageView = characterItemBinding.characterImage

        fun bind(character: Any, characterClickListener: OnCharacterClickListener) {
            when (character) {
                is ResultsCharacters -> {
                    name.text = character.name
                    image.setImageUrl(character.image)
                    itemView.setOnClickListener {
                        characterClickListener.onCharacterClick(character.name, character.image, character.id)
                    }
                }
                is Character -> {
                    name.text = character.name
                    image.setImageUrl(character.imageUrl)
                    itemView.setOnClickListener {
                        characterClickListener.onCharacterClick(character.name, character.imageUrl, character.id)
                    }
                }
            }
        }
    }

    private fun ImageView.setImageUrl(url: String) {
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.ic_loading)
            .into(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: List<Any>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}
