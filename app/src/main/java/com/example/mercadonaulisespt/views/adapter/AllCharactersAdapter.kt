package com.example.mercadonaulisespt.views.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mercadonaulisespt.R
import com.example.mercadonaulisespt.databinding.ItemAllCharactersBinding
import com.example.mercadonaulisespt.model.ResultsCharacters
import com.squareup.picasso.Picasso

class AllCharactersAdapter (
    private val list: List<ResultsCharacters>,
    private val clickListener: OnCharacterClickListener)
    : RecyclerView.Adapter<AllCharactersAdapter.AllCharactersViewHolder>() {

    private lateinit var characterItemBinding: ItemAllCharactersBinding

    interface OnCharacterClickListener {
        fun onCharacterClick(image: String, name: String, id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllCharactersViewHolder {
        characterItemBinding = ItemAllCharactersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllCharactersViewHolder(characterItemBinding.root)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: AllCharactersViewHolder, position: Int) {
        val characters: ResultsCharacters = list[position]
        holder.bind(characters, clickListener)
    }

    inner class AllCharactersViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private var name: TextView = characterItemBinding.characterName
        private var image: ImageView = characterItemBinding.characterImage

        fun bind (characters: ResultsCharacters, characterClickListener: OnCharacterClickListener){
            name.text = characters.name
            image.setImageUrl(characters.image)
            itemView.setOnClickListener{
                characterClickListener.onCharacterClick(characters.name, characters.image, characters.id)
            }
        }
    }

    private fun ImageView.setImageUrl(url:String){
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.ic_loading)
            .into(this)
    }
}