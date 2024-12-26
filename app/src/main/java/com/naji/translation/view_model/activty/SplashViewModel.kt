package com.naji.translation.view_model.activty

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naji.translation.api.libre_translate.LibreTranslateRepository
import com.naji.translation.model.api.ListOfLanguages
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val _listOfLanguagesLiveData = MutableLiveData<ListOfLanguages>()
    val listOfLanguagesLiveData: LiveData<ListOfLanguages> = _listOfLanguagesLiveData

    init {
        getListOfLanguages()
    }

    private fun getListOfLanguages() = viewModelScope.launch {
        _listOfLanguagesLiveData.value = LibreTranslateRepository.getListOfLanguages()
    }
}