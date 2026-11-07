package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BottomNavTab
import com.example.model.CartItem
import com.example.ui.components.KopiBottomNavBar
import com.example.ui.components.KopiInputField
import com.example.ui.components.KopiPrimaryButton
import com.example.ui.theme.CardCyan
import com.example.ui.theme.DarkNeutral
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.InteractiveAccent
import com.example.ui.theme.KopiGreen
import com.example.ui.theme.LibreBaskervilleFontFamily
import com.example.ui.theme.TextDarkSecondary
import com.example.ui.theme.TextLightSecondary
import com.example.ui.theme.White
import java.text.NumberFormat
import java.util.Locale

@Composable
fun OrderScreen(
    cartItems: List<CartItem>,
    onIncrementItem: (String) -> Unit,
    onDecrementItem: (String) -> Unit,
    onGoToMenu: () -> Unit,
    recipientNameValue: String,
    onRecipientNameChange: (String) -> Unit,
    addressValue: String,
    onAddressChange: (String) -> Unit,
    landmarkValue: String,
    onLandmarkChange: (String) -> Unit,
    errorMessage: String?,
    onOrderAndSendClick: () -> Unit,
    activeTab: BottomNavTab,
    onTabSelected: (BottomNavTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val totalAmount = cartItems.sumOf { it.subtotal }
    val formatter = NumberFormat.getNumberInstance(Locale("in", "ID"))
    val formattedTotal = "Rp ${formatter.format(totalAmount)}"

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

                    // Title: "My Order" (Requirement 4: changed from "Alamat" to "My Order")
                    Text(
                        text = "My Order",
                        fontFamily = LibreBaskervilleFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = White,
                        modifier = Modifier.testTag("order_title")
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Multi-Item Order List Section
                    Card(
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = CardCyan),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Daftar Pesanan (${cartItems.sumOf { it.quantity }} item)",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = DarkNeutral
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            if (cartItems.isEmpty()) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ShoppingCart,
                                        contentDescription = "Keranjang Kosong",
                                        tint = DarkNeutral.copy(alpha = 0.5f),
                                        modifier = Modifier.size(40.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Belum ada produk yang dipilih",
                                        fontSize = 14.sp,
                                        color = DarkNeutral
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    OutlinedButton(onClick = onGoToMenu) {
                                        Text(text = "Pilih Menu", color = InteractiveAccent)
                                    }
                                }
                            } else {
                                cartItems.forEachIndexed { index, cartItem ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Image(
                                            painter = painterResource(id = cartItem.product.drawableRes),
                                            contentDescription = cartItem.product.name,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .size(54.dp)
                                                .clip(RoundedCornerShape(10.dp))
                                        )
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = cartItem.product.name,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 14.sp,
                                                color = DarkNeutral
                                            )
                                            Text(
                                                text = "${cartItem.product.formattedPrice} x ${cartItem.quantity}",
                                                fontSize = 12.sp,
                                                color = TextDarkSecondary
                                            )
                                            Text(
                                                text = cartItem.formattedSubtotal,
                                                fontWeight = FontWeight.SemiBold,
                                                fontSize = 13.sp,
                                                color = InteractiveAccent
                                            )
                                        }

                                        // Stepper for adjusting quantity of this product
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(12.dp))
                                                .background(White)
                                                .padding(horizontal = 4.dp, vertical = 2.dp)
                                        ) {
                                            IconButton(
                                                onClick = { onDecrementItem(cartItem.product.id) },
                                                modifier = Modifier.size(28.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Remove,
                                                    contentDescription = "Kurang",
                                                    tint = DarkNeutral,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }
                                            Text(
                                                text = "${cartItem.quantity}",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 14.sp,
                                                color = DarkNeutral,
                                                modifier = Modifier.padding(horizontal = 6.dp)
                                            )
                                            IconButton(
                                                onClick = { onIncrementItem(cartItem.product.id) },
                                                modifier = Modifier.size(28.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Add,
                                                    contentDescription = "Tambah",
                                                    tint = DarkNeutral,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }
                                        }
                                    }

                                    if (index < cartItems.size - 1) {
                                        HorizontalDivider(
                                            modifier = Modifier.padding(vertical = 4.dp),
                                            color = DarkNeutral.copy(alpha = 0.1f)
                                        )
                                    }
                                }

                                HorizontalDivider(
                                    modifier = Modifier.padding(top = 10.dp, bottom = 8.dp),
                                    color = DarkNeutral.copy(alpha = 0.2f)
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Total Pembayaran:",
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 14.sp,
                                        color = DarkNeutral
                                    )
                                    Text(
                                        text = formattedTotal,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 17.sp,
                                        color = DarkNeutral
                                    )
                                }
                            }
                        }
                    }

                    // Error Banner
                    if (errorMessage != null) {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = ErrorRed.copy(alpha = 0.2f)),
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

                    // Input: Nama Lengkap
                    Text(
                        text = "Nama Lengkap",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextLightSecondary,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                    KopiInputField(
                        value = recipientNameValue,
                        onValueChange = onRecipientNameChange,
                        placeholder = "Nama Lengkap Penerima",
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        testTag = "order_recipient_input"
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Input: Alamat
                    Text(
                        text = "Alamat",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextLightSecondary,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                    KopiInputField(
                        value = addressValue,
                        onValueChange = onAddressChange,
                        placeholder = "Alamat Pengiriman",
                        singleLine = false,
                        maxLines = 3,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier.heightIn(min = 80.dp),
                        testTag = "order_address_input"
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Input: Patokan atau detail lengkap
                    Text(
                        text = "Patokan atau detail lengkap",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextLightSecondary,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                    KopiInputField(
                        value = landmarkValue,
                        onValueChange = onLandmarkChange,
                        placeholder = "Patokan atau detail lengkap (mis. Pagar hitam)",
                        singleLine = false,
                        maxLines = 3,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier.heightIn(min = 80.dp),
                        testTag = "order_landmark_input"
                    )
                }

                // Button: Order dan Kirim
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
                ) {
                    KopiPrimaryButton(
                        text = "Order dan Kirim",
                        onClick = onOrderAndSendClick,
                        backgroundColor = InteractiveAccent,
                        contentColor = White,
                        enabled = cartItems.isNotEmpty(),
                        testTag = "order_submit_button"
                    )
                }
            }
        }
    }
}
