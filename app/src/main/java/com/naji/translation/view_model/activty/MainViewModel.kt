package com.naji.translation.view_model.activty

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naji.translation.api.Resource
import com.naji.translation.api.libre_translate.LibreTranslateRepository
import com.naji.translation.model.Translation
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _translationLiveData = MutableLiveData<Resource<Translation>>()
    val translationLiveData: LiveData<Resource<Translation>> = _translationLiveData

    fun translate(
        q: String,
        source: String,
        target: String
    ) = viewModelScope.launch {
        _translationLiveData.value = Resource.Loading()
        _translationLiveData.value = LibreTranslateRepository.translate(q, source, target)
    }
}