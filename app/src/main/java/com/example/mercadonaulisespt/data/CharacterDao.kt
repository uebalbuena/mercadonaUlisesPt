package com.example.mercadonaulisespt.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CharacterDao {

    @Insert
    suspend fun insertAll(characters: List<Character>)

    @Query("SELECT * FROM characters")
    fun getAllCharacters(): LiveData<List<Character>>

    @Query("SELECT * FROM characters WHERE id = :id LIMIT 1")
    fun getCharacterById(id: Int): LiveData<Character>

    @Update
    suspend fun updateCharacter(character: Character)

    @Delete
    suspend fun delete(character: Character)
}