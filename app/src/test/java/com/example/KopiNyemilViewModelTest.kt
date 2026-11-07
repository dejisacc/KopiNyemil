package com.example

import com.example.model.ProductCategory
import com.example.model.Screen
import com.example.viewmodel.KopiNyemilViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class KopiNyemilViewModelTest {

    private lateinit var viewModel: KopiNyemilViewModel

    @Before
    fun setUp() {
        viewModel = KopiNyemilViewModel()
    }

    @Test
    fun testInitialState() {
        val state = viewModel.uiState.value
        assertEquals(Screen.Welcome, state.currentScreen)
        assertEquals(5, state.products.size)
        // Check order of products: Makanan first (Donat, Sourdough) then Minuman (Cappuccino, Kopi Susu, Matcha)
        assertEquals("Donat Cokelat", state.products[0].name)
        assertEquals(ProductCategory.MAKANAN, state.products[0].category)
        assertEquals("Sourdough Loaf", state.products[1].name)
        assertEquals(ProductCategory.MAKANAN, state.products[1].category)
        assertEquals("Cappuccino", state.products[2].name)
        assertEquals(ProductCategory.MINUMAN, state.products[2].category)
    }

    @Test
    fun testNavigationFlow() {
        viewModel.navigateTo(Screen.SignUpChoice)
        assertEquals(Screen.SignUpChoice, viewModel.uiState.value.currentScreen)

        viewModel.navigateTo(Screen.Login)
        assertEquals(Screen.Login, viewModel.uiState.value.currentScreen)

        viewModel.navigateBack()
        assertEquals(Screen.SignUpChoice, viewModel.uiState.value.currentScreen)
    }

    @Test
    fun testLoginSuccessAndError() {
        // Attempt empty login
        viewModel.updateLoginUsername("")
        viewModel.updateLoginPassword("")
        viewModel.login()
        assertNotNull(viewModel.uiState.value.loginError)

        // Login with valid dummy credentials
        viewModel.updateLoginUsername("budi")
        viewModel.updateLoginPassword("123")
        viewModel.login()

        val state = viewModel.uiState.value
        assertEquals(Screen.Home, state.currentScreen)
        assertEquals("Budi", state.currentUser?.fullName)
        assertEquals("budi.santoso@email.com", state.currentUser?.email)
        assertEquals("081234567890", state.currentUser?.phone)
    }

    @Test
    fun testRegisterNewUser() {
        viewModel.updateRegisterFullName("Dewi Lestari")
        viewModel.updateRegisterUsername("dewi")
        viewModel.updateRegisterPassword("secret")
        viewModel.register()

        val state = viewModel.uiState.value
        assertEquals(Screen.Login, state.currentScreen)
        assertNotNull(state.registerSuccess)
        assertTrue(state.users.any { it.username == "dewi" })
    }

    @Test
    fun testMultiProductOrderFlow() {
        // Add food: Donat Cokelat (12000 x 2)
        val donat = viewModel.uiState.value.products.first { it.name == "Donat Cokelat" }
        viewModel.addToCart(donat)
        viewModel.incrementCartItem(donat.id)

        // Add drink: Cappuccino (25000 x 1)
        val cappuccino = viewModel.uiState.value.products.first { it.name == "Cappuccino" }
        viewModel.addToCart(cappuccino)

        // Verify multi-item cart
        val cartItems = viewModel.uiState.value.cartItems
        assertTrue(cartItems.size >= 2)

        viewModel.updateOrderRecipientName("Andi Pratama")
        viewModel.updateOrderAddress("Jl. Melati No. 12")
        viewModel.updateOrderLandmark("Dekat Masjid")
        viewModel.submitOrder()

        val state = viewModel.uiState.value
        assertEquals(Screen.OrderConfirmation, state.currentScreen)
        assertNotNull(state.lastOrder)
        assertTrue(state.lastOrder!!.items.any { it.product.name == "Donat Cokelat" })
        assertTrue(state.lastOrder!!.items.any { it.product.name == "Cappuccino" })
        assertEquals("Andi Pratama", state.lastOrder?.recipientName)
    }

    @Test
    fun testUpdateProfileWithEmailAndPhone() {
        viewModel.updateLoginUsername("budi")
        viewModel.updateLoginPassword("123")
        viewModel.login()

        viewModel.updateProfileNameInput("Budi Santoso")
        viewModel.updateProfileEmailInput("budi.new@gmail.com")
        viewModel.updateProfilePhoneInput("081999888777")
        viewModel.saveProfile()

        val state = viewModel.uiState.value
        assertEquals("Budi Santoso", state.currentUser?.fullName)
        assertEquals("budi.new@gmail.com", state.currentUser?.email)
        assertEquals("081999888777", state.currentUser?.phone)
        assertNotNull(state.profileFeedback)
    }
}
