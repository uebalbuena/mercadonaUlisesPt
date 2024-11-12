package com.example.mercadonaulisespt.data

import androidx.lifecycle.LiveData
import com.example.mercadonaulisespt.model.ResultsCharacters

class CharacterRepository(private val characterDao: CharacterDao) {

    suspend fun saveCharactersFromApi(results: List<ResultsCharacters>) {
        val characters = results.map {
            Character(
                id = it.id,
                name = it.name,
                imageUrl = it.image,
                status = it.status,
                species = it.species,
                gender = it.gender
            )
        }
        characterDao.insertAll(characters)
    }

    fun getAllCharacters(): LiveData<List<Character>> {
        return characterDao.getAllCharacters()
    }

    suspend fun updateCharacter(updatedCharacter: Character) {
        characterDao.updateCharacter(updatedCharacter)
    }

    fun getCharacterById(id: Int): LiveData<Character> {
        return characterDao.getCharacterById(id)
    }
}