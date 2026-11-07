package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BottomNavTab
import com.example.model.User
import com.example.ui.components.KopiBottomNavBar
import com.example.ui.components.KopiInputField
import com.example.ui.components.KopiPrimaryButton
import com.example.ui.theme.CardCyan
import com.example.ui.theme.DarkNeutral
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.InteractiveAccent
import com.example.ui.theme.KopiGreen
import com.example.ui.theme.LibreBaskervilleFontFamily
import com.example.ui.theme.SuccessCircleBg
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextDarkSecondary
import com.example.ui.theme.TextLightSecondary
import com.example.ui.theme.White

@Composable
fun ProfileScreen(
    currentUser: User?,
    nameInputValue: String,
    onNameInputChange: (String) -> Unit,
    emailInputValue: String,
    onEmailInputChange: (String) -> Unit,
    phoneInputValue: String,
    onPhoneInputChange: (String) -> Unit,
    feedbackMessage: String?,
    onSaveClick: () -> Unit,
    onLogoutClick: () -> Unit,
    activeTab: BottomNavTab,
    onTabSelected: (BottomNavTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val displayName = currentUser?.fullName?.ifBlank { "Nama" } ?: "Nama"

    Scaffold(
        bottomBar = {
            KopiBottomNavBar(
                selectedTab = activeTab,
                onTabSelected = onTabSelected
            )
        },
        containerColor = KopiGreen,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 520.dp)
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(24.dp))

                    // Title: "Profile Settings"
                    Text(
                        text = "Profile Settings",
                        fontFamily = LibreBaskervilleFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        color = White,
                        modifier = Modifier.testTag("profile_title")
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // User Info Overview Card
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = CardCyan),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(CircleShape)
                                    .background(InteractiveAccent),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Avatar",
                                    tint = White,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(
                                    text = displayName,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp,
                                    color = DarkNeutral
                                )
                                Text(
                                    text = "Username: @${currentUser?.username ?: "budi"}",
                                    fontSize = 13.sp,
                                    color = TextDarkSecondary
                                )
                            }
                        }
                    }

                    // Feedback Message
                    if (feedbackMessage != null) {
                        val isSuccess = feedbackMessage.contains("berhasil", ignoreCase = true)
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSuccess) SuccessCircleBg else ErrorRed.copy(alpha = 0.2f)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 14.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (isSuccess) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Success",
                                        tint = SuccessGreen,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                }
                                Text(
                                    text = feedbackMessage,
                                    color = if (isSuccess) DarkNeutral else White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                    // Label: "Nama"
                    Text(
                        text = "Nama",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextLightSecondary,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                    KopiInputField(
                        value = nameInputValue,
                        onValueChange = onNameInputChange,
                        placeholder = "Nama Lengkap",
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        testTag = "profile_name_input"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Label: "Alamat Email"
                    Text(
                        text = "Alamat Email",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextLightSecondary,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                    KopiInputField(
                        value = emailInputValue,
                        onValueChange = onEmailInputChange,
                        placeholder = "contoh@email.com",
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        testTag = "profile_email_input"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Label: "Nomor Ponsel"
                    Text(
                        text = "Nomor Ponsel",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextLightSecondary,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                    KopiInputField(
                        value = phoneInputValue,
                        onValueChange = onPhoneInputChange,
                        placeholder = "081234567890",
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Done
                        ),
                        testTag = "profile_phone_input"
                    )
                }

                // Bottom Buttons: Save and Logout
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
                ) {
                    KopiPrimaryButton(
                        text = "Save",
                        onClick = onSaveClick,
                        backgroundColor = InteractiveAccent,
                        contentColor = White,
                        testTag = "profile_save_button"
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedButton(
                        onClick = onLogoutClick,
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("profile_logout_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Logout",
                            tint = White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Keluar / Ganti Akun",
                            color = White,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}
