package com.example.mornin_mobile.features.digest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mornin_mobile.MorninState
import com.example.mornin_mobile.domain.GetDigestForToday
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MorninViewModel(
    private val getDigestForToday: GetDigestForToday
): ViewModel() {

    private val _morninState = MutableStateFlow<MorninState>(MorninState.Loading())
    val state: StateFlow<MorninState> = _morninState

    init {
        loadDigestForToday()
    }

     fun loadDigestForToday() {
         _morninState.value = MorninState.Loading()

         viewModelScope.launch {
             _morninState.value = runCatching {
                 val digest = getDigestForToday.execute()
                 MorninState.Loaded(digest.articles)
             }.getOrElse {
                 MorninState.Error(it.message ?: "Something went wrong")
             }
         }
     }

    companion object {
        fun factory(getDigestForToday: GetDigestForToday): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    MorninViewModel(getDigestForToday)
                }
            }
    }
}