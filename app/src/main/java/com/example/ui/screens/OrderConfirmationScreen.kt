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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.OrderSummary
import com.example.model.User
import com.example.ui.components.KopiPrimaryButton
import com.example.ui.theme.CardCyan
import com.example.ui.theme.DarkNeutral
import com.example.ui.theme.InteractiveAccent
import com.example.ui.theme.KopiGreen
import com.example.ui.theme.LibreBaskervilleFontFamily
import com.example.ui.theme.SuccessCircleBg
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextDarkSecondary
import com.example.ui.theme.TextLightSecondary
import com.example.ui.theme.White

@Composable
fun OrderConfirmationScreen(
    currentUser: User?,
    order: OrderSummary?,
    onOkClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(KopiGreen)
            .padding(horizontal = 28.dp, vertical = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 480.dp)
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(64.dp))

                // Header: "Order Berhasil!"
                Text(
                    text = "Order Berhasil!",
                    fontFamily = LibreBaskervilleFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.testTag("confirmation_greeting")
                )

                Spacer(modifier = Modifier.height(36.dp))

                // Green Circular Indicator
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .shadow(12.dp, CircleShape)
                        .clip(CircleShape)
                        .background(SuccessCircleBg),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Success",
                            tint = SuccessGreen,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Terima kasih sudah memesan. Harap tunggu sebentar, kurir kami segera mengantarkan pesananmu!",
                    fontFamily = LibreBaskervilleFontFamily,
                    fontSize = 17.sp,
                    lineHeight = 26.sp,
                    color = White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .testTag("confirmation_message")
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Order Details Receipt Card
                if (order != null) {
                    Card(
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = CardCyan),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                            .testTag("confirmation_receipt_card")
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Rincian Pesanan",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = DarkNeutral
                                )
                                Text(
                                    text = order.orderId,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = DarkNeutral
                                )
                            }

                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 10.dp),
                                color = DarkNeutral.copy(alpha = 0.15f)
                            )

                            order.items.forEach { cartItem ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 2.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "${cartItem.product.name} x${cartItem.quantity}",
                                        fontSize = 14.sp,
                                        color = DarkNeutral
                                    )
                                    Text(
                                        text = cartItem.formattedSubtotal,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 14.sp,
                                        color = DarkNeutral
                                    )
                                }
                            }

                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 8.dp),
                                color = DarkNeutral.copy(alpha = 0.15f)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Total Pembayaran",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = DarkNeutral
                                )
                                Text(
                                    text = order.formattedTotalPrice,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = DarkNeutral
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "Tujuan: ${order.address} (${order.detailLandmark})",
                                fontSize = 12.sp,
                                color = TextDarkSecondary,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }

            // Bottom Button: "Oke" (matching wireframe)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
            ) {
                KopiPrimaryButton(
                    text = "Oke",
                    onClick = onOkClick,
                    backgroundColor = InteractiveAccent,
                    contentColor = White,
                    testTag = "confirmation_oke_button"
                )
            }
        }
    }
}
