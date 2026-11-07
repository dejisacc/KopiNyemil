package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.KopiPrimaryButton
import com.example.ui.components.KopiSecondaryButton
import com.example.ui.theme.DarkNeutral
import com.example.ui.theme.KopiGreen
import com.example.ui.theme.LibreBaskervilleFontFamily
import com.example.ui.theme.TextLightSecondary
import com.example.ui.theme.White

@Composable
fun SignUpChoiceScreen(
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(KopiGreen)
            .padding(horizontal = 28.dp, vertical = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 480.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
            ) {
                // Title "Sign up"
                Text(
                    text = "Sign up",
                    fontFamily = LibreBaskervilleFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    color = White,
                    modifier = Modifier.testTag("signup_choice_title")
                )

                Spacer(modifier = Modifier.height(16.dp))

                // description text
                Text(
                    text = "Join as a member & experience more fun from various meal at Kopi Nyemil marketing app",
                    fontSize = 15.sp,
                    lineHeight = 22.sp,
                    color = TextLightSecondary,
                    modifier = Modifier.testTag("signup_choice_description")
                )
            }

            // Bottom Buttons (Register & Login)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                KopiPrimaryButton(
                    text = "Register",
                    onClick = onRegisterClick,
                    backgroundColor = DarkNeutral,
                    contentColor = White,
                    testTag = "signup_choice_register_button"
                )

                Spacer(modifier = Modifier.height(16.dp))

                KopiSecondaryButton(
                    text = "Login",
                    onClick = onLoginClick,
                    testTag = "signup_choice_login_button"
                )
            }
        }
    }
}
