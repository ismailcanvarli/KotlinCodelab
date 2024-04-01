package com.example.marsphotos.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsphotos.network.MarsApi
import kotlinx.coroutines.launch
import java.io.IOException

// Mars API'den alınan fotoğrafların sayısını gösterir.
sealed interface MarsUiState {
    data class Success(val photos: String) : MarsUiState
    object Error : MarsUiState
    object Loading : MarsUiState
}

/**
 * MarsViewModel, Mars API'yi çağıran ve sonuçları saklayan ViewModel'dir.
 */
class MarsViewModel : ViewModel() {
    /** En son isteğin durumunu saklayan Composable state */
    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)
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
            marsUiState = MarsUiState.Loading
            // Mars API'yi çağır ve sonucu sakla (marsUiState).
            marsUiState = try {
                // Mars API'den fotoğrafları al. Bu istek GET isteğidir.
                val listResult = MarsApi.retrofitService.getPhotos()
                // İsteği başarılı bir şekilde aldıysak, sonucu göster.
                MarsUiState.Success(listResult)
            // İsteği alırken bir hata oluşursa, hata durumunu göster.
            } catch (e: IOException) {
                MarsUiState.Error
            }
        }
    }


}
