package com.example.marsphotos.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsphotos.network.MarsApi
import kotlinx.coroutines.launch

/**
 * MarsViewModel, Mars API'yi çağıran ve sonuçları saklayan ViewModel'dir.
 */
class MarsViewModel : ViewModel() {
    /** En son isteğin durumunu saklayan Composable state */
    var marsUiState: String by mutableStateOf("")
        private set

    /**
     * state hemen gösterebilmemiz için init üzerinde getMarsPhotos() çağırılır.
     */
    init {
        getMarsPhotos()
    }

    /**
     * Mars API Retrofit servisinden Mars fotoğrafları bilgisini alır ve
     * [MarsPhoto] [List] [MutableList]'i günceller.
     */
    private fun getMarsPhotos() {
        // Mars API'yi çağır ve sonucu sakla (marsUiState).
        viewModelScope.launch {
            // Mars API'yi çağır ve sonucu sakla (marsUiState).
            val listResult = MarsApi.retrofitService.getPhotos()
            marsUiState = listResult
        }
    }


}
