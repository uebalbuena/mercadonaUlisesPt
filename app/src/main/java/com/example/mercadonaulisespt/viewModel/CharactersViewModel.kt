package com.example.mercadonaulisespt.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mercadonaulisespt.model.AllCharacters
import com.example.mercadonaulisespt.model.ResultsCharacters
import com.example.mercadonaulisespt.service.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
        if (characterSingle == null || characterSingle!!.isInitialized) {
            characterSingle = MutableLiveData<ResultsCharacters>()
            loadSingleCharacter(id)
        }
        return characterSingle as MutableLiveData<ResultsCharacters>
    }

    private fun loadCharacters() {

        val baseURL = "https://rickandmortyapi.com/"

        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(Api::class.java)
        val call = api.getCharacter()

        call.enqueue(object : Callback<AllCharacters> {
            override fun onFailure(call: Call<AllCharacters>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<AllCharacters>,
                response: Response<AllCharacters>
            ) {
                characterList!!.value = response.body()
            }
        })

    }

    private fun loadSingleCharacter(id: Int) {

        val baseURL = "https://rickandmortyapi.com/"

        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(Api::class.java)
        val call = api.getSingleCharacter(id)

        call.enqueue(object : Callback<ResultsCharacters> {
            override fun onFailure(call: Call<ResultsCharacters>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<ResultsCharacters>,
                response: Response<ResultsCharacters>
            ) {
                characterSingle!!.value = response.body()
            }
        })
    }

    fun saveStrings(name: String, image: String, id: Int) {
        this.image.value = image
        this.name.value = name
        this.id.value = id
    }
}