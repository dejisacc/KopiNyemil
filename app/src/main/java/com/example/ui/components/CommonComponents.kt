package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BottomNavTab
import com.example.ui.theme.ActiveTabColor
import com.example.ui.theme.DarkNeutral
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.InactiveTabColor
import com.example.ui.theme.InteractiveAccent
import com.example.ui.theme.KopiGreen
import com.example.ui.theme.KopiGreenDark
import com.example.ui.theme.White

@Composable
fun KopiPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    backgroundColor: Color = DarkNeutral,
    contentColor: Color = White,
    testTag: String = "primary_button"
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContainerColor = backgroundColor.copy(alpha = 0.5f),
            disabledContentColor = contentColor.copy(alpha = 0.5f)
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .testTag(testTag)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = contentColor
        )
    }
}

@Composable
fun KopiSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    testTag: String = "secondary_button"
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(2.dp, White),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = White
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .testTag(testTag)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = White
        )
    }
}

@Composable
fun KopiInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    isPasswordVisible: Boolean = false,
    onTogglePasswordVisibility: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    testTag: String = "input_field"
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                color = DarkNeutral.copy(alpha = 0.55f),
                fontSize = 15.sp
            )
        },
        visualTransformation = if (isPassword && !isPasswordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        trailingIcon = if (isPassword && onTogglePasswordVisibility != null) {
            {
                IconButton(onClick = onTogglePasswordVisibility) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                        tint = DarkNeutral.copy(alpha = 0.7f)
                    )
                }
            }
        } else null,
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = White,
            unfocusedContainerColor = White.copy(alpha = 0.95f),
            disabledContainerColor = White.copy(alpha = 0.7f),
            focusedBorderColor = InteractiveAccent,
            unfocusedBorderColor = Color(0x33000000),
            focusedTextColor = DarkNeutral,
            unfocusedTextColor = DarkNeutral,
            cursorColor = InteractiveAccent
        ),
        singleLine = singleLine,
        maxLines = maxLines,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        modifier = modifier
            .fillMaxWidth()
            .testTag(testTag)
    )
}

@Composable
fun KopiBottomNavBar(
    selectedTab: BottomNavTab,
    onTabSelected: (BottomNavTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = KopiGreenDark,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        shadowElevation = 10.dp,
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                title = "Home",
                icon = Icons.Default.Home,
                isSelected = selectedTab == BottomNavTab.HOME,
                onClick = { onTabSelected(BottomNavTab.HOME) },
                testTag = "nav_home"
            )
            BottomNavItem(
                title = "Order",
                icon = Icons.Default.ReceiptLong,
                isSelected = selectedTab == BottomNavTab.ORDER,
                onClick = { onTabSelected(BottomNavTab.ORDER) },
                testTag = "nav_order"
            )
            BottomNavItem(
                title = "Profile",
                icon = Icons.Default.Person,
                isSelected = selectedTab == BottomNavTab.PROFILE,
                onClick = { onTabSelected(BottomNavTab.PROFILE) },
                testTag = "nav_profile"
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    val bgColor by animateColorAsState(
        targetValue = if (isSelected) DarkNeutral else Color.Transparent,
        label = "navBg"
    )
    val contentColor by animateColorAsState(
        targetValue = if (isSelected) InteractiveAccent else InactiveTabColor,
        label = "navColor"
    )

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .testTag(testTag),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = contentColor,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = title,
                color = contentColor,
                fontSize = 14.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
            )
        }
    }
}
