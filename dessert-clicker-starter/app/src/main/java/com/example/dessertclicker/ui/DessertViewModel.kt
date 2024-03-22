package com.example.dessertclicker.ui

import androidx.lifecycle.ViewModel
import com.example.dessertclicker.data.Datasource.dessertList
import com.example.dessertclicker.data.DessertUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DessertViewModel : ViewModel() {

    // MutableStateFlow, değiştirilebilir bir akış (flow) oluşturur.
    // _dessertUiState, tatlıların kullanıcı arayüzündeki durumunu içerir.
    private val _dessertUiState = MutableStateFlow(DessertUiState())

    // dessertUiState, kullanıcı arayüzündeki tatlı durumunu izlemek için bir StateFlow olarak erişilebilir.
    val dessertUiState: StateFlow<DessertUiState> = _dessertUiState.asStateFlow()

    // Kullanıcı tatlıya tıkladığında tetiklenen fonksiyon.
    fun onDessertClicked() {
        // _dessertUiState'i güncelleme işlemi.
        _dessertUiState.update { cupcakeUiState ->
            // Yeni tatlıların satış sayısını bir arttır.
            val dessertsSold = cupcakeUiState.dessertsSold + 1
            // Yeni tatlı indeksini belirle.
            val nextDessertIndex = determineDessertIndex(dessertsSold)
            // Yeni bir kopya oluştur ve güncellemeleri uygula.
            cupcakeUiState.copy(
                currentDessertIndex = nextDessertIndex,
                revenue = cupcakeUiState.revenue + cupcakeUiState.currentDessertPrice,
                dessertsSold = dessertsSold,
                // Yeni tatlıya ait resim ve fiyat bilgisini güncelle.
                currentDessertImageId = dessertList[nextDessertIndex].imageId,
                currentDessertPrice = dessertList[nextDessertIndex].price
            )
        }
    }

    // Satılan tatlı miktarına göre, sonraki tatlı indeksini belirleyen yardımcı bir fonksiyon.
    private fun determineDessertIndex(dessertsSold: Int): Int {
        var dessertIndex = 0
        // Tüm tatlıları dolaşarak satılan miktarı karşılaştır.
        for (index in dessertList.indices) {
            if (dessertsSold >= dessertList[index].startProductionAmount) {
                // Satılan miktar, tatlıların üretim miktarından büyük veya eşitse, indeksi güncelle.
                dessertIndex = index
            } else {
                // Tatlılar, üretim miktarına göre sıralanır. Daha pahalı tatlılar üretmek için
                // daha fazla tatlı satıldıkça startProductionAmount artar. Satılan miktarı aşan
                // bir üretim miktarına sahip tatlıya ulaştığımızda döngüyü sonlandırabiliriz.
                break
            }
        }
        return dessertIndex
    }
}