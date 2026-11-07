package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.model.Screen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.OrderConfirmationScreen
import com.example.ui.screens.OrderScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.RegisterScreen
import com.example.ui.screens.SignUpChoiceScreen
import com.example.ui.screens.WelcomeScreen
import com.example.ui.theme.KopiNyemilTheme
import com.example.viewmodel.KopiNyemilViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: KopiNyemilViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KopiNyemilTheme {
                KopiNyemilApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun KopiNyemilApp(viewModel: KopiNyemilViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState.currentScreen) {
        Screen.Welcome -> {
            WelcomeScreen(
                onStartNowClick = { viewModel.navigateTo(Screen.SignUpChoice) },
                modifier = Modifier.fillMaxSize()
            )
        }

        Screen.SignUpChoice -> {
            BackHandler {
                viewModel.navigateTo(Screen.Welcome)
            }
            SignUpChoiceScreen(
                onRegisterClick = { viewModel.navigateTo(Screen.Register) },
                onLoginClick = { viewModel.navigateTo(Screen.Login) },
                modifier = Modifier.fillMaxSize()
            )
        }

        Screen.Login -> {
            BackHandler {
                viewModel.navigateBack()
            }
            LoginScreen(
                usernameValue = uiState.loginUsername,
                onUsernameChange = viewModel::updateLoginUsername,
                passwordValue = uiState.loginPassword,
                onPasswordChange = viewModel::updateLoginPassword,
                isPasswordVisible = uiState.isLoginPasswordVisible,
                onTogglePasswordVisibility = viewModel::toggleLoginPasswordVisibility,
                errorMessage = uiState.loginError,
                successMessage = uiState.registerSuccess,
                onLoginClick = viewModel::login,
                onRegisterClick = { viewModel.navigateTo(Screen.Register) },
                onBackClick = { viewModel.navigateBack() },
                modifier = Modifier.fillMaxSize()
            )
        }

        Screen.Register -> {
            BackHandler {
                viewModel.navigateBack()
            }
            RegisterScreen(
                fullNameValue = uiState.registerFullName,
                onFullNameChange = viewModel::updateRegisterFullName,
                usernameValue = uiState.registerUsername,
                onUsernameChange = viewModel::updateRegisterUsername,
                passwordValue = uiState.registerPassword,
                onPasswordChange = viewModel::updateRegisterPassword,
                isPasswordVisible = uiState.isRegisterPasswordVisible,
                onTogglePasswordVisibility = viewModel::toggleRegisterPasswordVisibility,
                errorMessage = uiState.registerError,
                onRegisterClick = viewModel::register,
                onLoginClick = { viewModel.navigateTo(Screen.Login) },
                onBackClick = { viewModel.navigateBack() },
                modifier = Modifier.fillMaxSize()
            )
        }

        Screen.Home -> {
            HomeScreen(
                currentUser = uiState.currentUser,
                products = uiState.products,
                cart = uiState.cart,
                onAddToCart = viewModel::addToCart,
                onIncrementItem = viewModel::incrementCartItem,
                onDecrementItem = viewModel::decrementCartItem,
                onGoToOrder = { viewModel.navigateTo(Screen.OrderForm) },
                activeTab = uiState.activeNavTab,
                onTabSelected = viewModel::selectNavTab,
                modifier = Modifier.fillMaxSize()
            )
        }

        Screen.OrderForm -> {
            BackHandler {
                viewModel.navigateTo(Screen.Home)
            }
            OrderScreen(
                cartItems = uiState.cartItems,
                onIncrementItem = viewModel::incrementCartItem,
                onDecrementItem = viewModel::decrementCartItem,
                onGoToMenu = { viewModel.navigateTo(Screen.Home) },
                recipientNameValue = uiState.orderRecipientName,
                onRecipientNameChange = viewModel::updateOrderRecipientName,
                addressValue = uiState.orderAddress,
                onAddressChange = viewModel::updateOrderAddress,
                landmarkValue = uiState.orderLandmark,
                onLandmarkChange = viewModel::updateOrderLandmark,
                errorMessage = uiState.orderError,
                onOrderAndSendClick = viewModel::submitOrder,
                activeTab = uiState.activeNavTab,
                onTabSelected = viewModel::selectNavTab,
                modifier = Modifier.fillMaxSize()
            )
        }

        Screen.Profile -> {
            BackHandler {
                viewModel.navigateTo(Screen.Home)
            }
            ProfileScreen(
                currentUser = uiState.currentUser,
                nameInputValue = uiState.profileNameInput,
                onNameInputChange = viewModel::updateProfileNameInput,
                emailInputValue = uiState.profileEmailInput,
                onEmailInputChange = viewModel::updateProfileEmailInput,
                phoneInputValue = uiState.profilePhoneInput,
                onPhoneInputChange = viewModel::updateProfilePhoneInput,
                feedbackMessage = uiState.profileFeedback,
                onSaveClick = viewModel::saveProfile,
                onLogoutClick = viewModel::logout,
                activeTab = uiState.activeNavTab,
                onTabSelected = viewModel::selectNavTab,
                modifier = Modifier.fillMaxSize()
            )
        }

        Screen.OrderConfirmation -> {
            BackHandler {
                viewModel.resetOrderAndGoHome()
            }
            OrderConfirmationScreen(
                currentUser = uiState.currentUser,
                order = uiState.lastOrder,
                onOkClick = viewModel::resetOrderAndGoHome,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
