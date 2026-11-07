package com.example.model

import androidx.annotation.DrawableRes
import java.text.NumberFormat
import java.util.Locale

sealed interface Screen {
    data object Welcome : Screen
    data object SignUpChoice : Screen
    data object Login : Screen
    data object Register : Screen
    data object Home : Screen
    data object OrderForm : Screen
    data object Profile : Screen
    data object OrderConfirmation : Screen
}

enum class BottomNavTab {
    HOME,
    ORDER,
    PROFILE
}

enum class ProductCategory {
    MAKANAN,
    MINUMAN
}

data class ProductItem(
    val id: String,
    val name: String,
    val description: String,
    val price: Int,
    @DrawableRes val drawableRes: Int,
    val category: ProductCategory
) {
    val formattedPrice: String
        get() {
            val formatter = NumberFormat.getNumberInstance(Locale("in", "ID"))
            return "Rp ${formatter.format(price)}"
        }
}

data class CartItem(
    val product: ProductItem,
    val quantity: Int
) {
    val subtotal: Int get() = product.price * quantity
    val formattedSubtotal: String
        get() {
            val formatter = NumberFormat.getNumberInstance(Locale("in", "ID"))
            return "Rp ${formatter.format(subtotal)}"
        }
}

data class User(
    val username: String,
    val fullName: String,
    val password: String,
    val email: String = "",
    val phone: String = ""
)

data class OrderSummary(
    val orderId: String,
    val items: List<CartItem>,
    val totalPrice: Int,
    val recipientName: String,
    val address: String,
    val detailLandmark: String,
    val orderDate: String
) {
    val product: ProductItem
        get() = items.first().product

    val quantity: Int
        get() = items.sumOf { it.quantity }

    val formattedTotalPrice: String
        get() {
            val formatter = NumberFormat.getNumberInstance(Locale("in", "ID"))
            return "Rp ${formatter.format(totalPrice)}"
        }
}
