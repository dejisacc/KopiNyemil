package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.KopiPrimaryButton
import com.example.ui.theme.BowlbyOneSCFontFamily
import com.example.ui.theme.DarkNeutral
import com.example.ui.theme.InteractiveAccent
import com.example.ui.theme.KopiGreen
import com.example.ui.theme.LibreBaskervilleFontFamily
import com.example.ui.theme.TextLightSecondary
import com.example.ui.theme.White

@Composable
fun WelcomeScreen(
    onStartNowClick: () -> Unit,
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Center Content: Logo, Title, and Tagline (matching Wireframe 1. Splash/Welcome)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                // Circular Logo Container with soft warm background (peach/coral tint as in wireframe)
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .shadow(12.dp, CircleShape)
                        .clip(CircleShape)
                        .background(InteractiveAccent.copy(alpha = 0.35f)),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(136.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF3C4B6)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_kopi_nyemil_logo),
                            contentDescription = "Logo Kopi dan Nyemil",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(136.dp)
                                .clip(CircleShape)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(36.dp))

                // Bowlby One SC Google Font
                Text(
                    text = "Kopi Nyemil",
                    fontFamily = BowlbyOneSCFontFamily,
                    fontSize = 30.sp,
                    color = White,
                    textAlign = TextAlign.Center,
                    lineHeight = 38.sp,
                    modifier = Modifier.testTag("welcome_app_title")
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Libre Baskerville Google Font
                Text(
                    text = "Get a taste of your wish",
                    fontFamily = LibreBaskervilleFontFamily,
                    fontSize = 16.sp,
                    color = TextLightSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.testTag("welcome_tagline")
                )
            }

            // Bottom "Start now" Button
            KopiPrimaryButton(
                text = "Start now",
                onClick = onStartNowClick,
                backgroundColor = DarkNeutral,
                contentColor = White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                testTag = "start_now_button"
            )
        }
    }
}
