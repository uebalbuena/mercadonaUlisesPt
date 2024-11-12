package com.example.mercadonaulisespt.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mercadonaulisespt.model.AllCharacters
import com.example.mercadonaulisespt.model.ResultsCharacters
import com.example.mercadonaulisespt.service.Api
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharactersViewModel: ViewModel() {

    var image = MutableLiveData<String>()
    var name = MutableLiveData<String>()
    var id = MutableLiveData<Int>()

    private var characterList: MutableLiveData<AllCharacters>? = null
    private var characterSingle: MutableLiveData<ResultsCharacters>? = null

    fun getCharacters(): LiveData<AllCharacters> {
        if (characterList == null) {
            characterList = MutableLiveData<AllCharacters>()
            loadCharacters()
        }
        return characterList as MutableLiveData<AllCharacters>
    }

    fun getSingleCharacter(id: Int): LiveData<ResultsCharacters> {
        if (characterSingle == null) {
            characterSingle = MutableLiveData<ResultsCharacters>()
            loadSingleCharacter(id)
        }
        return characterSingle as MutableLiveData<ResultsCharacters>
    }

    private fun loadCharacters() {
        viewModelScope.launch {
            try {
                val api = Retrofit.Builder()
                    .baseUrl("https://rickandmortyapi.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(Api::class.java)

                val response = api.getCharacter()
                characterList?.value = response
            } catch (e: Exception) {
                Log.e("CharactersViewModel", "Error: ${e.message}")
            }
        }
    }

    private fun loadSingleCharacter(id: Int) {
        viewModelScope.launch {
            try {
                val api = Retrofit.Builder()
                    .baseUrl("https://rickandmortyapi.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(Api::class.java)

                val response = api.getSingleCharacter(id)
                characterSingle?.value = response
            } catch (e: Exception) {
                Log.e("CharactersViewModel", "Error: ${e.message}")
            }
        }
    }
}