package ru.skillbranch.gameofthrones.viewmodels

import androidx.lifecycle.*
import ru.skillbranch.gameofthrones.data.local.entities.CharacterFull
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem
import ru.skillbranch.gameofthrones.data.local.entities.House
import ru.skillbranch.gameofthrones.repositories.RootRepository

class MainViewModel : ViewModel() {

    private val query = mutableLiveData("")
    private val charactersItems = MutableLiveData<List<CharacterItem>>()
    private val characterFull = MutableLiveData<CharacterFull>()
    private val houses = MutableLiveData<List<House>>()

    fun getCharacterItems(): LiveData<List<CharacterItem>> {
        val result = MediatorLiveData<List<CharacterItem>>()
        val filterF = {
            val queryString = query.value!!
            result.value = if (queryString.isEmpty()) charactersItems.value
            else charactersItems.value?.filter { it.name.contains(queryString, true) }
        }
        result.addSource(charactersItems) { filterF.invoke() }
        result.addSource(query) { filterF.invoke() }

        return result
    }

    fun updateCharactersByHouseName(name: String) {
        RootRepository.findCharactersByHouseName(name) {
            charactersItems.postValue(it)
        }
    }

    fun updateCharacterFullById(id: String) {
        RootRepository.findCharacterFullById(id) {
            characterFull.postValue(it)
        }
    }

    fun updateHouses() {
        RootRepository.getHouses {
            houses.postValue(it)
        }
    }

    fun handleSearchQuery(text: String?) {
        query.value = text
    }

    fun getHouses(): LiveData<List<House>> = houses

    fun getCharacter(): LiveData<CharacterFull> = characterFull

    private fun <T> mutableLiveData(defaultValue: T? = null): MutableLiveData<T> {
        val data = MutableLiveData<T>()
        if (defaultValue != null) {
            data.value = defaultValue
        }
        return data
    }
}