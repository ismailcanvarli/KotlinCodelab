package com.example.marsphotos.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.marsphotos.MarsPhotosApplication
import com.example.marsphotos.data.MarsPhotosRepository
import com.example.marsphotos.model.MarsPhoto
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

// Mars API'den alınan fotoğrafların sayısını gösterir.
sealed interface MarsUiState {
    data class Success(val photos: List<MarsPhoto>) : MarsUiState
    object Error : MarsUiState
    object Loading : MarsUiState
}

/**
 * MarsViewModel, Mars API'yi çağıran ve sonuçları saklayan ViewModel'dir.
 */
class MarsViewModel(private val marsPhotosRepository: MarsPhotosRepository) : ViewModel(){
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
    fun getMarsPhotos() {
        // Mars API'yi çağır ve sonucu sakla (marsUiState).
        viewModelScope.launch {
            marsUiState = MarsUiState.Loading
            // Mars API'yi çağır ve sonucu sakla (marsUiState).
            marsUiState = try {
                val result = marsPhotosRepository.getMarsPhotos()[0]
                // Mars fotoğraflarını başarıyla alırsa, başarılı durumu göster.
                MarsUiState.Success(marsPhotosRepository.getMarsPhotos())
            // İsteği alırken bir hata oluşursa, hata durumunu göster.
            } catch (e: IOException) {
                MarsUiState.Error
            } catch (e: HttpException) {
                MarsUiState.Error
            }
        }
    }

    /**
     * // [MarsViewModel] için Factory, [MarsPhotosRepository] bağımlılığını alır.
     * Factory for [MarsViewModel] that takes [MarsPhotosRepository] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MarsPhotosApplication)
                val marsPhotosRepository = application.container.marsPhotosRepository
                MarsViewModel(marsPhotosRepository = marsPhotosRepository)
            }
        }
    }
}
