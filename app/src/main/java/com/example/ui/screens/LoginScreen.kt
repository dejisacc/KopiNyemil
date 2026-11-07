package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.KopiInputField
import com.example.ui.components.KopiPrimaryButton
import com.example.ui.theme.DarkNeutral
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.InteractiveAccent
import com.example.ui.theme.KopiGreen
import com.example.ui.theme.LibreBaskervilleFontFamily
import com.example.ui.theme.SuccessGreenLight
import com.example.ui.theme.TextLightSecondary
import com.example.ui.theme.White

@Composable
fun LoginScreen(
    usernameValue: String,
    onUsernameChange: (String) -> Unit,
    passwordValue: String,
    onPasswordChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    errorMessage: String?,
    successMessage: String?,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(KopiGreen)
            .padding(horizontal = 28.dp, vertical = 32.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 480.dp)
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("login_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali",
                            tint = White
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Login",
                        fontFamily = LibreBaskervilleFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                        color = White,
                        modifier = Modifier.testTag("login_title")
                    )
                }

                Spacer(modifier = Modifier.height(36.dp))

                // Success Message Banner if navigated from registration
                if (successMessage != null) {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = SuccessGreenLight),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Text(
                            text = successMessage,
                            color = DarkNeutral,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }

                // Error Message Banner
                if (errorMessage != null) {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = ErrorRed.copy(alpha = 0.15f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Text(
                            text = errorMessage,
                            color = White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }

                // Username Input
                Text(
                    text = "Username",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextLightSecondary,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                KopiInputField(
                    value = usernameValue,
                    onValueChange = onUsernameChange,
                    placeholder = "username",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    testTag = "login_username_input"
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Password Input
                Text(
                    text = "Password",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextLightSecondary,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                KopiInputField(
                    value = passwordValue,
                    onValueChange = onPasswordChange,
                    placeholder = "password",
                    isPassword = true,
                    isPasswordVisible = isPasswordVisible,
                    onTogglePasswordVisibility = onTogglePasswordVisibility,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    testTag = "login_password_input"
                )
            }

            // Bottom Actions: Login Button and Register Link
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                KopiPrimaryButton(
                    text = "Login",
                    onClick = onLoginClick,
                    backgroundColor = DarkNeutral,
                    contentColor = White,
                    testTag = "login_submit_button"
                )

                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Belum punya akun? ",
                        color = TextLightSecondary,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Register sekarang",
                        color = InteractiveAccent,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .clickable(onClick = onRegisterClick)
                            .testTag("login_to_register_link")
                    )
                }
            }
        }
    }
}
