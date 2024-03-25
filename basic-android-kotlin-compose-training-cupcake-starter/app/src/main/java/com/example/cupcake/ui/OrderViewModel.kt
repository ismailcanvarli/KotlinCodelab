package com.example.cupcake.ui

import androidx.lifecycle.ViewModel
import com.example.cupcake.data.OrderUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/** Bir cupcake için fiyat */
private const val PRICE_PER_CUPCAKE = 2.00

/** Aynı gün alım için ekstra maliyet */
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

/**
 * [OrderViewModel], miktar, lezzet ve alım tarihi açısından bir cupcake siparişi hakkında
 * bilgi saklar. Ayrıca, bu sipariş detaylarına dayanarak toplam fiyatı nasıl hesaplayacağını bilir.
 */
class OrderViewModel : ViewModel() {

    /**
     * Bu siparişin cupcake durumu
     */
    private val _uiState = MutableStateFlow(OrderUiState(pickupOptions = pickupOptions()))
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    /**
     * Bu siparişin durumu için cupcake miktarını [numberCupcakes] olarak ayarlar ve fiyatı günceller
     */
    fun setQuantity(numberCupcakes: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                quantity = numberCupcakes, price = calculatePrice(quantity = numberCupcakes)
            )
        }
    }

    /**
     * Bu siparişin durumu için cupcake'lerin [desiredFlavor]'ını ayarlar.
     * Tüm sipariş için yalnızca 1 lezzet seçilebilir.
     */
    fun setFlavor(desiredFlavor: String) {
        //_uiState'in flavor özelliğini desiredFlavor ile güncellemektir.
        _uiState.update { currentState ->
            currentState.copy(flavor = desiredFlavor)
        }
    }

    /**
     * Bu siparişin durumu için [pickupDate]'i ayarlar ve fiyatı günceller.
     */
    fun setDate(pickupDate: String) {
        _uiState.update { currentState ->
            currentState.copy(
                date = pickupDate, price = calculatePrice(pickupDate = pickupDate)
            )
        }
    }

    /**
     * Sipariş durumunu sıfırlar.
     */
    fun resetOrder() {
        _uiState.value = OrderUiState(pickupOptions = pickupOptions())
    }

    /**
     * Sipariş detaylarına dayanarak hesaplanmış fiyatı döndürür.
     */
    private fun calculatePrice(
        quantity: Int = _uiState.value.quantity, pickupDate: String = _uiState.value.date
    ): String {
        var calculatedPrice = quantity * PRICE_PER_CUPCAKE
        // Eğer kullanıcı alım için ilk seçeneği (bugün) seçtiyse, ek ücret ekler
        if (pickupOptions()[0] == pickupDate) {
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }
        val formattedPrice = NumberFormat.getCurrencyInstance().format(calculatedPrice)
        return formattedPrice
    }

    /**
     * Şu andan itibaren başlayarak, mevcut tarihi ve
     * sonraki 3 tarihi içeren bir tarih seçenekleri listesi döndürür.
     */
    private fun pickupOptions(): List<String> {
        val dateOptions = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        // mevcut tarihi ve sonraki 3 tarihi ekler.
        repeat(4) {
            dateOptions.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return dateOptions
    }
}
