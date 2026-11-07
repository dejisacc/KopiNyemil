package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.SampleData
import com.example.model.BottomNavTab
import com.example.model.CartItem
import com.example.model.OrderSummary
import com.example.model.ProductItem
import com.example.model.Screen
import com.example.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class UiState(
    val currentScreen: Screen = Screen.Welcome,
    val screenHistory: List<Screen> = listOf(Screen.Welcome),
    val activeNavTab: BottomNavTab = BottomNavTab.HOME,
    val currentUser: User? = null,
    val users: List<User> = SampleData.defaultUsers,
    val products: List<ProductItem> = SampleData.initialProducts,

    // Multi-item cart (ProductId -> Quantity)
    val cart: Map<String, Int> = emptyMap(),
    val lastOrder: OrderSummary? = null,

    // Login Form State
    val loginUsername: String = "",
    val loginPassword: String = "",
    val isLoginPasswordVisible: Boolean = false,
    val loginError: String? = null,

    // Register Form State
    val registerFullName: String = "",
    val registerUsername: String = "",
    val registerPassword: String = "",
    val isRegisterPasswordVisible: Boolean = false,
    val registerError: String? = null,
    val registerSuccess: String? = null,

    // Profile Form State (Name, Email, Phone)
    val profileNameInput: String = "",
    val profileEmailInput: String = "",
    val profilePhoneInput: String = "",
    val profileFeedback: String? = null,

    // Order Form State
    val orderRecipientName: String = "",
    val orderAddress: String = "",
    val orderLandmark: String = "",
    val orderError: String? = null
) {
    val cartItems: List<CartItem>
        get() = products.filter { (cart[it.id] ?: 0) > 0 }.map {
            CartItem(product = it, quantity = cart[it.id] ?: 0)
        }

    val cartTotalPrice: Int
        get() = cartItems.sumOf { it.subtotal }

    val totalItemsCount: Int
        get() = cartItems.sumOf { it.quantity }

    // Compatibility for any single-product helper
    val selectedProduct: ProductItem
        get() = cartItems.firstOrNull()?.product ?: products.first()

    val orderQuantity: Int
        get() = cartItems.firstOrNull()?.quantity ?: 1
}

class KopiNyemilViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun navigateTo(screen: Screen) {
        _uiState.update { state ->
            val newTab = when (screen) {
                Screen.Home -> BottomNavTab.HOME
                Screen.OrderForm -> BottomNavTab.ORDER
                Screen.Profile -> BottomNavTab.PROFILE
                else -> state.activeNavTab
            }
            // Auto populate recipient name in order if empty and user is logged in
            val recipient = if (state.orderRecipientName.isBlank() && state.currentUser != null) {
                state.currentUser.fullName
            } else {
                state.orderRecipientName
            }
            state.copy(
                currentScreen = screen,
                screenHistory = state.screenHistory + screen,
                activeNavTab = newTab,
                orderRecipientName = recipient,
                loginError = null,
                registerError = null,
                orderError = null
            )
        }
    }

    fun navigateBack(): Boolean {
        var handled = false
        _uiState.update { state ->
            if (state.screenHistory.size > 1) {
                handled = true
                val newHistory = state.screenHistory.dropLast(1)
                val previous = newHistory.last()
                val newTab = when (previous) {
                    Screen.Home -> BottomNavTab.HOME
                    Screen.OrderForm -> BottomNavTab.ORDER
                    Screen.Profile -> BottomNavTab.PROFILE
                    else -> state.activeNavTab
                }
                state.copy(
                    currentScreen = previous,
                    screenHistory = newHistory,
                    activeNavTab = newTab,
                    loginError = null,
                    registerError = null,
                    orderError = null
                )
            } else {
                state
            }
        }
        return handled
    }

    fun selectNavTab(tab: BottomNavTab) {
        when (tab) {
            BottomNavTab.HOME -> navigateTo(Screen.Home)
            BottomNavTab.ORDER -> navigateTo(Screen.OrderForm)
            BottomNavTab.PROFILE -> navigateTo(Screen.Profile)
        }
    }

    // --- Login Handlers ---
    fun updateLoginUsername(value: String) {
        _uiState.update { it.copy(loginUsername = value, loginError = null) }
    }

    fun updateLoginPassword(value: String) {
        _uiState.update { it.copy(loginPassword = value, loginError = null) }
    }

    fun toggleLoginPasswordVisibility() {
        _uiState.update { it.copy(isLoginPasswordVisible = !it.isLoginPasswordVisible) }
    }

    fun login() {
        val state = _uiState.value
        val username = state.loginUsername.trim()
        val password = state.loginPassword.trim()

        if (username.isEmpty() || password.isEmpty()) {
            _uiState.update {
                it.copy(loginError = "Username dan password tidak boleh kosong!")
            }
            return
        }

        val foundUser = state.users.find {
            it.username.equals(username, ignoreCase = true) && it.password == password
        }

        if (foundUser != null) {
            _uiState.update {
                it.copy(
                    currentUser = foundUser,
                    profileNameInput = foundUser.fullName,
                    profileEmailInput = foundUser.email,
                    profilePhoneInput = foundUser.phone,
                    orderRecipientName = foundUser.fullName,
                    loginError = null,
                    currentScreen = Screen.Home,
                    screenHistory = listOf(Screen.Home),
                    activeNavTab = BottomNavTab.HOME
                )
            }
        } else {
            _uiState.update {
                it.copy(loginError = "Username atau password salah. Coba: budi / 123")
            }
        }
    }

    // --- Register Handlers ---
    fun updateRegisterFullName(value: String) {
        _uiState.update { it.copy(registerFullName = value, registerError = null) }
    }

    fun updateRegisterUsername(value: String) {
        _uiState.update { it.copy(registerUsername = value, registerError = null) }
    }

    fun updateRegisterPassword(value: String) {
        _uiState.update { it.copy(registerPassword = value, registerError = null) }
    }

    fun toggleRegisterPasswordVisibility() {
        _uiState.update { it.copy(isRegisterPasswordVisible = !it.isRegisterPasswordVisible) }
    }

    fun register() {
        val state = _uiState.value
        val fullName = state.registerFullName.trim()
        val username = state.registerUsername.trim()
        val password = state.registerPassword.trim()

        if (fullName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            _uiState.update {
                it.copy(registerError = "Seluruh form pendaftaran wajib diisi!")
            }
            return
        }

        if (state.users.any { it.username.equals(username, ignoreCase = true) }) {
            _uiState.update {
                it.copy(registerError = "Username sudah digunakan, silakan pilih username lain.")
            }
            return
        }

        val newUser = User(
            username = username,
            fullName = fullName,
            password = password,
            email = "$username@email.com",
            phone = "081234567890"
        )
        _uiState.update {
            it.copy(
                users = it.users + newUser,
                loginUsername = username,
                loginPassword = "",
                registerError = null,
                registerSuccess = "Akun berhasil dibuat! Silakan login.",
                registerFullName = "",
                registerUsername = "",
                registerPassword = "",
                currentScreen = Screen.Login,
                screenHistory = it.screenHistory + Screen.Login
            )
        }
    }

    // --- Multi-product Cart Handlers ---
    fun addToCart(product: ProductItem) {
        _uiState.update { state ->
            val currentQty = state.cart[product.id] ?: 0
            val newCart = state.cart.toMutableMap()
            newCart[product.id] = (currentQty + 1).coerceAtMost(50)
            state.copy(cart = newCart)
        }
    }

    fun incrementCartItem(productId: String) {
        _uiState.update { state ->
            val currentQty = state.cart[productId] ?: 0
            val newCart = state.cart.toMutableMap()
            newCart[productId] = (currentQty + 1).coerceAtMost(50)
            state.copy(cart = newCart)
        }
    }

    fun decrementCartItem(productId: String) {
        _uiState.update { state ->
            val currentQty = state.cart[productId] ?: 0
            val newCart = state.cart.toMutableMap()
            if (currentQty <= 1) {
                newCart.remove(productId)
            } else {
                newCart[productId] = currentQty - 1
            }
            state.copy(cart = newCart)
        }
    }

    fun selectProductForOrder(product: ProductItem) {
        _uiState.update { state ->
            val newCart = state.cart.toMutableMap()
            if ((newCart[product.id] ?: 0) == 0) {
                newCart[product.id] = 1
            }
            state.copy(
                cart = newCart,
                currentScreen = Screen.OrderForm,
                screenHistory = state.screenHistory + Screen.OrderForm,
                activeNavTab = BottomNavTab.ORDER
            )
        }
    }

    // Single item compatibility helpers
    fun incrementOrderQuantity() {
        val firstItem = _uiState.value.cartItems.firstOrNull()
        if (firstItem != null) {
            incrementCartItem(firstItem.product.id)
        } else {
            val firstProd = _uiState.value.products.first()
            addToCart(firstProd)
        }
    }

    fun decrementOrderQuantity() {
        val firstItem = _uiState.value.cartItems.firstOrNull()
        if (firstItem != null) {
            decrementCartItem(firstItem.product.id)
        }
    }

    // --- Order Form Handlers ---
    fun updateOrderRecipientName(value: String) {
        _uiState.update { it.copy(orderRecipientName = value, orderError = null) }
    }

    fun updateOrderAddress(value: String) {
        _uiState.update { it.copy(orderAddress = value, orderError = null) }
    }

    fun updateOrderLandmark(value: String) {
        _uiState.update { it.copy(orderLandmark = value, orderError = null) }
    }

    fun submitOrder() {
        val state = _uiState.value
        val recipient = state.orderRecipientName.trim()
        val address = state.orderAddress.trim()
        val landmark = state.orderLandmark.trim()

        if (state.cartItems.isEmpty()) {
            _uiState.update {
                it.copy(orderError = "Pilih minimal satu produk untuk dipesan!")
            }
            return
        }

        if (recipient.isEmpty() || address.isEmpty() || landmark.isEmpty()) {
            _uiState.update {
                it.copy(orderError = "Harap lengkapi semua data alamat pengiriman!")
            }
            return
        }

        val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("in", "ID"))
        val order = OrderSummary(
            orderId = "KN-${System.currentTimeMillis() % 100000}",
            items = state.cartItems,
            totalPrice = state.cartTotalPrice,
            recipientName = recipient,
            address = address,
            detailLandmark = landmark,
            orderDate = dateFormat.format(Date())
        )

        _uiState.update {
            it.copy(
                lastOrder = order,
                orderError = null,
                currentScreen = Screen.OrderConfirmation,
                screenHistory = it.screenHistory + Screen.OrderConfirmation
            )
        }
    }

    // --- Profile Handlers ---
    fun updateProfileNameInput(value: String) {
        _uiState.update { it.copy(profileNameInput = value, profileFeedback = null) }
    }

    fun updateProfileEmailInput(value: String) {
        _uiState.update { it.copy(profileEmailInput = value, profileFeedback = null) }
    }

    fun updateProfilePhoneInput(value: String) {
        _uiState.update { it.copy(profilePhoneInput = value, profileFeedback = null) }
    }

    fun saveProfile() {
        val state = _uiState.value
        val newName = state.profileNameInput.trim()
        val newEmail = state.profileEmailInput.trim()
        val newPhone = state.profilePhoneInput.trim()

        if (newName.isEmpty()) {
            _uiState.update { it.copy(profileFeedback = "Nama tidak boleh kosong!") }
            return
        }

        val updatedUser = state.currentUser?.copy(
            fullName = newName,
            email = newEmail,
            phone = newPhone
        ) ?: User(
            username = "guest",
            fullName = newName,
            password = "",
            email = newEmail,
            phone = newPhone
        )

        val updatedUsers = state.users.map {
            if (it.username == updatedUser.username) updatedUser else it
        }

        _uiState.update {
            it.copy(
                currentUser = updatedUser,
                users = updatedUsers,
                orderRecipientName = newName,
                profileFeedback = "Profil berhasil diperbarui!"
            )
        }
    }

    fun logout() {
        _uiState.update {
            it.copy(
                currentUser = null,
                loginUsername = "",
                loginPassword = "",
                currentScreen = Screen.SignUpChoice,
                screenHistory = listOf(Screen.Welcome, Screen.SignUpChoice),
                activeNavTab = BottomNavTab.HOME
            )
        }
    }

    fun resetOrderAndGoHome() {
        _uiState.update {
            it.copy(
                currentScreen = Screen.Home,
                screenHistory = listOf(Screen.Home),
                activeNavTab = BottomNavTab.HOME,
                cart = emptyMap()
            )
        }
    }
}
